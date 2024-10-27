package com.app.fku.yenimediamarkt.fonksiyon.impl;

import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
import com.app.fku.vatan.fonksiyon.service.VatanService;
import com.app.fku.vatan.model.VatanUrunModel;
import com.app.fku.yenimediamarkt.fonksiyon.service.MediaMarktService;
import com.app.fku.yenimediamarkt.model.MediaMarktUrunModel;
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
public class MediaMarktServiceImpl implements MediaMarktService {

    private static final List<LinkModel> urlList = new ArrayList<>();

    public void sorgula() throws InterruptedException {
        urlList.add(new LinkModel("https://www.mediamarkt.com.tr/tr/category/_s%C3%BCp%C3%BCrgeler-465738.html?searchParams=&sort=price&view=&page=", 0.97d, "Süpürgeler"));//Süpürgeler
        urlList.add(new LinkModel("https://www.mediamarkt.com.tr/tr/category/_kahve-%C3%A7ay-806537.html?searchParams=&sort=price&view=&page=", 0.97d, "Kahve Çay"));//Kahve Çay
        urlList.add(new LinkModel("https://www.mediamarkt.com.tr/tr/category/_sa%C3%A7-bak%C4%B1m-675547.html?searchParams=&sort=price&view=&page=", 0.97d, "Saç Bakım"));//Saç Bakım
        for (; ; ) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("hata geldi mm");
            }
        }
    }

    private static void anaislem() throws InterruptedException, IOException {
        HashMap<String, MediaMarktUrunModel> urunHashMap = new HashMap<>();
        boolean ilkTur = true;
        for (; ; ) {

            List<MediaMarktUrunModel> topluUrunList = new ArrayList<>();
            for (LinkModel linkModel : urlList) {
                Document doc = readJsonFromUrl(linkModel.getUrl() + "1");
                Integer piCount = Integer.valueOf(doc.getElementsByClass("cf").get(0).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString().replace("(", "").replace(")", "")) / 36;
                piCount = piCount + 1;
                Elements urunElements = doc.getElementsByClass("products-list").get(0).getElementsByClass("product-wrapper");
                for (Element urunElement : urunElements) {
                    MediaMarktUrunModel mediaMarktUrunModel = new MediaMarktUrunModel();
                    String urunId = urunElement.attributes().get("data-modelnumber").trim();
                    mediaMarktUrunModel.setUrl("https://www.mediamarkt.com.tr/tr/product/a-" + urunId + ".html");
                    mediaMarktUrunModel.setUrunId(urunId);
                    mediaMarktUrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("price-box").get(0).childNodes().get(1).childNodes().get(0).toString().trim().split(",")[0]));
                    mediaMarktUrunModel.setUrunAdi(urunElement.childNodes().get(1).childNodes().get(1).childNodes().get(1).childNodes().get(1).attributes().get("alt"));
                    mediaMarktUrunModel.setKategoriAdi(linkModel.getKategoriAdi());
                    mediaMarktUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                    topluUrunList.add(mediaMarktUrunModel);
                }

                for (int i = 2; i <= piCount; i++) {
                    doc = readJsonFromUrl(linkModel.getUrl() + i);
                    urunElements = doc.getElementsByClass("products-list").get(0).getElementsByClass("product-wrapper");
                    for (Element urunElement : urunElements) {
                        MediaMarktUrunModel mediaMarktUrunModel = new MediaMarktUrunModel();
                        String urunId = urunElement.attributes().get("data-modelnumber").trim();
                        mediaMarktUrunModel.setUrl("https://www.mediamarkt.com.tr/tr/product/a-" + urunId + ".html");
                        mediaMarktUrunModel.setUrunId(urunId);
                        mediaMarktUrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("price-box").get(0).childNodes().get(1).childNodes().get(0).toString().trim().split(",")[0]));
                        mediaMarktUrunModel.setUrunAdi(urunElement.childNodes().get(1).childNodes().get(1).childNodes().get(1).childNodes().get(1).attributes().get("alt"));
                        mediaMarktUrunModel.setKategoriAdi(linkModel.getKategoriAdi());
                        mediaMarktUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                        topluUrunList.add(mediaMarktUrunModel);
                    }
                }
            }
            for (MediaMarktUrunModel mediaMarktUrunModel : topluUrunList) {
                MediaMarktUrunModel eskiMediaMarktUrunModel = urunHashMap.get(mediaMarktUrunModel.getUrunId());
                if (eskiMediaMarktUrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (mediaMarktUrunModel.getFiyat() < eskiMediaMarktUrunModel.getFiyat() * mediaMarktUrunModel.getIndirimOrani() && !ilkTur) {
                        String mesaj = "" +
                                "İndirim%0A" +
                                "" + mediaMarktUrunModel.getKategoriAdi() + "%0A" +
                                "" + mediaMarktUrunModel.getUrunAdi() + "%0A" +
                                "Eski Fiyat: " + eskiMediaMarktUrunModel.getFiyat() + "%0A" +
                                "Yeni Fiyat: " + mediaMarktUrunModel.getFiyat() + "%0A" +
                                "Link:" + mediaMarktUrunModel.getUrl();
                        telegramMesajGonder(mesaj, "-4252962534");
                        if (mediaMarktUrunModel.getUrunAdi().contains("DYSON") || mediaMarktUrunModel.getUrunAdi().contains("dyson") || mediaMarktUrunModel.getUrunAdi().contains("Dyson")) {
                            telegramMesajGonder(mesaj, "-4149448403");
                        }
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün%0A" +
                                "" + mediaMarktUrunModel.getKategoriAdi() + "%0A" +
                                "" + mediaMarktUrunModel.getUrunAdi() + "%0A" +
                                "Fiyat: " + mediaMarktUrunModel.getFiyat() + "%0A" +
                                "Link:" + mediaMarktUrunModel.getUrl();
                        telegramMesajGonder(mesaj, "-4252962534");
                        if (mediaMarktUrunModel.getUrunAdi().contains("DYSON") || mediaMarktUrunModel.getUrunAdi().contains("dyson") || mediaMarktUrunModel.getUrunAdi().contains("Dyson")) {
                            telegramMesajGonder(mesaj, "-4149448403");
                        }
                    }
                }

                urunHashMap.put(mediaMarktUrunModel.getUrunId(), mediaMarktUrunModel);
            }

            ilkTur = false;
            System.out.println("MediaMarkt: " + urunHashMap.size() + "\n");
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
