package com.app.fku.teknosa.fonksiyon.impl;

import com.app.fku.genel.entity.MailGonderim;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.enums.TelegramEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.repository.FkuConfRepository;
import com.app.fku.genel.repository.MailGonderimRepository;
import com.app.fku.teknosa.entity.TknFiyat;
import com.app.fku.teknosa.entity.TknTelegramConf;
import com.app.fku.teknosa.entity.TknUrun;
import com.app.fku.teknosa.fonksiyon.service.TknGenelService;
import com.app.fku.teknosa.repository.TknTelegramConfRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.*;

@Service
public class TknGenelServiceImpl implements TknGenelService {

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
    TknTelegramConfRepository tknTelegramConfRepository;

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
            logService.teknosaLogYaz("TKN Hata: Header: " + headerStr);
        }
        return null;
    }

    @Override
    public void mailGonderimi (TknUrun tknUrun, TknFiyat tknFiyat) throws IOException, InterruptedException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -10);
        Date kontrolTarihi = cal.getTime();
        List<MailGonderim> mailGonderimList = mailGonderimRepository.tknMailGonderilmisMi(tknUrun.getId(), kontrolTarihi);
        if (mailGonderimList != null && mailGonderimList.size() > 0) {
            //bu ürün için 5 dakika içerisinde mail gönderilmiş.
            return;
        }

        String hostname = genelService.getHostName();
        String subject = "TKN İ " + tknUrun.getModel() + " " + tknUrun.getTknNo();

        String text =
            tknUrun.getModel() + " \n" +
            tknUrun.getTknNo() + " \n" +
            "Beklenen Fiyat : " + tknFiyat.getEskiBeklenenFiyat() + " --> " + "Şuanki Fiyat : " + tknFiyat.getFiyat() + " \n" +
            new Date();

        MailGonderim mailGonderim = new MailGonderim();
        mailGonderim.setMailTo("");
        mailGonderim.setSubject(subject);
        mailGonderim.setText(text);
        mailGonderim.setHostname(hostname);
        mailGonderim.setStatus(EvetHayirEnum.HAYIR);
        mailGonderim.setKayitTarihi(new Date());
        mailGonderim.setTknUrun(tknUrun);
        mailGonderimRepository.save(mailGonderim);
        mailGonderimRepository.flush();

        String telegramText =
                tknUrun.getModel() + " " +
                tknUrun.getTknNo() + " " +
                "Beklenen Fiyat : " + tknFiyat.getEskiBeklenenFiyat() + " --> " + "Şuanki Fiyat : " + tknFiyat.getFiyat() + " " +
                new Date();

        List<TknTelegramConf> tknTelegramConfList = tknTelegramConfRepository.findByTknKategori(tknUrun.getTknKategori());
        if (tknTelegramConfList != null && tknTelegramConfList.size() > 0) {
            for (TknTelegramConf tknTelegramConf: tknTelegramConfList) {
                //genelService.telegramMesajGonder(telegramText, tknTelegramConf.getTelegramId(), tknUrun.getId());
            }
        } else {
            //genelService.telegramMesajGonder(telegramText, "-577122534", tknUrun.getId());
        }
    }

    public static void putHeaderHashMap(Long key, String value) {
        headerHashMap.put(key, value);
    }
}
