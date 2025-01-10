package com.app.fku.trendyol.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
import com.app.fku.trendyol.fonksiyon.service.TrendyolGenelService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolJSONService;
import com.app.fku.trendyol.model.TyGenelModel;
import com.app.fku.trendyol.model.TyResultModel;
import com.app.fku.trendyol.model.TyUrunModel;
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
public class TrendyolJSONServiceImpl implements TrendyolJSONService {

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
        /**
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=101939%2C102900%2C794%2C103009%2C577%2C626%2C325%2C326%2C786%2C392%2C102989%2C373&wc=1104&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//Küçük Ev Aletleri
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wc=109453&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//Robot Süpürge
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=101470%2C101939%2C103505%2C794%2C143668%2C105334%2C102900&wc=144430&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//Giyilebilir Teknoloji
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=794%2C101939%2C101470%2C103505%2C109251%2C108166%2C145557%2C102323&wc=104025&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//Telefon
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=102323%2C101849%2C101606%2C794%2C104964%2C102324%2C103502%2C107655%2C108166%2C577%2C101470%2C103505&wc=103660&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//Bilgisayar Tablet
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=577%2C633%2C102989%2C392%2C387%2C101939%2C104971&wc=103109&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//Kişisel Bakım
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wc=1354&wb=326&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//Mutfak Tefal
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=103138&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//Stanley Termos
        */

        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wc=1104&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wc=144430&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wc=104025&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wc=108656%2C103665&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wc=103109&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=325%2C651%2C326%2C105973%2C653%2C103138&wc=1354&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=103138%2C325%2C326&wc=104593&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=104725%2C104764%2C467&wc=90&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//
        urlList.add(new LinkModel("https://public.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?wb=109092&wc=164208&mb=kurumsal_satici&sst=PRICE_BY_ASC", 0.95d));//


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
                    if (tyUrunModel.getImageAlt().contains("Yenilenmiş")
                            || tyUrunModel.getUrl().contains("merchantId=115015")//EasyCep
                            || tyUrunModel.getUrl().contains("merchantId=106773")//HesapKitap
                            || tyUrunModel.getImageAlt().contains(" Fincan ")
                            || tyUrunModel.getImageAlt().contains(" Kupa ")
                            || tyUrunModel.getImageAlt().contains(" Porselen ")
                    ) {
                        continue;
                    }
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
                        if (tyUrunModel.getImageAlt().contains("Yenilenmiş")
                                || tyUrunModel.getUrl().contains("merchantId=115015")//EasyCep
                                || tyUrunModel.getUrl().contains("merchantId=106773")//HesapKitap
                                || tyUrunModel.getImageAlt().contains(" Fincan ")
                                || tyUrunModel.getImageAlt().contains(" Kupa ")
                                || tyUrunModel.getImageAlt().contains(" Porselen ")
                        ) {
                            continue;
                        }
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
                                "Link:https://www.trendyol.com/" + tyUrunModel.getUrl();

                        if (tyUrunModel.getImageAlt().contains("Dyson") || tyUrunModel.getImageAlt().contains("DYSON") || tyUrunModel.getImageAlt().contains("dyson")) {
                            telegramMesajGonder(mesaj, "-4149448403", tyUrunModel.getId());
                        } else if (tyUrunModel.getImageAlt().contains("Stanley") || tyUrunModel.getImageAlt().contains("STANLEY") || tyUrunModel.getImageAlt().contains("stanley")) {
                            telegramMesajGonder(mesaj, "-4506545515", tyUrunModel.getId());
                        } else if (tyUrunModel.getImageAlt().contains("Lego") || tyUrunModel.getImageAlt().contains("LEGO") || tyUrunModel.getImageAlt().contains("lego")) {
                            telegramMesajGonder(mesaj, "-4654789852", tyUrunModel.getId());
                        }

                        telegramMesajGonder(mesaj, "-4162270115", tyUrunModel.getId());
                        telegramMesajGonder(mesaj, "-4504951480", tyUrunModel.getId());

                        if (tyUrunModel.getPrice().getSellingPrice() < eskiTyUrunModel.getPrice().getSellingPrice() * 0.70d) {
                            telegramMesajGonder(mesaj, "-4654089282", tyUrunModel.getId());
                        }
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
                                "Link:https://www.trendyol.com/" + tyUrunModel.getUrl();

                        if (tyUrunModel.getImageAlt().contains("Dyson") || tyUrunModel.getImageAlt().contains("DYSON") || tyUrunModel.getImageAlt().contains("dyson")) {
                            telegramMesajGonder(mesaj, "-4149448403", tyUrunModel.getId());
                        } else if (tyUrunModel.getImageAlt().contains("Stanley") || tyUrunModel.getImageAlt().contains("STANLEY") || tyUrunModel.getImageAlt().contains("stanley")) {
                            telegramMesajGonder(mesaj, "-4506545515", tyUrunModel.getId());
                        } else if (tyUrunModel.getImageAlt().contains("Lego") || tyUrunModel.getImageAlt().contains("LEGO") || tyUrunModel.getImageAlt().contains("lego")) {
                            telegramMesajGonder(mesaj, "-4654789852", tyUrunModel.getId());
                        }

                        telegramMesajGonder(mesaj, "-4162270115", tyUrunModel.getId());
                        telegramMesajGonder(mesaj, "-4504951480", tyUrunModel.getId());
                    }
                }

                urunHashMap.put(tyUrunModel.getId(), tyUrunModel);
            }

            ilkTur = false;
            //urunHashMap = yeniUrunHashMap;
            System.out.println("TY: " + urunHashMap.size() + " : " + sdf.format(new Date()) + "\n");
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
            url = url + "&" + gen.nextString() + "=" + gen.nextString();
            String json = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                    .referrer("http://www.google.com")
                    .timeout(12000)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .execute()
                    .body();
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
