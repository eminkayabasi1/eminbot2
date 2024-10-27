package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.entity.Proxy;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.repository.FkuConfRepository;
import com.app.fku.genel.repository.MailGonderimRepository;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.repository.HbConfRepository;
import com.app.fku.hepsiburada.repository.HbTelegramConfRepository;
import com.app.fku.hepsiburada.repository.HbUyelikRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class HbGenelServiceImpl implements HbGenelService {

    public static Map<Long, String> headerHashMap = new HashMap< >();
    public static Map<Long, Proxy> proxyHashMap = new HashMap< >();
    public static String hbus_anonymousId = null;
    public static String hbus_sessionId = null;
    public static Date cookieDate = null;
    public static String headerStrGG = null;
    public static Proxy proxyGG = null;

    @Autowired
    MailGonderimRepository mailGonderimRepository;

    @Autowired
    FkuConfRepository fkuConfRepository;

    @Autowired
    HbConfRepository hbConfRepository;

    @Autowired
    HbUyelikRepository hbUyelikRepository;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    HbTelegramConfRepository hbTelegramConfRepository;


    @Override
    public Document urleGit(String url) throws Exception {
        //Document doc = ipBul(null, null);
        String secim = "";
        for (int i = 1; i > 0; i++) {
            Proxy proxyGGLocal = null;
            try {
                Random random = new Random();
                int proxyRandom = random.nextInt(proxyHashMap.size());
                proxyRandom = proxyRandom + 1;
                proxyGGLocal = proxyHashMap.get(new Long(proxyRandom));

                int headerRandom = random.nextInt(headerHashMap.size());
                headerRandom = headerRandom + 1;
                headerStrGG = headerHashMap.get(new Long(headerRandom));

                Connection.Response res = Jsoup
                        .connect(url)
                        .referrer("https://www.google.com")
                        //.proxy("159.146.46.154", 1441)
                        //.proxy("85.97.111.230", 3210)
                        .userAgent(headerStrGG)
                        .method(Connection.Method.GET)
                        .execute();

                Map<String, String> cookies = res.cookies();
                //System.out.println(cookies);

                return Jsoup.connect(url)
                        .cookies(cookies)
                        .referrer("https://www.google.com")
                        //.proxy("159.146.46.154", 1441)
                        //.proxy("85.97.111.230", 3210)
                        .userAgent(headerStrGG)
                        .get();

/**                if (headerRandom % 2 == 1) {
                    secim = "mod1";
                    return Jsoup.connect(url)
                            .referrer("https://www.google.com")
                            .proxy(proxyGGLocal.getProxy(), proxyGGLocal.getPort())
                            .userAgent("Mozilla/5.0")
                            .get()
                            ;
                } else if (headerRandom % 2 == 0) {
                    secim = "mod2";
                    Connection.Response res = Jsoup
                            .connect(url)
                            .referrer("https://www.google.com")
                            .proxy(proxyGGLocal.getProxy(), proxyGGLocal.getPort())
                            .userAgent("Mozilla/5.0")
                            .execute();

                    Map<String, String> cookies = res.cookies();

                    return Jsoup.connect(url)
                            .cookies(cookies)
                            //.referrer("https://www.google.com")
                            //.proxy(proxyGGLocal.getProxy(), proxyGGLocal.getPort())
                            //.userAgent("Mozilla/5.0")
                            .get();
                }*/
            }  catch (Exception e) {
                logService.hepsiBuradaLogYaz("Hb Genel Hata " + secim + " " + proxyGGLocal.getProxy() + " " + proxyGGLocal.getPort());
                Random rand = new Random();
                int sleepRand = rand.nextInt(6);
                TimeUnit.SECONDS.sleep(sleepRand);
            }
        }
        return null;
    }

    public static void putHeaderHashMap(Long key, String value) {
        headerHashMap.put(key, value);
    }

    public static void putProxyHashMap(Long key, Proxy proxy) {
        proxyHashMap.put(key, proxy);
    }

    public Document ipBul(String proxy, Integer port) throws IOException {
        if (proxy != null && !proxy.equals("")) {
            return Jsoup
                    .connect("https://nordvpn.com/tr/what-is-my-ip/")
                    .referrer("https://www.google.com")
                    .proxy(proxy, port)
                    .userAgent("Mozilla")
                    .method(Connection.Method.GET)
                    .get();
        } else {
            return Jsoup
                    .connect("https://nordvpn.com/tr/what-is-my-ip/")
                    .referrer("https://www.google.com")
                    .userAgent("Mozilla")
                    .method(Connection.Method.GET)
                    .get();
        }
    }
}
