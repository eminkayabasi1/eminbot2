package com.app.fku.genel.fonksiyon.impl;

import com.app.fku.genel.entity.MailAd;
import com.app.fku.genel.entity.MailSoyad;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.repository.MailAdRepository;
import com.app.fku.genel.repository.MailSoyadRepository;
import org.asynchttpclient.AsyncHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.asynchttpclient.Dsl.asyncHttpClient;

@Service
public class GenelServiceImpl implements GenelService {

    @Autowired
    MailAdRepository mailAdRepository;

    @Autowired
    MailSoyadRepository mailSoyadRepository;

    @Override
    public String getHostName() throws UnknownHostException {
        java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
        return localMachine.getHostName();
    }

    @Override
    public Document urleGit(String url) throws Exception {
        return Jsoup.connect(url).userAgent("Mozilla").get();
    }

    @Override
    public String yeniMailAdiUret() {
        Random random = new Random();
        int isimRandom = random.nextInt(1000);
        int soyisimRandom = random.nextInt(1000);

        String ad = yeniAdUret();
        String soyad = yeniSoyadUret();

        if (ad == null || ad.equals("")) {
            yeniMailAdiUret();
        }
        if (soyad == null || soyad.equals("")) {
            yeniMailAdiUret();
        }

        String mailAdi = ad + "." + soyad;
        return mailAdi;
    }

    @Override
    public String yeniAdUret() {
        Random random = new Random();
        int isimRandom = random.nextInt(1000);

        MailAd mailAd = mailAdRepository.findById(new Long(isimRandom)).orElse(null);

        if (mailAd == null) {
            yeniAdUret();
        }

        String ad = mailAd.getIsim();
        return ad;
    }

    @Override
    public String yeniSoyadUret() {
        Random random = new Random();
        int soyisimRandom = random.nextInt(1000);

        MailSoyad mailSoyad = mailSoyadRepository.findById(new Long(soyisimRandom)).orElse(null);

        if (mailSoyad == null) {
            yeniSoyadUret();
        }

        String soyad = mailSoyad.getSoyIsim();
        return soyad;
    }

    @Override
    public void telegramMesajGonder(String mesaj, String chatId, String urunId, String token) throws IOException, InterruptedException {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        String text = mesaj;
        urlString = String.format(urlString, token, chatId, URLEncoder.encode(text, StandardCharsets.UTF_8.toString()));
        try {
            AsyncHttpClient ASYNC_HTTP_CLIENT = asyncHttpClient();
            ASYNC_HTTP_CLIENT.preparePost(urlString)
                    .setMethod("GET")
                    //.setHeader("Content-Type", "application/json")
                    //.setHeader("Accept", "application/json")
                    .execute()
                    .toCompletableFuture()
                    .join();
            ASYNC_HTTP_CLIENT.close();

            /**
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();*/
        } catch (Exception e) {
            e.printStackTrace();
            Thread.sleep(10000L);
            //telegramMesajGonder("Mesaj Hatasi--" + urunId, chatId, urunId, token);
        }
    }

    static String getValidURL(String invalidURLString){
        try {
            // Convert the String and decode the URL into the URL class
            URL url = new URL(URLDecoder.decode(invalidURLString, StandardCharsets.UTF_8.toString()));

            // Use the methods of the URL class to achieve a generic solution
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            // return String or
            // uri.toURL() to return URL object
            return uri.toString();
        } catch (URISyntaxException | UnsupportedEncodingException | MalformedURLException ignored) {
            return null;
        }
    }

    @Override
    public void telegramBombaGonder(String mesaj, String urunId) throws IOException, InterruptedException {
        telegramMesajGonder(mesaj, "-4041731553", urunId, "5938603078:AAHR4rRVL0BxEUoPYuE_IVSkYJdT3pjaX_g");
    }

    @Override
    public void telegramFiyatHatasiGonder(String mesaj, String urunId) throws IOException, InterruptedException {
        //telegramMesajGonder(mesaj, "-587200763", urunId, "5938603078:AAHR4rRVL0BxEUoPYuE_IVSkYJdT3pjaX_g");
    }

    @Override
    public void telegramDysonGonder(String mesaj, String urunId) throws IOException, InterruptedException {
        telegramMesajGonder(mesaj, "-4039024584", urunId, "5938603078:AAHR4rRVL0BxEUoPYuE_IVSkYJdT3pjaX_g");
    }

    @Override
    public void telegramFritozGonder(String mesaj, String urunId) throws IOException, InterruptedException {
        telegramMesajGonder(mesaj, "-4046139050", urunId, "5938603078:AAHR4rRVL0BxEUoPYuE_IVSkYJdT3pjaX_g");
    }

    @Override
    public void telegramFisslerGonder(String mesaj, String urunId) throws IOException, InterruptedException {
        telegramMesajGonder(mesaj, "-4002541520", urunId, "5938603078:AAHR4rRVL0BxEUoPYuE_IVSkYJdT3pjaX_g");
    }

    @Override
    public void a101TelegramHDDGonder(String mesaj, String urunId) throws IOException, InterruptedException {
        telegramMesajGonder(mesaj, "-822898049", urunId, "5938603078:AAHR4rRVL0BxEUoPYuE_IVSkYJdT3pjaX_g");
    }

    @Override
    public void telegramPlayStationGonder(String mesaj, String urunId) throws IOException, InterruptedException {
        telegramMesajGonder(mesaj, "-844131464", urunId, "5938603078:AAHR4rRVL0BxEUoPYuE_IVSkYJdT3pjaX_g");
    }

    @Override
    public void telegramIphone14Gonder(String mesaj, String urunId) throws IOException, InterruptedException {
        telegramMesajGonder(mesaj, "-951503843", urunId, "6226340101:AAEHW0xDW9yCxKyjkoKB489mBv3_u1O5AQw");
    }

    @Override
    public void telegramKahramanGonder(String mesaj, String chatId, String urunId) throws IOException, InterruptedException {
        telegramMesajGonder(mesaj, chatId, urunId, "5967349444:AAESz_fVdC3TMvyCeSClP1sNRtaWNBikYD0");
    }

    @Override
    public void telegramHbSepetGonder(String mesaj, String urunId) throws IOException, InterruptedException {
        telegramMesajGonder(mesaj, "-924056541", urunId, "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM");
    }
}
