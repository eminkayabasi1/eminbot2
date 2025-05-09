package com.app.fku.trendyol.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
import com.app.fku.trendyol.fonksiyon.service.TrendyolGenelService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolJSONService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolTYJSONService;
import com.app.fku.trendyol.model.TyGenelModel;
import com.app.fku.trendyol.model.TyResultModel;
import com.app.fku.trendyol.model.TyUrunModel;
import com.app.fku.trendyol.model.TyUrunStampModel;
import com.app.fku.trendyol.repository.TrendyolTelegramConfRepository;
import com.app.fku.trendyol.repository.TyIstatistikRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.openqa.selenium.json.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TrendyolTYJSONServiceImpl implements TrendyolTYJSONService {

    @Autowired
    TrendyolGenelService tyGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    TrendyolTelegramConfRepository trendyolTelegramConfRepository;

    @Autowired
    TyIstatistikRepository tyIstatistikRepository;

    private final List<LinkModel> urlList = new ArrayList<>();
    private final HashMap<String, Date> mesajGonderimList = new HashMap<>();

    @Override
    public void sorgula() throws IOException, InterruptedException {
        urlList.add(new LinkModel("https://apigw.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=392%2C794%2C325%2C326%2C651%2C105973%2C373%2C577%2C401%2C102900&mid=968&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.97d, "-4719689695"));//


        for (; ; ) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("hata geldi ty");
            }
        }
    }

    private void anaislem() throws InterruptedException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        HashMap<String, TyUrunModel> urunHashMap = new HashMap<>();
        boolean ilkTur = true;
        for (; ; ) {
            List<TyUrunModel> topluUrunList = new ArrayList<>();
            //HashMap<String, TyUrunModel> yeniUrunHashMap = new HashMap<>();
            for (LinkModel linkModel : urlList) {
                TyResultModel tyResultModel = readJsonFromUrl(linkModel.getUrl() + "&pi=1");
                Integer piCount = tyResultModel.getTotalCount() / 24;
                piCount = piCount + 1;
                List<TyUrunModel> tyUrunModelList = tyResultModel.getProducts();
                for (TyUrunModel tyUrunModel : tyUrunModelList) {
                    /**
                    if (tyUrunModel.getImageAlt().contains("Yenilenmiş")
                            || tyUrunModel.getUrl().contains("merchantId=115015")//EasyCep
                            || tyUrunModel.getUrl().contains("merchantId=106773")//HesapKitap
                            || tyUrunModel.getImageAlt().contains(" Fincan ")
                            || tyUrunModel.getImageAlt().contains(" Kupa ")
                            || tyUrunModel.getImageAlt().contains(" Porselen ")
                            || tyUrunModel.getCategoryName().equals("Kapak & Kılıf")
                            || tyUrunModel.getCategoryName().equals("Kahve Fincanı")
                            || tyUrunModel.getCategoryName().equals("Kupa")
                            || tyUrunModel.getCategoryName().equals("Tepsi")
                            || tyUrunModel.getCategoryName().equals("Ekmek Kutusu ve Sepeti")
                            || tyUrunModel.getCategoryName().equals("Bardak")
                            || tyUrunModel.getCategoryName().equals("Peçetelik")
                            || tyUrunModel.getCategoryName().equals("Tabak")
                            || tyUrunModel.getCategoryName().equals("Kase")
                    ) {
                        continue;
                    }*/
                    tyUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                    if (tyUrunModel.getCollectableCouponDiscount() != null && tyUrunModel.getCollectableCouponDiscount() > 0) {
                        tyUrunModel.getPrice().setSellingPrice(tyUrunModel.getPrice().getSellingPrice() - tyUrunModel.getCollectableCouponDiscount());
                    }
                    topluUrunList.add(tyUrunModel);
                }

                for (int i = 2; i <= piCount; i++) {
                    tyResultModel = readJsonFromUrl(linkModel.getUrl() + "&pi=" + i);
                    tyUrunModelList = tyResultModel.getProducts();
                    for (TyUrunModel tyUrunModel : tyUrunModelList) {
                        /**
                        if (tyUrunModel.getImageAlt().contains("Yenilenmiş")
                                || tyUrunModel.getUrl().contains("merchantId=115015")//EasyCep
                                || tyUrunModel.getUrl().contains("merchantId=106773")//HesapKitap
                                || tyUrunModel.getImageAlt().contains(" Fincan ")
                                || tyUrunModel.getImageAlt().contains(" Kupa ")
                                || tyUrunModel.getImageAlt().contains(" Porselen ")
                                || tyUrunModel.getCategoryName().equals("Kapak & Kılıf")
                                || tyUrunModel.getCategoryName().equals("Kahve Fincanı")
                                || tyUrunModel.getCategoryName().equals("Kupa")
                                || tyUrunModel.getCategoryName().equals("Tepsi")
                                || tyUrunModel.getCategoryName().equals("Ekmek Kutusu ve Sepeti")
                                || tyUrunModel.getCategoryName().equals("Bardak")
                                || tyUrunModel.getCategoryName().equals("Peçetelik")
                                || tyUrunModel.getCategoryName().equals("Tabak")
                                || tyUrunModel.getCategoryName().equals("Kase")
                        ) {
                            continue;
                        }*/
                        tyUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                        if (tyUrunModel.getCollectableCouponDiscount() != null && tyUrunModel.getCollectableCouponDiscount() > 0) {
                            tyUrunModel.getPrice().setSellingPrice(tyUrunModel.getPrice().getSellingPrice() - tyUrunModel.getCollectableCouponDiscount());
                        }
                        topluUrunList.add(tyUrunModel);
                    }
                }
            }

            for (TyUrunModel tyUrunModel : topluUrunList) {
                TyUrunModel eskiTyUrunModel = urunHashMap.get(tyUrunModel.getId());
                if (eskiTyUrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (tyUrunModel.getPrice().getSellingPrice() < eskiTyUrunModel.getPrice().getSellingPrice() * eskiTyUrunModel.getIndirimOrani() && !ilkTur) {
                        String mesaj = "" +
                                "İndirim\n" +
                                "" + tyUrunModel.getCategoryName() + "\n" +
                                "" + tyUrunModel.getImageAlt() + "\n" +
                                "Eski Fiyat: " + eskiTyUrunModel.getPrice().getSellingPrice() + "\n" +
                                "Yeni Fiyat: " + tyUrunModel.getPrice().getSellingPrice() + "\n" +
                                "Kuponlu Mu: " + tyUrunModel.getHasCollectableCoupon() + "\n" +
                                "Link:https://www.trendyol.com" + tyUrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4719689695", tyUrunModel.getId());
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün\n" +
                                "" + tyUrunModel.getCategoryName() + "\n" +
                                "" + tyUrunModel.getImageAlt() + "\n" +
                                "Fiyat Fiyat: " + tyUrunModel.getPrice().getSellingPrice() + "\n" +
                                "Kuponlu Mu: " + tyUrunModel.getHasCollectableCoupon() + "\n" +
                                "Link:https://www.trendyol.com" + tyUrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4719689695", tyUrunModel.getId());
                    }
                }

                urunHashMap.put(tyUrunModel.getId(), tyUrunModel);
            }

            ilkTur = false;
            //urunHashMap = yeniUrunHashMap;
            System.out.println("TYTY2: " + urunHashMap.size() + " : " + sdf.format(new Date()) + "\n");
        }
    }

    private void telegramMesajGonder(String mesaj, String chatId, String id) throws IOException, InterruptedException {
        genelService.telegramMesajGonder(mesaj, chatId, "1", "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM");
        mesajGonderimList.put(id, new Date());
    }

    public static TyResultModel readJsonFromUrl(String url) throws IOException, JsonException {
        TyResultModel tyResultModel = null;
        for (;;) {
            RandomString gen = new RandomString(8, ThreadLocalRandom.current());
            //url = url + "&" + gen.nextString() + "=" + gen.nextString();
            String json = "";
            for (;;) {
                json = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .followRedirects(true)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute()
                        .body();

                if (json != null && !json.equals("")) {
                    break;
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            TyGenelModel tyGenelModel = new TyGenelModel();
            tyGenelModel = mapper.readValue(json, TyGenelModel.class);

            tyResultModel = tyGenelModel.getResult();
            if (tyResultModel != null) {
                return  tyResultModel;
            }
        }
    }
}
