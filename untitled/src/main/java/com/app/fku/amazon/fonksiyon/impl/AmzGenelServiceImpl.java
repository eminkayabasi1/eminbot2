package com.app.fku.amazon.fonksiyon.impl;

import com.app.fku.amazon.entity.AmzFiyat;
import com.app.fku.amazon.entity.AmzTelegramConf;
import com.app.fku.amazon.entity.AmzUrun;
import com.app.fku.amazon.fonksiyon.service.AmzGenelService;
import com.app.fku.amazon.repository.AmzTelegramConfRepository;
import com.app.fku.genel.entity.MailGonderim;
import com.app.fku.genel.entity.Proxy;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.repository.FkuConfRepository;
import com.app.fku.genel.repository.MailGonderimRepository;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AmzGenelServiceImpl implements AmzGenelService {

    public static Map<Long, String> headerHashMap = new HashMap< >();
    public static Map<Long, Proxy> proxyHashMap = new HashMap< >();

    @Autowired
    MailGonderimRepository mailGonderimRepository;

    @Autowired
    FkuConfRepository fkuConfRepository;

    @Autowired
    GenelService genelService;

    @Autowired
    AmzTelegramConfRepository amzTelegramConfRepository;

    public static String session_id = "";
    public static String session_id_time = "";
    public static String i18n_prefs = "";
    public static Date cookieTime = new Date();
    public static String lastUrl = "";
    public static String headerStrGG = null;
    public static Proxy proxyGG = null;

    @Override
    public Document urleGit(String url) throws Exception {

        String proxyStr = "";
        Integer proxyPort = 0;

        for (int i = 1; i > 0; i++) {
            try {
                Random random = new Random();
                int headerRandom = random.nextInt(headerHashMap.size());
                headerRandom = headerRandom + 1;
                headerStrGG = headerHashMap.get(new Long(headerRandom));


                //if (proxyGG == null) {
                int proxyRandom = random.nextInt(proxyHashMap.size());
                proxyRandom = proxyRandom + 1;

                proxyGG = proxyHashMap.get(new Long(proxyRandom));
                //}

                /**
                proxyStr = proxyGG.getProxy();
                proxyPort = proxyGG.getPort();
                Document a= Jsoup //
                        .connect(url)//.connect(url) //
                        .method(Connection.Method.GET)
                        //.proxy(proxyStr, proxyPort) //
                        .userAgent(headerStrGG) //
                        .header("Content-Language", "en-US") //
                        .referrer("https://www.google.com")
                        .get();

                //System.out.println("Amz New Genel Başarı " + proxyStr + " " + proxyPort);
                return a;*/

                return Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36")
                        .maxBodySize(0)
                        .get();
                /**
                return Jsoup
                        .connect(url)
                        .proxy(proxyGG.getProxy(), proxyGG.getPort())
                        .userAgent(headerStrGG)
                        .maxBodySize(0)
                        .referrer("https://www.google.com.tr")
                        .header("Content-Language", "tr-TR")
                        .get();*/
                /**
                return Jsoup
                        .connect(url)
                        .userAgent(headerStrGG)
                        .followRedirects(true)
                        .header("Content-Language", "tr-TR")
                        .proxy(proxyGG.getProxy(), proxyGG.getPort())
                        .referrer("https://www.amazon.com.tr")
                        .timeout(100000)
                        .get();*/
            } catch (Exception e) {
                //System.out.println("Amz New Genel Hata " + proxyStr + " " + proxyPort);
                headerStrGG = null;
                proxyGG = null;
                Random rand = new Random();
                int sleepRand = rand.nextInt(25);
                TimeUnit.SECONDS.sleep(sleepRand + 1);
            }
        }

        return null;

        /**
        Random random = new Random();
        int headerRandom = random.nextInt(headerHashMap.size());
        if (headerRandom == 0) {
            headerRandom = 1;
        }
        String headerStr = headerHashMap.get(new Long(headerRandom));

        Connection.Response res = Jsoup
                .connect(url + "?th=1")
                .method(Connection.Method.GET)
                .referrer("www.google.com.tr")
                .userAgent(headerStr)
                .execute();

        Map<String, String> cookies = res.cookies();

        return Jsoup.connect(url).cookies(cookies).get();
         */
    }

    @Override
    public void mailGonderimi (AmzUrun amzUrun, AmzFiyat amzFiyat) throws IOException, InterruptedException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -10);
        Date kontrolTarihi = cal.getTime();
        List<MailGonderim> mailGonderimList = mailGonderimRepository.amzMailGonderilmisMi(amzUrun.getId(), kontrolTarihi);
        if (mailGonderimList != null && mailGonderimList.size() > 0) {
            //bu ürün için 5 dakika içerisinde mail gönderilmiş.
            return;
        }

        String hostname = genelService.getHostName();
        String subject = "AMZ İ " + amzUrun.getModel() + " " + amzUrun.getAsinNo();

        String text =
            amzUrun.getModel() + " \n" +
            amzUrun.getAsinNo() + " \n" +
            "Beklenen Fiyat : " + amzFiyat.getEskiBeklenenFiyat() + " --> " + "Şuanki Fiyat : " + amzFiyat.getFiyat() + " \n" +
            new Date() + " \n" +
            "https://www.amazon.com.tr/dp/" + amzUrun.getAsinNo();

        MailGonderim mailGonderim = new MailGonderim();
        mailGonderim.setMailTo("");
        mailGonderim.setSubject(subject);
        mailGonderim.setText(text);
        mailGonderim.setHostname(hostname);
        mailGonderim.setStatus(EvetHayirEnum.HAYIR);
        mailGonderim.setKayitTarihi(new Date());
        mailGonderim.setAmzUrun(amzUrun);
        mailGonderimRepository.save(mailGonderim);
        mailGonderimRepository.flush();

        String telegramText =
                amzUrun.getModel() + " " +
                amzUrun.getAsinNo() + " " +
                "Beklenen Fiyat : " + amzFiyat.getEskiBeklenenFiyat() + " --> " + "Şuanki Fiyat : " + amzFiyat.getFiyat() + " " +
                new Date() + " " +
                "https://www.amazon.com.tr/dp/" + amzUrun.getAsinNo();

        List<AmzTelegramConf> amzTelegramConfList = amzTelegramConfRepository.findByAmzKategori(amzUrun.getAmzKategori());

//        if (amzTelegramConfList != null && amzTelegramConfList.size() > 0) {
//            for (AmzTelegramConf amzTelegramConf: amzTelegramConfList) {
//                genelService.telegramMesajGonder(telegramText, amzTelegramConf.getTelegramId(), amzUrun.getId().toString());
//            }
//        } else {
//            genelService.telegramMesajGonder(telegramText, "-679113079", amzUrun.getId().toString());
//        }
    }

    public static void putHeaderHashMap(Long key, String value) {
        headerHashMap.put(key, value);
    }

    public static void putProxyHashMap(Long key, Proxy proxy) {
        proxyHashMap.put(key, proxy);
    }
}
