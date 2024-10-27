package com.app.fku.dyson.fonksiyon.impl;

import com.app.fku.dyson.fonksiyon.service.DysonService;
import com.app.fku.dyson.model.DysonUrunModel;
import com.app.fku.genel.model.LinkModel;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DysonServiceImpl implements DysonService {

    private static final List<LinkModel> urlList = new ArrayList<>();

    public void sorgula() throws InterruptedException {
        urlList.add(new LinkModel("https://www.dyson.com.tr/products/vacuum-cleaners", 0.97d, "Süpürgeler"));//Süpürgeler
        urlList.add(new LinkModel("https://www.dyson.com.tr/products/hair-care", 0.97d, "Saç Bakımı"));//Saç Bakımı
        urlList.add(new LinkModel("https://www.dyson.com.tr/products/air-quality", 0.97d, "Hava Temizleyici"));//Hava Temizleyici
        for (;;) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("Dyson hata geldi");
            }
        }
    }

    private static void anaislem() throws InterruptedException, IOException {
        HashMap<String, DysonUrunModel> urunHashMap = new HashMap<>();

        boolean ilkTur = true;
        for (;;) {

            List<DysonUrunModel> topluUrunList = new ArrayList<>();
            for (LinkModel linkModel: urlList) {
                Document doc = readJsonFromUrl(linkModel.getUrl());

                Elements urunElements = doc.getElementsByClass("slider__item");

                for (Element urunElement: urunElements) {
                    if (urunElement.getElementsByClass("button--cart").size() > 0) {
                        DysonUrunModel dysonUrunModel = new DysonUrunModel();
                        dysonUrunModel.setUrl(urunElement.getElementsByClass("product-item-link").get(0).attributes().get("href"));
                        dysonUrunModel.setUrunId(urunElement.attributes().get("id"));
                        try{
                            dysonUrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("price-wrapper").get(0).attributes().get("data-price-amount")));
                        }catch (Exception e) {
                            try {
                                dysonUrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("price__is").get(0).childNodes().get(0).childNodes().get(0).toString().split(",")[0].replace(".", "")));
                            } catch (Exception e1) {
                                System.out.println("Dyson Fiyat Hata");
                            }
                        }
                        dysonUrunModel.setUrunAdi(urunElement.getElementsByClass("product-item-link").get(0).childNodes().get(0).toString());
                        topluUrunList.add(dysonUrunModel);
                    }
                }
            }

            for (DysonUrunModel dysonUrunModel: topluUrunList) {
                DysonUrunModel eskiDysonUrunModel = urunHashMap.get(dysonUrunModel.getUrunId());
                if (eskiDysonUrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (dysonUrunModel.getFiyat() < eskiDysonUrunModel.getFiyat() && !ilkTur) {
                        String mesaj = "" +
                                "İndirim%0A" +
                                "" + dysonUrunModel.getUrunAdi() + "%0A" +
                                "Eski Fiyat: " + eskiDysonUrunModel.getFiyat() + "%0A" +
                                "Yeni Fiyat: " + dysonUrunModel.getFiyat() + "%0A" +
                                "Link:" + dysonUrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4119021304");
                        telegramMesajGonder(mesaj, "-4106248789");
                        telegramMesajGonder(mesaj, "-4149448403");
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün%0A" +
                                "" + dysonUrunModel.getUrunAdi() + "%0A" +
                                "Fiyat: " + dysonUrunModel.getFiyat() + "%0A" +
                                "Link:" + dysonUrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4119021304");
                        telegramMesajGonder(mesaj, "-4106248789");
                        telegramMesajGonder(mesaj, "-4149448403");
                    }
                }

                urunHashMap.put(dysonUrunModel.getUrunId(), dysonUrunModel);
            }

            ilkTur = false;
            System.out.println("Dyson: " + urunHashMap.size() + "\n");
        }
    }

    public static Document readJsonFromUrl(String url) throws IOException, JSONException {
        //RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        //url = url + "&" + gen.nextString() + "=" + gen.nextString();

        return Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                .referrer("http://www.google.com")
                .timeout(12000)
                .followRedirects(true)
                .ignoreContentType(true)
                .get();
    }


    public static void telegramMesajGonder(String mesaj, String chatId) throws IOException, InterruptedException {
        //System.out.println(urunId + " " + chatId + " mesaj gönderdim. " + urunId);
        mesaj = mesaj.replace(" h", "xh");
        mesaj = mesaj.replace(" H", "xh");
        mesaj = mesaj.replace("ş", "s");
        mesaj = mesaj.replace("Ş", "s");
        mesaj = mesaj.replace("ı", "i");
        mesaj = mesaj.replace("İ", "i");
        mesaj = mesaj.replace("ğ", "g");
        mesaj = mesaj.replace("Ğ", "g");
        mesaj = mesaj.replace("ö", "o");
        mesaj = mesaj.replace("Ö", "o");
        mesaj = mesaj.replace("Ç", "c");
        mesaj = mesaj.replace("ç", "c");
        mesaj = mesaj.replace("Ü", "u");
        mesaj = mesaj.replace("ü", "u");
        mesaj = mesaj.replace("&", "");
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";    //Add Telegram token//
        String token = "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM";
        String text = mesaj;
        urlString = String.format(urlString, token, chatId, text);
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            Thread.sleep(10000L);
        }
    }


}
