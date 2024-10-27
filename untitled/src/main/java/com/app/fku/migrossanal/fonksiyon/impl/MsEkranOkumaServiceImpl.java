package com.app.fku.migrossanal.fonksiyon.impl;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.migrossanal.entity.MsKategori;
import com.app.fku.migrossanal.entity.MsTelegramConf;
import com.app.fku.migrossanal.fonksiyon.service.MsEkranOkumaService;
import com.app.fku.migrossanal.fonksiyon.service.MsGenelService;
import com.app.fku.migrossanal.repository.MsTelegramConfRepository;
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
public class MsEkranOkumaServiceImpl implements MsEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    MsGenelService msGenelService;

    @Autowired
    MsTelegramConfRepository msTelegramConfRepository;


    public String chromeDriverPath = "C:\\GoogleChromeDriver\\chromedriver_win32\\chromedriver.exe";
    public Long BEKLEME_SURESI_20SN = 20000L;
    public Long BEKLEME_SURESI_10SN = 10000L;
    public Long BEKLEME_SURESI_6SN = 6000L;
    public Long BEKLEME_SURESI_5SN = 5000L;
    public Long BEKLEME_SURESI_4SN = 4000L;
    public Long BEKLEME_SURESI_3SN = 3000L;
    public Long BEKLEME_SURESI_2SN = 2000L;
    public Long BEKLEME_SURESI_1SN = 1000L;

    @Override
    public HashMap<String, GenelUrunModel> kategoriIleUrunBul(MsKategori msKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        if (yeniUrunHashMap == null) {
            yeniUrunHashMap = new HashMap<>();
        }

        try {
            Document msDoc = null;

            for (int i = 1; i > 0; i++) {

                try {
                    if (msKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                        msDoc = msGenelService.urleGit(msKategori.getSayfaAdresi() + "&sayfa=" + pageNumber);
                    } else {
                        msDoc = msGenelService.urleGit(msKategori.getSayfaAdresi() + "?sayfa=" + pageNumber);
                    }

                    if (msDoc == null) {
                        System.out.println("MigrosSanal Hata aAA 1");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("MigrosSanal Hata aAA 2");
                }
                Thread.sleep(10000L);
            }


            if (maxPageNumber == 1) {
                String pageStr = "";
                try {
                    pageStr = msDoc.getElementById("category").getElementsByClass("cf").get(0).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString();
                } catch (Exception e) {
                    pageStr = msDoc.getElementById("category").getElementsByClass("cf").get(1).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString();
                }

                pageStr = pageStr.split("\\(")[1].split("\\)")[0];
                int urunSayisi = Integer.valueOf(pageStr);
                maxPageNumber = (urunSayisi / 36) + 1;
            }

            Elements productElements = msDoc.getElementsByClass("product-wrapper");

            for (Element element: productElements) {
                Attributes productAttr = element.attributes();
                String mmNo = productAttr.get("data-modelnumber");

                GenelUrunModel genelUrunModel = urunHashMap.get(mmNo);
                String model = "";
                try {
                    model = element.getElementsByClass("content").get(0).childNodes().get(3).childNodes().get(1).childNodes().get(0).toString();
                } catch (Exception e) {
                    model = element.getElementsByClass("content").get(0).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString();
                }

                if (model.contains("Outlet")) {
                    continue;
                }

                Elements fiyatElements = element.getElementsByClass("product-price alt");
                if (fiyatElements == null ||fiyatElements.size() == 0) {
                    continue;
                }
                String fiyatStr = element.getElementsByClass("product-price alt").get(0).childNodes().get(1).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString().trim().split(",")[0];
                Double yeniFiyat = Double.valueOf(fiyatStr);

                if (genelUrunModel == null) {
                    //yeni gelmiş ürün
                    GenelUrunModel yeniUrunModel = new GenelUrunModel();
                    yeniUrunModel.setModel(model);
                    yeniUrunModel.setFiyat(yeniFiyat);
                    yeniUrunModel.setMinFiyat(yeniFiyat);
                    yeniUrunModel.setMinFiyatTarihi(new Date());
                    yeniUrunModel.setSonMesajTarihi(new Date());
                    urunHashMap.put(mmNo, yeniUrunModel);

                    if (!ilkMi) {
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                //mmKategori.getKategoriAdi() + "%0A" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + model + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + "https://www.mediamarkt.com.tr/tr/product/a-" + mmNo + ".html %0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, msKategori, mmNo);
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

                            if (indYuzde > 5d) {
                                String mesaj = "" +
                                        msKategori.getKategoriAdi() + "%0A" +
                                        "Ürün Adı: " + model + "%0A" +
                                        "Eski Fiyat: " + nf.format(genelUrunModel.getFiyat()) + "%0A" +
                                        "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                        "Min Fiyat: " + nf.format(genelUrunModel.getMinFiyat()) + "%0A" +
                                        "Min Fiyat Tarihi: " + sdf.format(genelUrunModel.getMinFiyatTarihi()) + "%0A" +
                                        "İndirim: " + nf.format(indirim) + "%0A" +
                                        "İndirim Yüzde: " + nf.format(indYuzde) + "%0A" +
                                        "Ürün Link:" + "https://www.mediamarkt.com.tr/tr/product/a-" + mmNo + ".html %0A" +
                                        "Tarih: " + sdf.format(new Date()) + "%0A" +
                                        "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                        "***Generated By Emin KAYABASI***";

                                telegramMesajGonder(mesaj, msKategori, mmNo);
                                genelUrunModel.setSonMesajTarihi(new Date());
                            }
                        }
                    }

                    genelUrunModel.setFiyat(yeniFiyat);
                    if (yeniFiyat < genelUrunModel.getMinFiyat()) {
                        genelUrunModel.setMinFiyat(yeniFiyat);
                        genelUrunModel.setMinFiyatTarihi(new Date());
                    }

                    urunHashMap.remove(mmNo);
                    urunHashMap.put(mmNo, genelUrunModel);
                }
            }

            if (pageNumber < maxPageNumber) {
                urunHashMap = kategoriIleUrunBul(msKategori, 0, pageNumber + 1, maxPageNumber, urunHashMap, yeniUrunHashMap, ilkMi);
            }
        } catch (Exception e) {
            Thread.sleep(3000L);
            System.out.println("MigrosSanal Hata");
            if (hataCount < 10) {
                kategoriIleUrunBul(msKategori, hataCount++, pageNumber + 1, 1, urunHashMap, yeniUrunHashMap, ilkMi);
            }
        }
        return urunHashMap;
    }

    private void telegramMesajGonder(String mesaj, MsKategori msKategori, String urunId) throws IOException, InterruptedException {
        List<MsTelegramConf> msTelegramConfList = msTelegramConfRepository.findByMsKategori(msKategori);

        for (MsTelegramConf msTelegramConf : msTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, msTelegramConf.getTelegramId(), urunId, "5872630140:AAH9qO662vDAAJAZtzdRxOwkLFpp4bsvYY0");
        }
    }
}