package com.app.fku.trendyol.fonksiyon.impl;

import com.app.fku.genel.entity.MailGonderim;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.enums.ThreadTypeEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.repository.FkuConfRepository;
import com.app.fku.genel.repository.MailGonderimRepository;
import com.app.fku.hepsiburada.entity.*;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.repository.HbConfRepository;
import com.app.fku.hepsiburada.repository.HbTelegramConfRepository;
import com.app.fku.hepsiburada.repository.HbUyelikRepository;
import com.app.fku.trendyol.fonksiyon.service.TrendyolGenelService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TrendyolGenelServiceImpl implements TrendyolGenelService {

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
                }
                Random rand = new Random();
                int sleepRand = rand.nextInt(6);
                TimeUnit.SECONDS.sleep(sleepRand);
            } catch (Exception e) {
                logService.trendyolLogYaz("Trendyol Header: " + headerStr);
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
}
