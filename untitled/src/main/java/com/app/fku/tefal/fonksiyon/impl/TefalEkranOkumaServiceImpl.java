package com.app.fku.tefal.fonksiyon.impl;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.tefal.entity.TefalKategori;
import com.app.fku.tefal.entity.TefalTelegramConf;
import com.app.fku.tefal.fonksiyon.service.TefalEkranOkumaService;
import com.app.fku.tefal.fonksiyon.service.TefalGenelService;
import com.app.fku.tefal.repository.TefalTelegramConfRepository;
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
public class TefalEkranOkumaServiceImpl implements TefalEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    TefalGenelService tefalGenelService;

    @Autowired
    TefalTelegramConfRepository tefalTelegramConfRepository;

    @Override
    public HashMap<String, GenelUrunModel> kategoriIleUrunBul(TefalKategori tefalKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        if (yeniUrunHashMap == null) {
            yeniUrunHashMap = new HashMap<>();
        }

        try {

            Document tefalDoc = null;
            for (int i = 1; i > 0; i++) {
                if (tefalKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    tefalDoc = tefalGenelService.urleGit(tefalKategori.getSayfaAdresi() + "&page=" + pageNumber);
                } else {
                    tefalDoc = tefalGenelService.urleGit(tefalKategori.getSayfaAdresi() + "?page=" + pageNumber);
                }

                if (tefalDoc == null) {
                    logService.tefalLogYaz("TEFAL Hata aAA");
                    Thread.sleep(4000);
                } else {
                    break;
                }
            }

            int urunSayisi = Integer.valueOf(tefalDoc.getElementsByClass("filter__count").get(0).childNodes().get(1).childNodes().get(0).toString().trim());
            if (urunSayisi % 24 == 0) {
                maxPageNumber = (urunSayisi / 24);
            } else {
                maxPageNumber = (urunSayisi / 24) + 1;
            }

            Elements productElements = tefalDoc.getElementsByClass("js-product-wrapper");
            for (Element element: productElements) {
                Attributes productAttr = element.attributes();
                String tefalNo = productAttr.get("data-pk");
                String model = productAttr.get("data-name");
                String urunUrl = "https://www.tefal.com.tr" + productAttr.get("data-url");

                GenelUrunModel genelUrunModel = urunHashMap.get(tefalNo);
                 String fiyatStr = "";
                 if (element.getElementsByClass("product-wrapper__basket-offers").get(0).childNodes().size() > 1) {
                     //sepet fiyatı var
                     fiyatStr = element.getElementsByClass("product-wrapper__basket-offers").get(0).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString().split("\\.")[0].trim();
                 } else {
                     //sepet fiyatı yok
                     fiyatStr = productAttr.get("data-price").split("\\.")[0].trim();
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
                     yeniUrunHashMap.put(tefalNo, yeniUrunModel);

                    if (!ilkMi) {
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                tefalKategori.getKategoriAdi() + "%0A" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + model + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + urunUrl + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, tefalKategori, model, 0L, yeniFiyat.longValue());
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
                                    tefalKategori.getKategoriAdi() + "%0A" +
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

                            telegramMesajGonder(mesaj, tefalKategori, model, indYuzde.longValue(), yeniFiyat.longValue());
                            genelUrunModel.setSonMesajTarihi(new Date());
                        }

                    }
                     genelUrunModel.setFiyat(yeniFiyat);
                     if (yeniFiyat < genelUrunModel.getMinFiyat()) {
                         genelUrunModel.setMinFiyat(yeniFiyat);
                         genelUrunModel.setMinFiyatTarihi(new Date());
                     }

                     yeniUrunHashMap.put(tefalNo, genelUrunModel);
                }
            }

            if (pageNumber < maxPageNumber) {
                yeniUrunHashMap = kategoriIleUrunBul(tefalKategori, 0, pageNumber + 1, maxPageNumber, urunHashMap, yeniUrunHashMap, ilkMi);
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            logService.tefalLogYaz("TEFAL Hata");
            Thread.sleep(3000L);
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, TefalKategori tefalKategori, String urunId, Long indYuzde, Long fiyat) throws IOException, InterruptedException {
        List<TefalTelegramConf> tefalTelegramConfList = tefalTelegramConfRepository.findByTefalKategori(tefalKategori);

        for (TefalTelegramConf tefalTelegramConf : tefalTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, tefalTelegramConf.getTelegramId(), urunId, "5881026289:AAFqCmragnhNv2BiwmKeI5nFPaafBsk4ILg");
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