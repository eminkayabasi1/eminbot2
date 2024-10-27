package com.app.fku.yeniamazon.fonksiyon.impl;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.yeniamazon.entity.YeniAmazonKategori;
import com.app.fku.yeniamazon.entity.YeniAmazonTelegramConf;
import com.app.fku.yeniamazon.fonksiyon.service.YeniAmazonEkranOkumaService;
import com.app.fku.yeniamazon.fonksiyon.service.YeniAmazonGenelService;
import com.app.fku.yeniamazon.repository.YeniAmazonTelegramConfRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class YeniAmazonEkranOkumaServiceImpl implements YeniAmazonEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    YeniAmazonGenelService yeniAmazonGenelService;

    @Autowired
    YeniAmazonTelegramConfRepository yeniAmazonTelegramConfRepository;

    @Override
    public HashMap<String, GenelUrunModel> kategoriIleUrunBul(YeniAmazonKategori yeniAmazonKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        if (yeniUrunHashMap == null) {
            yeniUrunHashMap = new HashMap<>();
        }
        Document yeniAmzDoc = null;
        try {
            for (int i = 1; i > 0; i++) {
                if (yeniAmazonKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    yeniAmzDoc = yeniAmazonGenelService.urleGit(yeniAmazonKategori.getSayfaAdresi() + "&page=" + pageNumber);
                } else {
                    yeniAmzDoc = yeniAmazonGenelService.urleGit(yeniAmazonKategori.getSayfaAdresi() + "?page=" + pageNumber);
                }

                if (yeniAmzDoc == null) {
                    logService.yeniAmzLogYaz("Yeni Amz Hata aAA");
                    Thread.sleep(4000);
                } else {
                    break;
                }
            }

            Random rand = new Random();
            int sleepRand = rand.nextInt(5);
            TimeUnit.SECONDS.sleep(sleepRand + 15);

            logService.yeniAmzLogYaz("Yeni Amz Başarılı : " + sdf.format(new Date()));

            try {
                int urunSayisi = Integer.valueOf(yeniAmzDoc.getElementsByClass("a-section a-spacing-small a-spacing-top-small").get(0).childNodes().get(1).childNodes().get(0).toString().split("/")[1].split(" sonu")[0].trim());
                if (urunSayisi % 24 == 0) {
                    maxPageNumber = (urunSayisi / 24);
                } else {
                    maxPageNumber = (urunSayisi / 24) + 1;
                }
            } catch (Exception e) {
                System.out.println(yeniAmazonKategori.getKategoriAdi() + " ürün sayısı okuyamadım.");
            }


            Elements productElements = yeniAmzDoc.getElementsByClass("s-search-results").get(0).getElementsByClass("s-result-item");
            for (Element element : productElements) {
                String amzNo = element.attributes().get("data-asin").trim();
                if (amzNo == null || amzNo.equals("")) {
                    continue;
                }
                String model = element.getElementsByClass("a-section a-spacing-none a-spacing-top-small s-title-instructions-style").get(0).childNodes().get(0).childNodes().get(0).childNodes().get(0).childNodes().get(0).toString();
                String urunUrl = "https://www.amazon.com.tr/a/dp/" + amzNo;

                GenelUrunModel genelUrunModel = urunHashMap.get(amzNo);
                String fiyatStr = "";
                try {
                    fiyatStr = element.getElementsByClass("a-price").get(0).getElementsByClass("a-price-whole").get(0).childNodes().get(0).toString().replace(".","").trim();
                } catch (Exception e) {
                    continue;
                }

                Double yeniFiyat = Double.valueOf(fiyatStr);

                if (genelUrunModel == null) {
                    //yeni gelmiş ürün
                    GenelUrunModel yeniUrunModel = new GenelUrunModel();
                    yeniUrunModel.setModel(model);
                    yeniUrunModel.setFiyat(yeniFiyat);
                    yeniUrunModel.setMinFiyat(yeniFiyat);
                    yeniUrunModel.setMinFiyatTarihi(new Date());
                    yeniUrunModel.setSonMesajTarihi(new Date());
                    yeniUrunHashMap.put(amzNo, yeniUrunModel);

                    if (!ilkMi) {
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                yeniAmazonKategori.getKategoriAdi() + "%0A" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + model + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + urunUrl + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, yeniAmazonKategori, model, 0L, yeniFiyat.longValue());
                    }
                } else {
                    //zaten var olan ürün
                    if (yeniFiyat < genelUrunModel.getFiyat()) {
                        //mesaj at
                        long diff = new Date().getTime() - genelUrunModel.getSonMesajTarihi().getTime();
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                        if (minutes > 10) {
                            String akakceLink = model.replace(" ", "%2B");
                            akakceLink = akakceLink.replace("+", "");

                            Double indirim = genelUrunModel.getFiyat() - yeniFiyat;
                            Double indYuzde = 100 * indirim / genelUrunModel.getFiyat();
                            String mesaj = "" +
                                    yeniAmazonKategori.getKategoriAdi() + "%0A" +
                                    "Ürün Adı: " + model + "%0A" +
                                    "Eski Fiyat: " + nf.format(genelUrunModel.getFiyat()) + "%0A" +
                                    "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                    "Min Fiyat: " + nf.format(genelUrunModel.getMinFiyat()) + "%0A" +
                                    "Min Fiyat Tarihi: " + sdf.format(genelUrunModel.getMinFiyatTarihi()) + "%0A" +
                                    "İndirim Yüzde: " + nf.format(indYuzde) + "%0A" +
                                    "İndirim: " + nf.format(indirim) + "%0A" +
                                    "Ürün Link:" + urunUrl + "%0A" +
                                    "Tarih: " + sdf.format(new Date()) + "%0A" +
                                    "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                    "***Generated By Emin KAYABASI***";

                            telegramMesajGonder(mesaj, yeniAmazonKategori, model, indYuzde.longValue(), yeniFiyat.longValue());
                            genelUrunModel.setSonMesajTarihi(new Date());
                        }

                    }
                    genelUrunModel.setFiyat(yeniFiyat);
                    if (yeniFiyat < genelUrunModel.getMinFiyat()) {
                        genelUrunModel.setMinFiyat(yeniFiyat);
                        genelUrunModel.setMinFiyatTarihi(new Date());
                    }

                    yeniUrunHashMap.put(amzNo, genelUrunModel);
                }
            }

            if (pageNumber < maxPageNumber) {
                yeniUrunHashMap = kategoriIleUrunBul(yeniAmazonKategori, 0, pageNumber + 1, maxPageNumber, urunHashMap, yeniUrunHashMap, ilkMi);
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            logService.yeniAmzLogYaz("Yeni Amz Hata");
            Thread.sleep(3000L);
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, YeniAmazonKategori yeniAmazonKategori, String urunId, Long indYuzde, Long fiyat) throws IOException, InterruptedException {
        List<YeniAmazonTelegramConf> yeniAmazonTelegramConfList = yeniAmazonTelegramConfRepository.findByYeniAmazonKategori(yeniAmazonKategori);

        for (YeniAmazonTelegramConf yeniAmazonTelegramConf : yeniAmazonTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, yeniAmazonTelegramConf.getTelegramId(), urunId, "5408509672:AAGGqO8w_M57aUeXvvxMycnDrJZ7fvonzsM");
        }

        if (indYuzde > 40) {
            genelService.telegramBombaGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("DYSON")) {
            genelService.telegramDysonGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("AIRFRYER") ||
                urunId.toUpperCase().contains("AİRFRYER") ||
                urunId.toUpperCase().contains("AIR FRYER") ||
                urunId.toUpperCase().contains("AİR FRYER") ||
                urunId.toUpperCase().contains("FRITOZ") ||
                urunId.toUpperCase().contains("FRİTOZ") ||
                urunId.toUpperCase().contains("FRİTÖZ") ||
                urunId.toUpperCase().contains("FRITÖZ")) {
            genelService.telegramFritozGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("FISSLER") ||
                urunId.toUpperCase().contains("FİSSLER")) {
            genelService.telegramFisslerGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("PLAYSTATION") ||
                urunId.toUpperCase().contains("PLAYSTATİON") ||
                urunId.toUpperCase().contains("PLAY STATION") ||
                urunId.toUpperCase().contains("PLAY STATİON")) {
            genelService.telegramPlayStationGonder(mesaj, urunId);
        }

        if (fiyat <= 50) {
            genelService.telegramFiyatHatasiGonder(mesaj, urunId);
        }
    }
}