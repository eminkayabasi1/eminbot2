package com.app.fku.turkcell.fonksiyon.impl;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.turkcell.entity.TurkcellKategori;
import com.app.fku.turkcell.entity.TurkcellTelegramConf;
import com.app.fku.turkcell.fonksiyon.service.TurkcellEkranOkumaService;
import com.app.fku.turkcell.fonksiyon.service.TurkcellGenelService;
import com.app.fku.turkcell.repository.TurkcellTelegramConfRepository;
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
public class TurkcellEkranOkumaServiceImpl implements TurkcellEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    TurkcellGenelService turkcellGenelService;

    @Autowired
    TurkcellTelegramConfRepository turkcellTelegramConfRepository;

    @Override
    public HashMap<String, GenelUrunModel> kategoriIleUrunBul(TurkcellKategori turkcellKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        if (yeniUrunHashMap == null) {
            yeniUrunHashMap = new HashMap<>();
        }

        try {
            Document turkcellDoc = null;
            for (int i = 1; i > 0; i++) {
                if (turkcellKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    turkcellDoc = turkcellGenelService.urleGit(turkcellKategori.getSayfaAdresi() + "&page=" + pageNumber);
                } else {
                    turkcellDoc = turkcellGenelService.urleGit(turkcellKategori.getSayfaAdresi() + "?page=" + pageNumber);
                }

                if (turkcellDoc == null) {
                    logService.turkcellLogYaz("TURKCELL Hata aAA");
                    Thread.sleep(4000);
                } else {
                    break;
                }
            }

            if (maxPageNumber == 1) {
                Elements pagerElements = turkcellDoc.getElementsByClass("m-p-pagination__container");
                if (pagerElements != null && pagerElements.size() > 0) {
                    maxPageNumber = Integer.parseInt(pagerElements.get(0).childNodes().get(1).childNodes().get(pagerElements.get(0).childNodes().get(1).childNodes().size()-2).childNodes().get(1).childNodes().get(0).toString().trim());
                } else {
                    maxPageNumber = 1;
                }
            }

            Elements productElements = turkcellDoc.getElementById("all-devices-section").getElementsByClass("product");
            for (Element element: productElements) {
                Attributes productAttr = element.childNodes().get(1).attributes();
                String turkcellNo = productAttr.get("data-product-id");
                String model = element.getElementsByClass("m-p-pc__title").get(0).childNodes().get(0).toString().trim();
                String urunUrl = "https://www.turkcell.com.tr" + productAttr.get("href");

                Elements comingSoonElements = element.getElementsByClass("m-p-pc__foot m-p-pc__foot--coming-soon");
                if (comingSoonElements != null && comingSoonElements.size() > 0) {
                    continue;
                }

                GenelUrunModel genelUrunModel = urunHashMap.get(turkcellNo);
                String fiyatStr = "";

                Elements footElements = element.getElementsByClass("m-p-pc__foot");
                Elements secondaryPriceElements = footElements.get(0).getElementsByClass("m-p-pc__price--secondary");
                if (secondaryPriceElements != null && secondaryPriceElements.size() > 0) {
                    fiyatStr = secondaryPriceElements.get(0).childNodes().get(0).toString().trim().split(",")[0].replace(".", "");
                } else {
                    Elements primaryPriceElements = footElements.get(0).getElementsByClass("m-p-pc__price--primary");
                    fiyatStr = primaryPriceElements.get(0).childNodes().get(0).toString().trim().split(",")[0].replace(".", "").split(",")[0];
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
                     yeniUrunHashMap.put(turkcellNo, yeniUrunModel);

                    if (!ilkMi) {
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                turkcellKategori.getKategoriAdi() + "%0A" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + model + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + urunUrl + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, turkcellKategori, model, 0L, yeniFiyat.longValue());
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
                                    turkcellKategori.getKategoriAdi() + "%0A" +
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

                            telegramMesajGonder(mesaj, turkcellKategori, model, indYuzde.longValue(), yeniFiyat.longValue());
                            genelUrunModel.setSonMesajTarihi(new Date());
                        }

                    }
                     genelUrunModel.setFiyat(yeniFiyat);
                     if (yeniFiyat < genelUrunModel.getMinFiyat()) {
                         genelUrunModel.setMinFiyat(yeniFiyat);
                         genelUrunModel.setMinFiyatTarihi(new Date());
                     }

                     yeniUrunHashMap.put(turkcellNo, genelUrunModel);
                }
            }

            if (pageNumber < maxPageNumber) {
                yeniUrunHashMap = kategoriIleUrunBul(turkcellKategori, 0, pageNumber + 1, maxPageNumber, urunHashMap, yeniUrunHashMap, ilkMi);
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            logService.turkcellLogYaz("TURKCELL Hata");
            Thread.sleep(3000L);
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, TurkcellKategori turkcellKategori, String urunId, Long indYuzde, Long fiyat) throws IOException, InterruptedException {
        List<TurkcellTelegramConf> turkcellTelegramConfList = turkcellTelegramConfRepository.findByTurkcellKategori(turkcellKategori);

        for (TurkcellTelegramConf turkcellTelegramConf : turkcellTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, turkcellTelegramConf.getTelegramId(), urunId, "5917313476:AAEFnOAp6xWFmI-a1Y4G8PJO1QGfKUqVSds");
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

        if (fiyat <= 50) {
            genelService.telegramFiyatHatasiGonder(mesaj, urunId);
        }
    }
}