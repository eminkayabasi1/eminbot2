package com.app.fku.yemeksepeti.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.repository.FkuConfRepository;
import com.app.fku.genel.repository.MailGonderimRepository;
import com.app.fku.yemeksepeti.fonksiyon.service.YmkGenelService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class YmkGenelServiceImpl implements YmkGenelService {

    public static Map<Long, String> headerHashMap = new HashMap< >();

    @Autowired
    MailGonderimRepository mailGonderimRepository;

    @Autowired
    FkuConfRepository fkuConfRepository;

    @Autowired
    GenelService genelService;

    @Override
    public Document urleGit(String url) throws Exception {
        Random random = new Random();
        int headerRandom = random.nextInt(headerHashMap.size());
        String headerStr = headerHashMap.get(new Long(headerRandom));
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

    }

    public static void putHeaderHashMap(Long key, String value) {
        headerHashMap.put(key, value);
    }

    public String headerGetir() {
        Random random = new Random();
        int headerRandom = random.nextInt(headerHashMap.size());
        String headerStr = headerHashMap.get(new Long(headerRandom));
        return headerStr;
    }
}
