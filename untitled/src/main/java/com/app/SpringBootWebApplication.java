package com.app;

import com.app.fku.amazonx.fonksiyon.service.AmazonxService;
import com.app.fku.amazonx.fonksiyon.threadclass.AmazonxThread;
import com.app.fku.arzum.fonksiyon.service.ArzumService;
import com.app.fku.dyson.fonksiyon.impl.DysonGenelServiceImpl;
import com.app.fku.dyson.fonksiyon.service.DysonService;
import com.app.fku.genel.entity.FkuConf;
import com.app.fku.genel.entity.Header;
import com.app.fku.genel.entity.Proxy;
import com.app.fku.genel.enums.ThreadTypeEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.fonksiyon.threadclass.MailSenderThread;
import com.app.fku.genel.repository.*;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaJSONService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaSepetJSONService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaSepetOtomatikEkleJSONService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaSepetUrunEkleJSONService;
import com.app.fku.hepsiburada.fonksiyon.service.HbGenelServiceImpl;
import com.app.fku.hepsiburada.fonksiyon.threadclass.HepsiBuradaSepetJSONThread;
import com.app.fku.hepsiburada.fonksiyon.threadclass.HepsiBuradaSepetOtomatikEkleJSONThread;
import com.app.fku.karaca.fonksiyon.service.KaracaAlperJSONService;
import com.app.fku.karaca.fonksiyon.service.KaracaJSONService;
import com.app.fku.karaca.fonksiyon.threadclass.KaracaAlperJSONThread;
import com.app.fku.karaca.fonksiyon.threadclass.KaracaJSONThread;
import com.app.fku.tesla.TeslaJSONService;
import com.app.fku.tesla.TeslaJSONThread;
import com.app.fku.trendyol.fonksiyon.impl.TrendyolGenelServiceImpl;
import com.app.fku.trendyol.fonksiyon.service.Trendyol5AlJSONService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolJSONService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolTYEminJSONService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolTYJSONService;
import com.app.fku.trendyol.fonksiyon.threadclass.TrendyolJSONThread;
import com.app.fku.trendyol.fonksiyon.threadclass.TrendyolTYEminJSONThread;
import com.app.fku.trendyol.fonksiyon.threadclass.TrendyolTYJSONThread;
import com.app.fku.yeniamazon.fonksiyon.impl.YeniAmazonGenelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EnableSwagger2
public class SpringBootWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
//        SpringApplication.run(SpringBootWebApplication.class, args);
        SpringApplication app = new SpringApplication(SpringBootWebApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        app.run(args);
        System.out.println("Fiyat Kontrol Projemiz Ayağa Kalktı... App.host:" + InetAddress.getLocalHost().getHostAddress());
    }

    //Genel
    @Autowired
    FkuConfRepository fkuConfRepository;
    @Autowired
    GenelService genelService;
    @Autowired
    LogService logService;
    @Autowired
    MailGonderimRepository mailGonderimRepository;
    @Autowired
    MailConfRepository mailConfRepository;
    @Autowired
    HeaderRepository headerRepository;
    @Autowired
    ProxyRepository proxyRepository;
    //Genel

    //Hepsi Burada
    @Autowired
    HepsiBuradaJSONService hepsiBuradaJSONService;
    @Autowired
    HepsiBuradaSepetJSONService hepsiBuradaSepetJSONService;
    @Autowired
    HepsiBuradaSepetUrunEkleJSONService hepsiBuradaSepetUrunEkleJSONService;
    @Autowired
    HepsiBuradaSepetOtomatikEkleJSONService hepsiBuradaSepetOtomatikEkleJSONService;
    //Hepsi Burada

    //KARACA
    @Autowired
    KaracaJSONService karacaJSONService;
    @Autowired
    KaracaAlperJSONService karacaAlperJSONService;
    //KARACA

    //DYSON
    @Autowired
    DysonService dysonService;
    //DYSON

    //ARZUM
    @Autowired
    ArzumService arzumService;
    //ARZUM

    //Amazonx
    @Autowired
    AmazonxService amazonxService;
    //Amazonx

    //Trendyol
    @Autowired
    TrendyolJSONService trendyolJSONService;
    @Autowired
    TrendyolTYJSONService trendyolTYJSONService;
    @Autowired
    TrendyolTYEminJSONService trendyolTYEminJSONService;
    @Autowired
    Trendyol5AlJSONService trendyol5AlJSONService;
    //Trendyol


    @Autowired
    TeslaJSONService teslaJSONService;


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws IOException, InterruptedException {

        String hostname = genelService.getHostName();
        //hostname = hostname + "1";
        System.out.println("Makine IP: " + hostname);

        //genelService.telegramMesajGonder(hostname, "-659185836", 10L);

        //JSONLAR

        Thread[] threadsHBSepet = new Thread[1];
        HepsiBuradaSepetJSONThread[] hepsiBuradaSepetJSONThreads = new HepsiBuradaSepetJSONThread[1];
        hepsiBuradaSepetJSONThreads[0] = new HepsiBuradaSepetJSONThread();
        hepsiBuradaSepetJSONThreads[0].hepsiBuradaSepetJSONService = hepsiBuradaSepetJSONService;
        threadsHBSepet[0] = new Thread(hepsiBuradaSepetJSONThreads[0]);
        threadsHBSepet[0].start();

        Thread[] threadsHBSepetOE = new Thread[1];
        HepsiBuradaSepetOtomatikEkleJSONThread[] hepsiBuradaSepetOtomatikEkleJSONThreads = new HepsiBuradaSepetOtomatikEkleJSONThread[1];
        hepsiBuradaSepetOtomatikEkleJSONThreads[0] = new HepsiBuradaSepetOtomatikEkleJSONThread();
        hepsiBuradaSepetOtomatikEkleJSONThreads[0].hepsiBuradaSepetOtomatikEkleJSONService = hepsiBuradaSepetOtomatikEkleJSONService;
        threadsHBSepetOE[0] = new Thread(hepsiBuradaSepetOtomatikEkleJSONThreads[0]);
        threadsHBSepetOE[0].start();

        Thread[] threadsTY = new Thread[1];
        TrendyolJSONThread[] trendyolJSONThreads = new TrendyolJSONThread[1];
        trendyolJSONThreads[0] = new TrendyolJSONThread();
        trendyolJSONThreads[0].trendyolJSONService = trendyolJSONService;
        threadsTY[0] = new Thread(trendyolJSONThreads[0]);
        threadsTY[0].start();

        Thread[] threadsTYTY = new Thread[1];
        TrendyolTYJSONThread[] trendyolTYJSONThreads = new TrendyolTYJSONThread[1];
        trendyolTYJSONThreads[0] = new TrendyolTYJSONThread();
        trendyolTYJSONThreads[0].trendyolTYJSONService = trendyolTYJSONService;
        threadsTYTY[0] = new Thread(trendyolTYJSONThreads[0]);
        threadsTYTY[0].start();

        Thread[] threadsTYEminTY = new Thread[1];
        TrendyolTYEminJSONThread[] trendyolTYEminJSONThreads = new TrendyolTYEminJSONThread[1];
        trendyolTYEminJSONThreads[0] = new TrendyolTYEminJSONThread();
        trendyolTYEminJSONThreads[0].trendyolTYEminJSONService = trendyolTYEminJSONService;
        threadsTYEminTY[0] = new Thread(trendyolTYEminJSONThreads[0]);
        threadsTYEminTY[0].start();

        Thread[] threadsAmazonx = new Thread[1];
        AmazonxThread[] amazonxThreads = new AmazonxThread[1];
        amazonxThreads[0] = new AmazonxThread();
        amazonxThreads[0].amazonxService = amazonxService;
        threadsAmazonx[0] = new Thread(amazonxThreads[0]);
        threadsAmazonx[0].start();

        Thread[] threadsKaraca = new Thread[1];
        KaracaJSONThread[] karacaJSONThreads = new KaracaJSONThread[1];
        karacaJSONThreads[0] = new KaracaJSONThread();
        karacaJSONThreads[0].karacaJSONService = karacaJSONService;
        threadsKaraca[0] = new Thread(karacaJSONThreads[0]);
        threadsKaraca[0].start();

        Thread[] threadsKaracaAlper = new Thread[1];
        KaracaAlperJSONThread[] karacaAlperJSONThreads = new KaracaAlperJSONThread[1];
        karacaAlperJSONThreads[0] = new KaracaAlperJSONThread();
        karacaAlperJSONThreads[0].karacaAlperJSONService = karacaAlperJSONService;
        threadsKaracaAlper[0] = new Thread(karacaAlperJSONThreads[0]);
        threadsKaracaAlper[0].start();

        /**
         Thread[] threadsHB = new Thread[1];
         HepsiBuradaJSONThread[] hepsiBuradaJSONThreads = new HepsiBuradaJSONThread[1];
         hepsiBuradaJSONThreads[0] = new HepsiBuradaJSONThread();
         hepsiBuradaJSONThreads[0].hepsiBuradaJSONService = hepsiBuradaJSONService;
         threadsHB[0] = new Thread(hepsiBuradaJSONThreads[0]);
         threadsHB[0].start();

         Thread[] threadsHBSepetUE = new Thread[1];
         HepsiBuradaSepetUrunEkleJSONThread[] hepsiBuradaSepetUEJSONThreads = new HepsiBuradaSepetUrunEkleJSONThread[1];
         hepsiBuradaSepetUEJSONThreads[0] = new HepsiBuradaSepetUrunEkleJSONThread();
         hepsiBuradaSepetUEJSONThreads[0].hepsiBuradaSepetJSONService = hepsiBuradaSepetUrunEkleJSONService;
         threadsHBSepetUE[0] = new Thread(hepsiBuradaSepetUEJSONThreads[0]);
         threadsHBSepetUE[0].start();

         Thread[] threadsTY5Al = new Thread[1];
         Trendyol5AlJSONThread[] trendyol5AlJSONThreads = new Trendyol5AlJSONThread[1];
         trendyol5AlJSONThreads[0] = new Trendyol5AlJSONThread();
         trendyol5AlJSONThreads[0].trendyol5AlJSONService = trendyol5AlJSONService;
         threadsTY5Al[0] = new Thread(trendyol5AlJSONThreads[0]);
         threadsTY5Al[0].start();

         Thread[] threadsDyson = new Thread[1];
         DysonThread[] dysonThreads = new DysonThread[1];
         dysonThreads[0] = new DysonThread();
         dysonThreads[0].dysonService = dysonService;
         threadsDyson[0] = new Thread(dysonThreads[0]);
         threadsDyson[0].start();

         Thread[] threadsArzum = new Thread[1];
         ArzumThread[] arzumThreads = new ArzumThread[1];
         arzumThreads[0] = new ArzumThread();
         arzumThreads[0].arzumService = arzumService;
         threadsArzum[0] = new Thread(arzumThreads[0]);
         threadsArzum[0].start();*/
        //JSONLAR

        headerListDoldur();
        proxyListDoldur();

        List<FkuConf> fkuConfList = fkuConfRepository.findByHostname(hostname);
        //GENEL START
        for (FkuConf fkuConf : fkuConfList) {
            if (fkuConf.getThreadTypeEnum().equals(ThreadTypeEnum.GNL_MAIL_SENDER) && fkuConf.getThreadCount() > 0) {
                Thread[] threads = new Thread[1];
                MailSenderThread[] mailSenderThreadArr = new MailSenderThread[1];
                mailSenderThreadArr[0] = new MailSenderThread();
                mailSenderThreadArr[0].mailConfRepository = mailConfRepository;
                mailSenderThreadArr[0].mailGonderimRepository = mailGonderimRepository;
                threads[0] = new Thread(mailSenderThreadArr[0]);
                threads[0].start();
                TimeUnit.SECONDS.sleep(3);
            }
        }
        //GENEL END

    }

    public void headerListDoldur() {
        List<Header> headerList = headerRepository.findAll();
        for (Header header : headerList) {
            TrendyolGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            HbGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            DysonGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            YeniAmazonGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
        }
    }

    public void proxyListDoldur() {
        List<Proxy> proxyList = proxyRepository.findAll();
        for (Proxy proxy : proxyList) {
            //AmzGenelServiceImpl.putProxyHashMap(proxy.getId(), proxy);
            HbGenelServiceImpl.putProxyHashMap(proxy.getId(), proxy);
            YeniAmazonGenelServiceImpl.putProxyHashMap(proxy.getId(), proxy);
        }
    }
}