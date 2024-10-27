package com.app.fku.bim.fonksiyon.impl;

import com.app.fku.bim.fonksiyon.service.BimGenelService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class BimGenelServiceImpl implements BimGenelService {

    public static Map<Long, String> headerHashMap = new HashMap< >();

    @Override
    public Document urleGit(String url) throws Exception {
        String headerStr = "";
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
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println("Header: " + headerStr + " \n" + sw.toString());
        }
        return null;
    }

    public static void putHeaderHashMap(Long key, String value) {
        headerHashMap.put(key, value);
    }
}
