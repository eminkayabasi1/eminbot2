package com.app.fku.yeniamazon.fonksiyon.impl;

import com.app.fku.genel.entity.Proxy;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.repository.FkuConfRepository;
import com.app.fku.genel.repository.MailGonderimRepository;
import com.app.fku.yeniamazon.fonksiyon.service.YeniAmazonGenelService;
import com.app.fku.yeniamazon.repository.YeniAmazonTelegramConfRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class YeniAmazonGenelServiceImpl implements YeniAmazonGenelService {

    public static Map<Long, String> headerHashMap = new HashMap< >();
    public static Map<Long, Proxy> proxyHashMap = new HashMap< >();

    public static String headerStrGG = null;
    public static Proxy proxyGG = null;

    @Autowired
    MailGonderimRepository mailGonderimRepository;

    @Autowired
    FkuConfRepository fkuConfRepository;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    YeniAmazonTelegramConfRepository vatanTelegramConfRepository;

    @Override
    public Document urleGit(String url) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 1; i > 0; i++) {
            String headerStrGGLocal = "";
            Proxy proxyGGLocal = null;
            try {
                Random random = new Random();
                int headerRandom = random.nextInt(headerHashMap.size());
                headerRandom = headerRandom + 1;
                headerStrGGLocal = headerHashMap.get(new Long(headerRandom));

                int proxyRandom = random.nextInt(proxyHashMap.size());
                proxyRandom = proxyRandom + 1;
                if (proxyRandom < 6) {
                    proxyRandom = proxyRandom + 5;
                }

                proxyGGLocal = proxyHashMap.get(new Long(proxyRandom));
                System.out.println("Proxy Random: " + proxyRandom + " " + proxyGGLocal.getProxy() + " " + proxyGGLocal.getPort());

                return Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36")
                        .referrer("https://www.google.com")
                        .proxy("159.146.46.154", 1441)
                        .get();

                //return Jsoup.connect(url)
                        //.header("authority", "www.amazon.com.tr")
                        //.header("method", "GET")
                        //.header("path", "/s?rh=n%3A14630942031%2Cp_n_fulfilled_by_amazon%3A21345978031%2Cp_6%3AA1UNQM1SR2CHM&s=price-asc-rank")
                        //.header("scheme", "https")
                        //.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                        //.header("accept-encoding", "gzip, deflate, br")
                        //.header("accept-language", "tr-TR,tr;q=0.8")
                        //.header("cache-control", "max-age=0")
                        //.header("sec-ch-ua-mobile", "?0")
                        //.header("sec-ch-ua-platform", "Windows")
                        //.header("sec-fetch-dest", "document")
                        //.header("sec-fetch-mode", "navigate")
                        //.header("sec-fetch-site", "same-origin")
                        //.header("sec-fetch-user", "?1")
                        //.header("sec-gpc", "1")
                        //.header("upgrade-insecure-requests", "1")
                        //.header("user-agent", headerStrGGLocal)
                        //.proxy(proxyGGLocal.getProxy(), proxyGGLocal.getPort())
                        //.timeout(5000)
                        //.get();
//                if (false) {
//                    return Jsoup.connect(url)
//                            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//                            .header("Accept-Encoding", "gzip, deflate, sdch, br")
//                            .header("Accept-Language", "en-US,en;q=0.8")
//                            .header("Cache-Control", "max-age=0")
//                            .header("Connection", "keep-alive")
//                            .header("Host", "www.amazon.com")
//                            .header("Upgrade-Insecure-Requests", "1")
//                            .header("User-Agent", headerStrGGLocal)
//                            .header("Connection", "close")
//                            .proxy(proxyGGLocal.getProxy(), proxyGGLocal.getPort())
//                            .timeout(5000)
//                            .get();
//                } else {
//                    return Jsoup.connect(url)
//                            .userAgent(headerStrGGLocal)
//                            .proxy(proxyGGLocal.getProxy(), proxyGGLocal.getPort())
//                            .get();
//                }
            } catch (Exception e) {
                logService.yeniAmzLogYaz("Yeni Amz Genel Hata : " + proxyGGLocal.getProxy() + " : " + headerStrGGLocal + " : " + sdf.format(new Date()));
                Random rand = new Random();
                int sleepRand = rand.nextInt(12);
                TimeUnit.SECONDS.sleep(sleepRand + 15);
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
}
