package com.app.fku.arzum.fonksiyon.impl;

import com.app.fku.arzum.fonksiyon.service.ArzumService;
import com.app.fku.arzum.model.ArzumUrunModel;
import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
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
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ArzumServiceImpl implements ArzumService {

    private static final List<LinkModel> urlList = new ArrayList<>();

    public void sorgula() throws InterruptedException {
        urlList.add(new LinkModel("https://www.arzum.com.tr/mutfak-aletleri?stock=1&sort=1&pg=", 1.00d, "MA"));//Mutfak Aletleri
        urlList.add(new LinkModel("https://www.arzum.com.tr/elektrikli-ev-aletleri?stock=1&sort=1&pg=", 1.00d, "EEA"));//Elektrikli Ev Aletleri
        urlList.add(new LinkModel("https://www.arzum.com.tr/kisisel-bakim-aletleri?stock=1&sort=1&pg=", 1.00d, "KBA"));//Kişisel Bakım Aletleri
        urlList.add(new LinkModel("https://www.arzum.com.tr/arzum-okka?stock=1&sort=1&pg=", 1.00d, "AO"));//Arzum OKKA
        for (; ; ) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("hata geldi vatan");
            }
        }
    }

    private static void anaislem() throws InterruptedException, IOException {
        HashMap<String, ArzumUrunModel> urunHashMap = new HashMap<>();

        boolean ilkTur = true;
        for (; ; ) {

            List<ArzumUrunModel> topluUrunList = new ArrayList<>();
            for (LinkModel linkModel : urlList) {
                Document doc = readJsonFromUrl(linkModel.getUrl() + "1");
                Integer piCount = 0;
                try {
                    piCount = Integer.valueOf(doc.getElementsByClass("pagination").get(0).getElementsByClass("last").get(0).attributes().get("href").toString().split("pg=")[1].split("&")[0]);
                } catch (Exception e) {
                    piCount = 1;
                }


                Elements urunElements = doc.getElementsByClass("product-item");

                for (Element urunElement : urunElements) {
                    ArzumUrunModel arzumUrunModel = new ArzumUrunModel();

                    arzumUrunModel.setUrl("https://www.arzum.com.tr" + urunElement.getElementsByClass("product-title").get(0).attributes().get("href"));
                    arzumUrunModel.setUrunId(urunElement.getElementsByClass("product-title").get(0).attributes().get("id"));
                    arzumUrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("product-price").get(0).childNodes().get(0).toString().replace(".", "").split(",")[0]));
                    arzumUrunModel.setUrunAdi(urunElement.getElementsByClass("product-title").get(0).childNodes().get(0).toString());
                    arzumUrunModel.setKategoriAdi(linkModel.getKategoriAdi());
                    arzumUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                    topluUrunList.add(arzumUrunModel);
                }

                for (int i = 2; i <= piCount; i++) {
                    doc = readJsonFromUrl(linkModel.getUrl() + i);
                    urunElements = doc.getElementsByClass("product-item");

                    for (Element urunElement : urunElements) {
                        ArzumUrunModel arzumUrunModel = new ArzumUrunModel();

                        arzumUrunModel.setUrl("https://www.arzum.com.tr" + urunElement.getElementsByClass("product-title").get(0).attributes().get("href"));
                        arzumUrunModel.setUrunId(urunElement.getElementsByClass("product-title").get(0).attributes().get("id"));
                        arzumUrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("product-price").get(0).childNodes().get(0).toString().replace(".", "").split(",")[0]));
                        arzumUrunModel.setUrunAdi(urunElement.getElementsByClass("product-title").get(0).childNodes().get(0).toString());
                        arzumUrunModel.setKategoriAdi(linkModel.getKategoriAdi());
                        arzumUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                        topluUrunList.add(arzumUrunModel);
                    }
                }
            }

            for (ArzumUrunModel arzumUrunModel : topluUrunList) {
                ArzumUrunModel eskiArzumUrunModel = urunHashMap.get(arzumUrunModel.getUrunId());
                if (eskiArzumUrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (arzumUrunModel.getFiyat() < eskiArzumUrunModel.getFiyat() * arzumUrunModel.getIndirimOrani() && !ilkTur) {
                        String mesaj = "" +
                                "İndirim%0A" +
                                "" + arzumUrunModel.getKategoriAdi() + "%0A" +
                                "" + arzumUrunModel.getUrunAdi() + "%0A" +
                                "Eski Fiyat: " + eskiArzumUrunModel.getFiyat() + "%0A" +
                                "Yeni Fiyat: " + arzumUrunModel.getFiyat() + "%0A" +
                                "Link:" + arzumUrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4570570201");
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün%0A" +
                                "" + arzumUrunModel.getKategoriAdi() + "%0A" +
                                "" + arzumUrunModel.getUrunAdi() + "%0A" +
                                "Fiyat: " + arzumUrunModel.getFiyat() + "%0A" +
                                "Link:" + arzumUrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4570570201");
                    }
                }

                urunHashMap.put(arzumUrunModel.getUrunId(), arzumUrunModel);
            }
            ilkTur = false;
            System.out.println("Arzum: " + urunHashMap.size() + "\n");
        }
    }

    public static Document readJsonFromUrl(String url) throws IOException, JSONException {
        RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        url = url + "&" + gen.nextString() + "=" + gen.nextString();

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
