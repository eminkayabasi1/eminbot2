package com.app.fku.itopya.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.itopya.fonksiyon.service.ItopyaGenelService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ItopyaGenelServiceImpl implements ItopyaGenelService {

    public static Map<Long, String> headerHashMap = new HashMap< >();

    @Autowired
    LogService logService;

    @Override
    public Document urleGit(String url) throws Exception {
        String headerStr = "";
        for (int i = 1; i > 0; i++) {
            try {
                Random random = new Random();
                int headerRandom = random.nextInt(headerHashMap.size());
                headerRandom = headerRandom + 1;
                headerStr = headerHashMap.get(new Long(headerRandom));
                Connection.Response response = Jsoup.connect(url)
                        .userAgent(headerStr)
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .followRedirects(true)
                        .execute();
                if (response != null) {
                    return response.parse();
                } else {
                    return null;
                }
            } catch (Exception e) {
                logService.itopyaLogYaz("Header: " + headerStr);
                Thread.sleep(10000);
            }
        }
        return null;
    }

    public static void putHeaderHashMap(Long key, String value) {
        headerHashMap.put(key, value);
    }
}
