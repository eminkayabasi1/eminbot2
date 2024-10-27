package com.app.fku.mutfakdunyasi.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.repository.FkuConfRepository;
import com.app.fku.genel.repository.MailGonderimRepository;
import com.app.fku.mutfakdunyasi.fonksiyon.service.MdGenelService;
import com.app.fku.mutfakdunyasi.repository.MdTelegramConfRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MdGenelServiceImpl implements MdGenelService {

    public static Map<Long, String> headerHashMap = new HashMap< >();

    @Autowired
    MailGonderimRepository mailGonderimRepository;

    @Autowired
    FkuConfRepository fkuConfRepository;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    MdTelegramConfRepository mdTelegramConfRepository;

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
            logService.mdLogYaz("MD Hata: Header: " + headerStr);
        }
        return null;
    }

    public static void putHeaderHashMap(Long key, String value) {
        headerHashMap.put(key, value);
    }
}
