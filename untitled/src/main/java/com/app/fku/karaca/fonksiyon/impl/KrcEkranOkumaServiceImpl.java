package com.app.fku.karaca.fonksiyon.impl;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.karaca.entity.KrcKategori;
import com.app.fku.karaca.entity.KrcTelegramConf;
import com.app.fku.karaca.fonksiyon.service.KrcEkranOkumaService;
import com.app.fku.karaca.fonksiyon.service.KrcGenelService;
import com.app.fku.karaca.repository.KrcTelegramConfRepository;
import org.jsoup.nodes.Attributes;
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
import java.util.concurrent.TimeUnit;

@Service
public class KrcEkranOkumaServiceImpl implements KrcEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    KrcGenelService krcGenelService;

    @Autowired
    KrcTelegramConfRepository krcTelegramConfRepository;

    @Override
    public HashMap<String, GenelUrunModel> kategoriIleUrunBul(KrcKategori krcKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        if (yeniUrunHashMap == null) {
            yeniUrunHashMap = new HashMap<>();
        }

        try {
            Document krcDoc = null;
            for (int i = 1; i > 0; i++) {
                if (krcKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    krcDoc = krcGenelService.urleGit(krcKategori.getSayfaAdresi() + "&page=" + pageNumber);
                } else {
                    krcDoc = krcGenelService.urleGit(krcKategori.getSayfaAdresi() + "?page=" + pageNumber);
                }

                if (krcDoc == null) {
                    logService.krcLogYaz("KRC Hata aAA");
                    Thread.sleep(4000);
                } else {
                    break;
                }
            }

            int urunSayisi = Integer.valueOf(krcDoc.getElementById("pagination-total").childNodes().get(0).toString());
            if (urunSayisi % 23 == 0) {
                maxPageNumber = (urunSayisi / 23);
            } else {
                maxPageNumber = (urunSayisi / 23) + 1;
            }

            Elements productElements = krcDoc.getElementsByClass("product-item");
            for (Element element: productElements) {
                Attributes productAttr = element.attributes();
                String krcNo = element.getElementsByClass("product-detail-card").get(0).getElementsByClass("product-title").get(0).attributes().get("href");
                String model = element.getElementsByClass("product-detail-card").get(0).getElementsByClass("product-title").get(0).childNodes().get(0).toString();
                String urunUrl = "https://www.mutfakdunyasi.com/" + element.getElementsByClass("product-detail-card").get(0).getElementsByClass("product-title").get(0).attributes().get("href");

                GenelUrunModel genelUrunModel = urunHashMap.get(krcNo);
                String fiyatStr = element.getElementsByClass("current-price").get(0).childNodes().get(1).childNodes().get(0).toString().split(",")[0].replace(".","");

                 Double yeniFiyat = Double.valueOf(fiyatStr);

                 if (genelUrunModel == null) {
                    //yeni gelmiş ürün
                     GenelUrunModel yeniUrunModel = new GenelUrunModel();
                     yeniUrunModel.setModel(model);
                     yeniUrunModel.setFiyat(yeniFiyat);
                     yeniUrunModel.setMinFiyat(yeniFiyat);
                     yeniUrunModel.setMinFiyatTarihi(new Date());
                     yeniUrunModel.setSonMesajTarihi(new Date());
                     yeniUrunHashMap.put(krcNo, yeniUrunModel);

                    if (!ilkMi) {
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                krcKategori.getKategoriAdi() + "%0A" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + model + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + urunUrl + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, krcKategori, model, 0L, yeniFiyat.longValue());
                    }
                } else {
                    //zaten var olan ürün
                    if (yeniFiyat < genelUrunModel.getFiyat()) {
                        //mesaj at
                        long diff = new Date().getTime() - genelUrunModel.getSonMesajTarihi().getTime();
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                        minutes = 100;
                        if (minutes > 10) {
                            String akakceLink = model.replace(" ", "%2B");
                            akakceLink = akakceLink.replace("+", "");

                            Double indirim = genelUrunModel.getFiyat() - yeniFiyat;
                            Double indYuzde = 100 * indirim / genelUrunModel.getFiyat();
                            String mesaj = "" +
                                    krcKategori.getKategoriAdi() + "%0A" +
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

                            telegramMesajGonder(mesaj, krcKategori, model, indYuzde.longValue(), yeniFiyat.longValue());
                            genelUrunModel.setSonMesajTarihi(new Date());
                        }

                    }
                     genelUrunModel.setFiyat(yeniFiyat);
                     if (yeniFiyat < genelUrunModel.getMinFiyat()) {
                         genelUrunModel.setMinFiyat(yeniFiyat);
                         genelUrunModel.setMinFiyatTarihi(new Date());
                     }

                     yeniUrunHashMap.put(krcNo, genelUrunModel);
                }
            }

            if (pageNumber < maxPageNumber) {
                yeniUrunHashMap = kategoriIleUrunBul(krcKategori, 0, pageNumber + 1, maxPageNumber, urunHashMap, yeniUrunHashMap, ilkMi);
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            logService.krcLogYaz("KRC Hata");
            Thread.sleep(3000L);
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, KrcKategori krcKategori, String urunId, Long indYuzde, Long fiyat) throws IOException, InterruptedException {
        List<KrcTelegramConf> krcTelegramConfList = krcTelegramConfRepository.findByKrcKategori(krcKategori);

        for (KrcTelegramConf krcTelegramConf : krcTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, krcTelegramConf.getTelegramId(), urunId, "5962933022:AAE3Gc-9l0fB7AfCqfVmTJjPpeu-DjTn5bs");
        }
    }
}