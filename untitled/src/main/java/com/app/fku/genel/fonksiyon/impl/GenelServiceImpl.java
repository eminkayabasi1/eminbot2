package com.app.fku.genel.fonksiyon.impl;

import com.app.fku.genel.entity.MailAd;
import com.app.fku.genel.entity.MailSoyad;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.repository.MailAdRepository;
import com.app.fku.genel.repository.MailSoyadRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Random;

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

        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        //Add Telegram token
//        String apiToken = "1518724507:AAElzNxKZyZi_9oIGiX9tybXCS6Xd4JXRnk";//emincansubot
//        String apiToken = "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM";//fkumesajbot

//        String apiToken = "1610924512:AAFdxtkwC0zSCyZnmWrmN4Gixj4JIgNO-6U";
        String text = mesaj;
        urlString = String.format(urlString, token, chatId, text);
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            Thread.sleep(10000L);
            telegramMesajGonder("Mesaj Hatasi--" + urunId, chatId, urunId, token);
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
