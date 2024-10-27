package com.app.fku.mutfakdunyasi.fonksiyon.impl;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.mutfakdunyasi.entity.MdKategori;
import com.app.fku.mutfakdunyasi.entity.MdTelegramConf;
import com.app.fku.mutfakdunyasi.fonksiyon.service.MdEkranOkumaService;
import com.app.fku.mutfakdunyasi.fonksiyon.service.MdGenelService;
import com.app.fku.mutfakdunyasi.repository.MdTelegramConfRepository;
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
public class MdEkranOkumaServiceImpl implements MdEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    MdGenelService mdGenelService;

    @Autowired
    MdTelegramConfRepository mdTelegramConfRepository;

    @Override
    public HashMap<String, GenelUrunModel> kategoriIleUrunBul(MdKategori mdKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        if (yeniUrunHashMap == null) {
            yeniUrunHashMap = new HashMap<>();
        }

        try {
            Document mdDoc = null;
            for (int i = 1; i > 0; i++) {
                if (mdKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    mdDoc = mdGenelService.urleGit(mdKategori.getSayfaAdresi() + "&pg=" + pageNumber);
                } else {
                    mdDoc = mdGenelService.urleGit(mdKategori.getSayfaAdresi() + "?pg=" + pageNumber);
                }

                if (mdDoc == null) {
                    logService.mdLogYaz("MD Hata aAA");
                    Thread.sleep(4000);
                } else {
                    break;
                }
            }

            int urunSayisi = Integer.valueOf(mdDoc.getElementById("pagination-total").childNodes().get(0).toString());
            if (urunSayisi % 23 == 0) {
                maxPageNumber = (urunSayisi / 23);
            } else {
                maxPageNumber = (urunSayisi / 23) + 1;
            }

            Elements productElements = mdDoc.getElementsByClass("product-item");
            for (Element element: productElements) {
                Attributes productAttr = element.attributes();
                String mdNo = element.getElementsByClass("product-detail-card").get(0).getElementsByClass("product-title").get(0).attributes().get("href");
                String model = element.getElementsByClass("product-detail-card").get(0).getElementsByClass("product-title").get(0).childNodes().get(0).toString();
                String urunUrl = "https://www.mutfakdunyasi.com/" + element.getElementsByClass("product-detail-card").get(0).getElementsByClass("product-title").get(0).attributes().get("href");

                GenelUrunModel genelUrunModel = urunHashMap.get(mdNo);
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
                     yeniUrunHashMap.put(mdNo, yeniUrunModel);

                    if (!ilkMi) {
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                mdKategori.getKategoriAdi() + "%0A" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + model + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + urunUrl + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, mdKategori, model, 0L, yeniFiyat.longValue());
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
                                    mdKategori.getKategoriAdi() + "%0A" +
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

                            telegramMesajGonder(mesaj, mdKategori, model, indYuzde.longValue(), yeniFiyat.longValue());
                            genelUrunModel.setSonMesajTarihi(new Date());
                        }

                    }
                     genelUrunModel.setFiyat(yeniFiyat);
                     if (yeniFiyat < genelUrunModel.getMinFiyat()) {
                         genelUrunModel.setMinFiyat(yeniFiyat);
                         genelUrunModel.setMinFiyatTarihi(new Date());
                     }

                     yeniUrunHashMap.put(mdNo, genelUrunModel);
                }
            }

            if (pageNumber < maxPageNumber) {
                yeniUrunHashMap = kategoriIleUrunBul(mdKategori, 0, pageNumber + 1, maxPageNumber, urunHashMap, yeniUrunHashMap, ilkMi);
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            logService.mdLogYaz("MD Hata");
            Thread.sleep(3000L);
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, MdKategori mdKategori, String urunId, Long indYuzde, Long fiyat) throws IOException, InterruptedException {
        List<MdTelegramConf> mdTelegramConfList = mdTelegramConfRepository.findByMdKategori(mdKategori);

        for (MdTelegramConf mdTelegramConf : mdTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, mdTelegramConf.getTelegramId(), urunId, "5760735112:AAGMgkKqQG2Y1eZGPaymCnt9hthhPgONkOw");
        }
    }
}