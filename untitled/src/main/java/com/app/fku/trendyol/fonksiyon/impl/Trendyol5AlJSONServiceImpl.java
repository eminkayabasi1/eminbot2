package com.app.fku.trendyol.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
import com.app.fku.trendyol.fonksiyon.service.Trendyol5AlJSONService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolGenelService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolJSONService;
import com.app.fku.trendyol.model.TyGenelModel;
import com.app.fku.trendyol.model.TyResultModel;
import com.app.fku.trendyol.model.TyUrunModel;
import com.app.fku.trendyol.model.TyUrunPromotionModel;
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
public class Trendyol5AlJSONServiceImpl implements Trendyol5AlJSONService {

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

        urlList.add(new LinkModel("https://apigw.trendyol.com/discovery-web-searchgw-service/v2/api/infinite-scroll/sr?mid=107950&os=1&pi=1", 0.95d));//


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
            for (LinkModel linkModel : urlList) {
                TyResultModel tyResultModel = readJsonFromUrl(linkModel.getUrl());
                Integer piCount = tyResultModel.getTotalCount() / 24;
                piCount = piCount + 1;
                List<TyUrunModel> tyUrunModelList = tyResultModel.getProducts();
                for (TyUrunModel tyUrunModel : tyUrunModelList) {
                    if (tyUrunModel.getPromotions() != null) {
                        for (TyUrunPromotionModel promotionModel: tyUrunModel.getPromotions()) {
                            if (promotionModel.getName() != null && promotionModel.getName().contains("5 Al")) {
                                telegramMesajGonder("Dermoten 5 Al 1 Öde Var\n https://www.trendyol.com" + tyUrunModel.getUrl(), "-4617324498", tyUrunModel.getId());
                                Thread.sleep(2000L);
                            }
                        }
                    }
                }

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
            //url = url + "&" + gen.nextString() + "=" + gen.nextString();
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
