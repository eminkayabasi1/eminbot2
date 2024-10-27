package com.app.fku.amazon.fonksiyon.threadclass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AmzWorkerManuelThread implements Runnable {
    public static HashMap<String, Long> urunHashMap = new HashMap<>();
    public static List<String> userAgentList = new ArrayList<>();
    public static int lastRandom;
    public static String lastUrl = "http://google.com";

    public void run() {
        System.out.println("Amz Worker Manuel Thread Ayağa Kalktı");

        for (int i = 1; i > 0; i++) {
            try {
                program();
            } catch (InterruptedException e) {
                System.out.println("Hata");
            }
        }
    }

    private static void program() throws InterruptedException {
        userAgentListDoldur();

        List<String> asinList = new ArrayList<>();

        //Macbook Pro
        asinList.add("B09JQV3N2M");//2021 Apple MacBook Pro (16 inç, 10 çekirdekli CPU’ya ve 32 çekirdekli GPU’ya sahip Apple M1 Max çip, 32 GB RAM, 1 TB SSD) - Gümüş Rengi
        asinList.add("B09JQMW5PQ");//2021 Apple MacBook Pro (16 inç, 10 çekirdekli CPU’ya ve 16 çekirdekli GPU’ya sahip Apple M1 Pro çip, 16 GB RAM, 512 GB SSD) - Uzay Grisi
        asinList.add("B09JQZ2QM7");//2021 Apple MacBook Pro (16 inç, 10 çekirdekli CPU’ya ve 16 çekirdekli GPU’ya sahip Apple M1 Pro çip, 16 GB RAM, 1 TB SSD) - Uzay Grisi
        asinList.add("B09JQTYZMP");//2021 Apple MacBook Pro (16 inç, 10 çekirdekli CPU’ya ve 16 çekirdekli GPU’ya sahip Apple M1 Pro çip, 16 GB RAM, 1 TB SSD) - Gümüş Rengi
        asinList.add("B08NGWW7B9");//2020 Apple Macbook Pro (13 inç, 8 Çekirdekli CPU'ya ve 8 Çekirdekli GPU'ya Sahip Apple M1 Çip, 8 GB RAM, 512 GB SSD) - Gümüş Rengi
        asinList.add("B0B3CPKR38");//M2 çipli 2022 Apple MacBook Pro laptop: 13 inç Retina ekran, 8GB RAM, 256 GB SSD ​​​​​​​depolama, Touch Bar, arkadan aydınlatmalı klavye, FaceTime HD kamera. Gümüş Rengi
        //Macbook Pro

        //Apple AirTag
        asinList.add("B0988271QZ");//Apple AirTag
        //Apple AirTag

        //AirFry
        asinList.add("B08KGDS4CZ");//Philips HD9252/90 Airfryer Fritöz
        asinList.add("B0767DNSMH");//Philips HD9650/90 Airfryer XXL Fritöz
        asinList.add("B092DS3RDL");//Philips HD9867/90 Airfryer XXL Smart Sensing Fritöz, Siyah
        asinList.add("B09TBGR44Q");//Philips HD9870/20 Airfryer XXL Smart Sensing Fritöz (Aksesuar Hediyeli)
        //AirFry

        /**
         urunHashMap.put("B09JQV3N2M", 555555L);
         urunHashMap.put("B09JQMW5PQ", 555555L);
         urunHashMap.put("B09JQZ2QM7", 555555L);
         urunHashMap.put("B09JQTYZMP", 555555L);
         urunHashMap.put("B08NGWW7B9", 555555L);
         urunHashMap.put("B0B3CPKR38", 555555L);
         urunHashMap.put("B0988271QZ", 555555L);
         urunHashMap.put("B0767DNSMH", 555555L);
         urunHashMap.put("B0767DNSMH", 555555L);
         urunHashMap.put("B092DS3RDL", 555555L);
         urunHashMap.put("B09TBGR44Q", 555555L);*/



        for(int i = 1; i > 0; i++) {
            Random rand = new Random();
            int sleepRand = rand.nextInt(6);
            TimeUnit.SECONDS.sleep(sleepRand);

            try {
                String asin = asinList.get(i % 11);
                Document doc = sayfayiOku(asin);
                Element titleElement = doc.getElementById("productTitle");
                String model = titleElement.childNodes().get(0).toString();
                Element fiyatDivElement = doc.getElementById("corePrice_feature_div");
                if (fiyatDivElement != null) {
                    String fiyat = fiyatDivElement.getElementsByClass("a-price-whole").get(0).childNodes().get(0).toString();
                    fiyat = fiyat.replace(".", "");
                    Long fiyatL = new Long(fiyat);
                    fiyatKarsilastir(asin, fiyatL, model);
                    //System.out.println(model + " " + fiyatL + " " + lastRandom + " " + sleepRand);
                } else {
                    Document fiyatDoc = sayfayiOku(asin + "/ref=olp-opf-redir?aod=1");
                    String fiyat = fiyatDoc.getElementsByClass("a-size-base a-color-price").get(0).childNodes().get(0).toString().split(",")[0].replace(".", "");
                    Long fiyatL = new Long(fiyat);
                    fiyatKarsilastir(asin, fiyatL, model);
                    //System.out.println(model + " " + fiyatL + " " + lastRandom + " " + sleepRand);
                }
                lastUrl = "https://www.amazon.com.tr/dp/" + asin;
            } catch (Exception e) {
                System.out.println("Aa " + lastRandom + " hata");
            }
        }
    }

    private static Document sayfayiOku(String asinNo) throws IOException {
        return Jsoup.connect("https://www.amazon.com.tr/dp/" + asinNo)
                .userAgent(getRandomUserAgent())
                .timeout(5000)
                .referrer(lastUrl)
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("sec-gpc", "1")
                .header("x-requested-with", "XMLHttpRequest")
                .get();
    }

    private static void fiyatKarsilastir(String asin, Long yeniFiyat, String model) {
        Long eskiFiyat = urunHashMap.get(asin);
        if (eskiFiyat == null) {
            urunHashMap.put(asin, yeniFiyat);
            return;
        }

        if (yeniFiyat < eskiFiyat) {
            telegramMesajGonder(asin, yeniFiyat, eskiFiyat, model);
        }

        urunHashMap.remove(asin);
        urunHashMap.put(asin, yeniFiyat);
    }

    private static void telegramMesajGonder(String asin, Long yeniFiyat, Long eskiFiyat, String model) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        Long fark = eskiFiyat - yeniFiyat;
        String mesaj = "" +
                "*Model: " + model + "%0A" +
                "*Yeni Fiyat: " + yeniFiyat.toString() + "%0A" +
                "*Eski Fiyat: " + eskiFiyat.toString() + "%0A" +
                "*Indirim: " + fark + "%0A" +
                "*Tarih: " + sdf.format(new Date()) + "%0A" +
                "*Urün Link:https://www.amazon.com.tr/dp/" + asin + "%0A" +
                "**Generated By Emin KAYABASI**";

        mesaj = mesaj.replace("&", "");
        mesaj = mesaj.replace(" H", "xh");
        mesaj = mesaj.replace(" h", "xh");

        String chatId = "-661343662";
        String token = "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM";
        String urlStr = "" +
                "https://api.telegram.org/bot" +
                token +
                "/sendMessage?chat_id=" + chatId +
                "&text=" + mesaj;

        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void userAgentListDoldur() {
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        userAgentList.add("Mozilla/5.0 (X11; CrOS x86_64 14816.131.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:78.0) Gecko/20100101 Firefox/78.0");
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.1.2 Safari/605.1.15");
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.115 Safari/537.36");
    }

    private static String getRandomUserAgent() {
        Random rand = new Random();
        lastRandom = rand.nextInt(6);
        return userAgentList.get(lastRandom);
    }
}