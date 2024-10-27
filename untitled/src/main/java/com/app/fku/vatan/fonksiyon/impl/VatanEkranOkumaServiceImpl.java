package com.app.fku.vatan.fonksiyon.impl;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.vatan.entity.VatanKategori;
import com.app.fku.vatan.entity.VatanTelegramConf;
import com.app.fku.vatan.fonksiyon.service.VatanEkranOkumaService;
import com.app.fku.vatan.fonksiyon.service.VatanGenelService;
import com.app.fku.vatan.repository.VatanTelegramConfRepository;
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
public class VatanEkranOkumaServiceImpl implements VatanEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    VatanGenelService vatanGenelService;

    @Autowired
    VatanTelegramConfRepository vatanTelegramConfRepository;

    @Override
    public HashMap<String, GenelUrunModel> kategoriIleUrunBul(VatanKategori vatanKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        if (yeniUrunHashMap == null) {
            yeniUrunHashMap = new HashMap<>();
        }

        try {

            Document vatanDoc = null;
            for (int i = 1; i > 0; i++) {
                if (vatanKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    vatanDoc = vatanGenelService.urleGit(vatanKategori.getSayfaAdresi() + "&page=" + pageNumber);
                } else {
                    vatanDoc = vatanGenelService.urleGit(vatanKategori.getSayfaAdresi() + "?page=" + pageNumber);
                }

                if (vatanDoc == null) {
                    logService.vatanLogYaz("VATAN Hata aAA");
                    Thread.sleep(4000);
                } else {
                    break;
                }
            }

            int urunSayisi = Integer.valueOf(vatanDoc.getElementsByClass("wrapper-detailpage-header__text").get(0).childNodes().get(1).childNodes().get(2).childNodes().get(0).toString().trim());
            if (urunSayisi % 16 == 0) {
                maxPageNumber = (urunSayisi / 16);
            } else {
                maxPageNumber = (urunSayisi / 16) + 1;
            }

            Elements productElements = vatanDoc.getElementById("productsLoad").getElementsByClass("product-list");
            for (Element element : productElements) {
                Attributes productAttr = element.attributes();
                String vatanNo = element.getElementsByClass("product-list__product-code").get(0).childNodes().get(0).toString().trim();
                String model = element.getElementsByClass("product-list__product-name").get(0).childNodes().get(1).childNodes().get(0).toString();
                String urunUrl = "https://www.vatanbilgisayar.com" + element.getElementsByClass("product-list__content").get(0).childNodes().get(3).attributes().get("href");

                GenelUrunModel genelUrunModel = urunHashMap.get(vatanNo);
                String fiyatStr = element.getElementsByClass("product-list__price").get(0).childNodes().get(0).toString().trim().replace(".", "");

                Double yeniFiyat = Double.valueOf(fiyatStr);

                if (genelUrunModel == null) {
                    //yeni gelmiş ürün
                    GenelUrunModel yeniUrunModel = new GenelUrunModel();
                    yeniUrunModel.setModel(model);
                    yeniUrunModel.setFiyat(yeniFiyat);
                    yeniUrunModel.setMinFiyat(yeniFiyat);
                    yeniUrunModel.setMinFiyatTarihi(new Date());
                    yeniUrunModel.setSonMesajTarihi(new Date());
                    yeniUrunHashMap.put(vatanNo, yeniUrunModel);

                    if (!ilkMi) {
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                vatanKategori.getKategoriAdi() + "%0A" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + model + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + urunUrl + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, vatanKategori, model, 0L, yeniFiyat.longValue());
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
                                    vatanKategori.getKategoriAdi() + "%0A" +
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

                            telegramMesajGonder(mesaj, vatanKategori, model, indYuzde.longValue(), yeniFiyat.longValue());
                            genelUrunModel.setSonMesajTarihi(new Date());
                        }

                    }
                    genelUrunModel.setFiyat(yeniFiyat);
                    if (yeniFiyat < genelUrunModel.getMinFiyat()) {
                        genelUrunModel.setMinFiyat(yeniFiyat);
                        genelUrunModel.setMinFiyatTarihi(new Date());
                    }

                    yeniUrunHashMap.put(vatanNo, genelUrunModel);
                }
            }

            if (pageNumber < maxPageNumber) {
                yeniUrunHashMap = kategoriIleUrunBul(vatanKategori, 0, pageNumber + 1, maxPageNumber, urunHashMap, yeniUrunHashMap, ilkMi);
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            logService.vatanLogYaz("VATAN Hata");
            Thread.sleep(3000L);
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, VatanKategori vatanKategori, String urunId, Long indYuzde, Long fiyat) throws IOException, InterruptedException {
        List<VatanTelegramConf> vatanTelegramConfList = vatanTelegramConfRepository.findByVatanKategori(vatanKategori);

        for (VatanTelegramConf vatanTelegramConf : vatanTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, vatanTelegramConf.getTelegramId(), urunId, "5760676131:AAH17ISU_Uqu0qkZkTN_CJ4JKu08k8780vM");
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