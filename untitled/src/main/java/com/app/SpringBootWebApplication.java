package com.app;

import com.app.fku.a101.fonksiyon.impl.A101GenelServiceImpl;
import com.app.fku.a101.fonksiyon.service.A101UrunToplaFonksiyon;
import com.app.fku.a101.repository.A101ConfRepository;
import com.app.fku.a101.repository.A101KategoriRepository;
import com.app.fku.amazon.fonksiyon.service.AmzEkranOkumaService;
import com.app.fku.amazon.fonksiyon.service.AmzUrunToplaFonksiyon;
import com.app.fku.amazon.fonksiyon.service.AmzWorkerService;
import com.app.fku.amazon.repository.AmzConfRepository;
import com.app.fku.amazon.repository.AmzKategoriRepository;
import com.app.fku.amazon.repository.AmzUrunRepository;
import com.app.fku.amazonx.fonksiyon.service.AmazonxService;
import com.app.fku.amazonx.fonksiyon.threadclass.AmazonxThread;
import com.app.fku.arzum.fonksiyon.service.ArzumService;
import com.app.fku.arzum.fonksiyon.threadclass.ArzumThread;
import com.app.fku.bim.fonksiyon.service.BimUrunToplaFonksiyon;
import com.app.fku.bim.repository.BimConfRepository;
import com.app.fku.bim.repository.BimKategoriRepository;
import com.app.fku.dyson.fonksiyon.impl.DysonGenelServiceImpl;
import com.app.fku.dyson.fonksiyon.service.DysonEkranOkumaService;
import com.app.fku.dyson.fonksiyon.service.DysonService;
import com.app.fku.dyson.fonksiyon.threadclass.DysonThread;
import com.app.fku.dyson.repository.DysonConfRepository;
import com.app.fku.dyson.repository.DysonKategoriRepository;
import com.app.fku.galerycristal.fonksiyon.impl.GcGenelServiceImpl;
import com.app.fku.galerycristal.fonksiyon.service.GcEkranOkumaService;
import com.app.fku.galerycristal.repository.GcConfRepository;
import com.app.fku.galerycristal.repository.GcKategoriRepository;
import com.app.fku.genel.entity.FkuConf;
import com.app.fku.genel.entity.Header;
import com.app.fku.genel.entity.Proxy;
import com.app.fku.genel.enums.ThreadTypeEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.fonksiyon.threadclass.MailSenderThread;
import com.app.fku.genel.repository.*;
import com.app.fku.hepsiburada.fonksiyon.impl.*;
import com.app.fku.hepsiburada.fonksiyon.service.HbGenelServiceImpl;
import com.app.fku.hepsiburada.fonksiyon.threadclass.HepsiBuradaJSONThread;
import com.app.fku.hepsiburada.fonksiyon.threadclass.HepsiBuradaSepetJSONThread;
import com.app.fku.hepsiburada.repository.*;
import com.app.fku.instagram.fonksiyon.service.InsEkranOkumaService;
import com.app.fku.instagram.repository.InsConfRepository;
import com.app.fku.itopya.fonksiyon.impl.ItopyaGenelServiceImpl;
import com.app.fku.itopya.fonksiyon.service.ItopyaUrunToplaFonksiyon;
import com.app.fku.itopya.repository.ItopyaConfRepository;
import com.app.fku.itopya.repository.ItopyaKategoriRepository;
import com.app.fku.karaca.fonksiyon.impl.KrcGenelServiceImpl;
import com.app.fku.karaca.fonksiyon.service.KaracaJSONService;
import com.app.fku.karaca.fonksiyon.service.KrcEkranOkumaService;
import com.app.fku.karaca.fonksiyon.threadclass.KaracaJSONThread;
import com.app.fku.karaca.repository.KrcConfRepository;
import com.app.fku.karaca.repository.KrcKategoriRepository;
import com.app.fku.medimarkt.fonksiyon.impl.MmGenelServiceImpl;
import com.app.fku.medimarkt.fonksiyon.service.MmEkranOkumaService;
import com.app.fku.medimarkt.repository.MmConfRepository;
import com.app.fku.medimarkt.repository.MmKategoriRepository;
import com.app.fku.migrossanal.fonksiyon.service.MsEkranOkumaService;
import com.app.fku.migrossanal.repository.MsConfRepository;
import com.app.fku.migrossanal.repository.MsKategoriRepository;
import com.app.fku.mutfakdunyasi.fonksiyon.impl.MdGenelServiceImpl;
import com.app.fku.mutfakdunyasi.fonksiyon.service.MdEkranOkumaService;
import com.app.fku.mutfakdunyasi.repository.MdConfRepository;
import com.app.fku.mutfakdunyasi.repository.MdKategoriRepository;
import com.app.fku.n11.fonksiyon.service.N11JSONService;
import com.app.fku.tefal.fonksiyon.impl.TefalGenelServiceImpl;
import com.app.fku.tefal.fonksiyon.service.TefalEkranOkumaService;
import com.app.fku.tefal.repository.TefalConfRepository;
import com.app.fku.tefal.repository.TefalKategoriRepository;
import com.app.fku.teknosa.fonksiyon.impl.TknGenelServiceImpl;
import com.app.fku.teknosa.fonksiyon.service.TknEkranOkumaService;
import com.app.fku.teknosa.repository.TknConfRepository;
import com.app.fku.teknosa.repository.TknKategoriRepository;
import com.app.fku.teknosa.repository.TknUrunRepository;
import com.app.fku.trendyol.fonksiyon.impl.TrendyolGenelServiceImpl;
import com.app.fku.trendyol.fonksiyon.service.TrendyolJSONService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolUrunToplaFonksiyon;
import com.app.fku.trendyol.fonksiyon.threadclass.TrendyolJSONThread;
import com.app.fku.trendyol.fonksiyon.utils.TrendyolUtilsInterface;
import com.app.fku.trendyol.repository.*;
import com.app.fku.turkcell.fonksiyon.impl.TurkcellGenelServiceImpl;
import com.app.fku.turkcell.fonksiyon.service.TurkcellEkranOkumaService;
import com.app.fku.turkcell.repository.TurkcellConfRepository;
import com.app.fku.turkcell.repository.TurkcellKategoriRepository;
import com.app.fku.vatan.fonksiyon.impl.VatanGenelServiceImpl;
import com.app.fku.vatan.fonksiyon.service.VatanEkranOkumaService;
import com.app.fku.vatan.fonksiyon.service.VatanService;
import com.app.fku.vatan.repository.VatanConfRepository;
import com.app.fku.vatan.repository.VatanKategoriRepository;
import com.app.fku.yemeksepeti.fonksiyon.service.YmkEkranOkumaService;
import com.app.fku.yemeksepeti.repository.YmkConfRepository;
import com.app.fku.yeniamazon.fonksiyon.impl.YeniAmazonGenelServiceImpl;
import com.app.fku.yeniamazon.fonksiyon.service.YeniAmazonEkranOkumaService;
import com.app.fku.yeniamazon.repository.YeniAmazonConfRepository;
import com.app.fku.yeniamazon.repository.YeniAmazonKategoriRepository;
import com.app.fku.yenimediamarkt.fonksiyon.service.MediaMarktService;
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
    HbFiyatToplaFonksiyon hbFiyatToplaFonksiyon;
    @Autowired
    HbUrunRepository hbUrunRepository;
    @Autowired
    HbKategoriRepository hbKategoriRepository;
    @Autowired
    HbConfRepository hbConfRepository;
    @Autowired
    HbGenelService hbGenelService;
    @Autowired
    HbWorkerService hbWorkerService;
    @Autowired
    HbSearcherService hbSearcherService;
    @Autowired
    HbSepetEklemeRepository hbSepetEklemeRepository;
    @Autowired
    HbUyelikService hbUyelikService;
    @Autowired
    HbUrunToplaFonksiyon hbUrunToplaFonksiyon;
    @Autowired
    HbKahramanRepository hbKahramanRepository;
    @Autowired
    HepsiBuradaJSONService hepsiBuradaJSONService;
    @Autowired
    HepsiBuradaSepetJSONService hepsiBuradaSepetJSONService;
    @Autowired
    HepsiBuradaKuponJSONService hepsiBuradaKuponJSONService;
    //Hepsi Burada

    //Hepsi Burada Fırsat
    @Autowired
    HbFirsatService hbFirsatService;
    //Hepsi Burada Fırsat

    //KARACA
    @Autowired
    KaracaJSONService karacaJSONService;
    //KARACA

    //N11
    @Autowired
    N11JSONService n11JSONService;
    //N11

    //DYSON
    @Autowired
    DysonService dysonService;
    //DYSON

    //VATAN
    @Autowired
    VatanService vatanService;
    //VATAN

    //ARZUM
    @Autowired
    ArzumService arzumService;
    //ARZUM

    //MEDIAMARKT
    @Autowired
    MediaMarktService mediaMarktService;
    //MEDIAMARKT

    //Amazonx
    @Autowired
    AmazonxService amazonxService;
    //Amazonx

    //Amazon
    @Autowired
    AmzConfRepository amzConfRepository;
    @Autowired
    AmzEkranOkumaService amzEkranOkumaService;
    @Autowired
    AmzKategoriRepository amzKategoriRepository;
    @Autowired
    AmzUrunRepository amzUrunRepository;
    @Autowired
    AmzWorkerService amzWorkerService;
    @Autowired
    AmzUrunToplaFonksiyon amzUrunToplaFonksiyon;
    //Amazon

    //A101
    @Autowired
    A101ConfRepository a101ConfRepository;
    @Autowired
    A101KategoriRepository a101KategoriRepository;
    @Autowired
    A101UrunToplaFonksiyon a101UrunToplaFonksiyon;
    //A101

    //ITOPYA
    @Autowired
    ItopyaConfRepository itopyaConfRepository;
    @Autowired
    ItopyaKategoriRepository itopyaKategoriRepository;
    @Autowired
    ItopyaUrunToplaFonksiyon itopyaUrunToplaFonksiyon;
    //ITOPYA

    //BIM
    @Autowired
    BimConfRepository bimConfRepository;
    @Autowired
    BimKategoriRepository bimKategoriRepository;
    @Autowired
    BimUrunToplaFonksiyon bimUrunToplaFonksiyon;
    //BIM

    //Teknosa
    @Autowired
    TknConfRepository tknConfRepository;
    @Autowired
    TknKategoriRepository tknKategoriRepository;
    @Autowired
    TknEkranOkumaService tknEkranOkumaService;
    @Autowired
    TknUrunRepository tknUrunRepository;
    //Teknosa

    //TEFAL
    @Autowired
    TefalConfRepository tefalConfRepository;
    @Autowired
    TefalKategoriRepository tefalKategoriRepository;
    @Autowired
    TefalEkranOkumaService tefalEkranOkumaService;
    //TEFAL

    //TURKCELL
    @Autowired
    TurkcellConfRepository turkcellConfRepository;
    @Autowired
    TurkcellKategoriRepository turkcellKategoriRepository;
    @Autowired
    TurkcellEkranOkumaService turkcellEkranOkumaService;
    //TURKCELL

    //GC
    @Autowired
    GcConfRepository gcConfRepository;
    @Autowired
    GcKategoriRepository gcKategoriRepository;
    @Autowired
    GcEkranOkumaService gcEkranOkumaService;
    //GC

    //MD
    @Autowired
    MdConfRepository mdConfRepository;
    @Autowired
    MdKategoriRepository mdKategoriRepository;
    @Autowired
    MdEkranOkumaService mdEkranOkumaService;
    //MD

    //KRC
    @Autowired
    KrcConfRepository krcConfRepository;
    @Autowired
    KrcKategoriRepository krcKategoriRepository;
    @Autowired
    KrcEkranOkumaService krcEkranOkumaService;
    //KRC

    //VATAN
    @Autowired
    VatanConfRepository vatanConfRepository;
    @Autowired
    VatanKategoriRepository vatanKategoriRepository;
    @Autowired
    VatanEkranOkumaService vatanEkranOkumaService;
    //VATAN

    //YENİ AMAZON
    @Autowired
    YeniAmazonConfRepository yeniAmazonConfRepository;
    @Autowired
    YeniAmazonKategoriRepository yeniAmazonKategoriRepository;
    @Autowired
    YeniAmazonEkranOkumaService yeniAmazonEkranOkumaService;
    //YENİ AMAZON

    //DYSON
    @Autowired
    DysonConfRepository dysonConfRepository;
    @Autowired
    DysonKategoriRepository dysonKategoriRepository;
    @Autowired
    DysonEkranOkumaService dysonEkranOkumaService;
    //DYSON

    //Media Markt
    @Autowired
    MmConfRepository mmConfRepository;
    @Autowired
    MmEkranOkumaService mmEkranOkumaService;
    @Autowired
    MmKategoriRepository mmKategoriRepository;
    //Media Markt

    //Migros Sanal
    @Autowired
    MsConfRepository msConfRepository;
    @Autowired
    MsEkranOkumaService msEkranOkumaService;
    @Autowired
    MsKategoriRepository msKategoriRepository;
    //Migros Sanal

    //Trendyol
    @Autowired
    TrendyolConfRepository trendyolConfRepository;
    @Autowired
    TrendyolUyelikRepository trendyolUyelikRepository;
    @Autowired
    TrendyolUyelikEskiRepository trendyolUyelikEskiRepository;
    @Autowired
    TrendyolUtilsInterface trendyolUtilsInterface;
    @Autowired
    TrendyolKategoriRepository trendyolKategoriRepository;
    @Autowired
    TrendyolUrunRepository trendyolUrunRepository;
    @Autowired
    TrendyolUrunToplaFonksiyon trendyolUrunToplaFonksiyon;
    @Autowired
    TrendyolKahramanRepository trendyolKahramanRepository;
    @Autowired
    TrendyolJSONService trendyolJSONService;
    //Trendyol

    //Instagram
    @Autowired
    InsConfRepository insConfRepository;
    @Autowired
    InsEkranOkumaService insEkranOkumaService;
    //Instagram

    //Yemek Sepeti
    @Autowired
    YmkConfRepository ymkConfRepository;
    @Autowired
    YmkEkranOkumaService ymkEkranOkumaService;
    //Yemek Sepeti

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws IOException, InterruptedException {

        String hostname = genelService.getHostName();
        //hostname = hostname + "1";
        System.out.println("Makine IP: " + hostname);

        //genelService.telegramMesajGonder(hostname, "-659185836", 10L);

        //JSONLAR

        /**
         Thread[] threadsKuponHB = new Thread[1];
         HepsiBuradaKuponJSONThread[] hepsiBuradaKuponJSONThreads = new HepsiBuradaKuponJSONThread[1];
         hepsiBuradaKuponJSONThreads[0] = new HepsiBuradaKuponJSONThread();
         hepsiBuradaKuponJSONThreads[0].hepsiBuradaKuponJSONService = hepsiBuradaKuponJSONService;
         threadsKuponHB[0] = new Thread(hepsiBuradaKuponJSONThreads[0]);
         threadsKuponHB[0].start();*/

        Thread[] threadsHB = new Thread[1];
        HepsiBuradaJSONThread[] hepsiBuradaJSONThreads = new HepsiBuradaJSONThread[1];
        hepsiBuradaJSONThreads[0] = new HepsiBuradaJSONThread();
        hepsiBuradaJSONThreads[0].hepsiBuradaJSONService = hepsiBuradaJSONService;
        threadsHB[0] = new Thread(hepsiBuradaJSONThreads[0]);
        threadsHB[0].start();

        Thread[] threadsHBSepet = new Thread[1];
        HepsiBuradaSepetJSONThread[] hepsiBuradaSepetJSONThreads = new HepsiBuradaSepetJSONThread[1];
        hepsiBuradaSepetJSONThreads[0] = new HepsiBuradaSepetJSONThread();
        hepsiBuradaSepetJSONThreads[0].hepsiBuradaSepetJSONService = hepsiBuradaSepetJSONService;
        threadsHBSepet[0] = new Thread(hepsiBuradaSepetJSONThreads[0]);
        threadsHBSepet[0].start();

        Thread[] threadsKaraca = new Thread[1];
        KaracaJSONThread[] karacaJSONThreads = new KaracaJSONThread[1];
        karacaJSONThreads[0] = new KaracaJSONThread();
        karacaJSONThreads[0].karacaJSONService = karacaJSONService;
        threadsKaraca[0] = new Thread(karacaJSONThreads[0]);
        threadsKaraca[0].start();

        Thread[] threadsTY = new Thread[1];
        TrendyolJSONThread[] trendyolJSONThreads = new TrendyolJSONThread[1];
        trendyolJSONThreads[0] = new TrendyolJSONThread();
        trendyolJSONThreads[0].trendyolJSONService = trendyolJSONService;
        threadsTY[0] = new Thread(trendyolJSONThreads[0]);
        threadsTY[0].start();

        /**
        Thread[] threadsN11 = new Thread[1];
        N11JSONThread[] n11JSONThreads = new N11JSONThread[1];
        n11JSONThreads[0] = new N11JSONThread();
        n11JSONThreads[0].n11JSONService = n11JSONService;
        threadsN11[0] = new Thread(n11JSONThreads[0]);
        threadsN11[0].start();*/

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
        threadsArzum[0].start();

        /**
        Thread[] threadsVatan = new Thread[1];
        VatanThread[] vatanThreads = new VatanThread[1];
        vatanThreads[0] = new VatanThread();
        vatanThreads[0].vatanService = vatanService;
        threadsVatan[0] = new Thread(vatanThreads[0]);
        threadsVatan[0].start();*/

        /**
        Thread[] threadsMediaMarkt = new Thread[1];
        MediaMarktThread[] mediaMarktThreads = new MediaMarktThread[1];
        mediaMarktThreads[0] = new MediaMarktThread();
        mediaMarktThreads[0].mediaMarktService = mediaMarktService;
        threadsMediaMarkt[0] = new Thread(mediaMarktThreads[0]);
        threadsMediaMarkt[0].start();*/

        Thread[] threadsAmazonx = new Thread[1];
        AmazonxThread[] amazonxThreads = new AmazonxThread[1];
        amazonxThreads[0] = new AmazonxThread();
        amazonxThreads[0].amazonxService = amazonxService;
        threadsAmazonx[0] = new Thread(amazonxThreads[0]);
        threadsAmazonx[0].start();
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

        //YENİ AMAZON START
        /**
         List<YeniAmazonConf> yeniAmazonConfList = yeniAmazonConfRepository.findByHostname(hostname);
         for (YeniAmazonConf yeniAmazonConf: yeniAmazonConfList) {
         if (yeniAmazonConf.getThreadTypeEnum().equals(ThreadTypeEnum.YENI_AMZ_SEARCHER) && yeniAmazonConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[yeniAmazonConf.getThreadCount()];
         YeniAmazonSearcherThread[] yeniAmazonSearcherThreads = new YeniAmazonSearcherThread[yeniAmazonConf.getThreadCount()];
         for (int i = 0; i < yeniAmazonConf.getThreadCount(); i++) {
         yeniAmazonSearcherThreads[i] = new YeniAmazonSearcherThread();
         yeniAmazonSearcherThreads[i].yeniAmazonKategoriRepository = yeniAmazonKategoriRepository;
         yeniAmazonSearcherThreads[i].threadSirasi = i;
         yeniAmazonSearcherThreads[i].kategoriId = yeniAmazonConf.getCategory();
         yeniAmazonSearcherThreads[i].yeniAmazonEkranOkumaService = yeniAmazonEkranOkumaService;
         yeniAmazonSearcherThreads[i].logService = logService;
         threads[i] = new Thread(yeniAmazonSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //YENİ AMAZON END

        //KARACA START

        //KARACA END

        //HB START
/**
 Thread[] tmpThreads = new Thread[3];
 HbFirsatThread[] tmpFirsatThreads = new HbFirsatThread[3];
 tmpFirsatThreads[0] = new HbFirsatThread();
 tmpFirsatThreads[0].hbFirsatService = hbFirsatService;
 tmpFirsatThreads[0].logService = logService;
 tmpFirsatThreads[0].eposta = "atlastek1@atlasposta.com";
 tmpThreads[0] = new Thread(tmpFirsatThreads[0]);
 tmpThreads[0].start();

 TimeUnit.SECONDS.sleep(3);

 tmpFirsatThreads[1] = new HbFirsatThread();
 tmpFirsatThreads[1].hbFirsatService = hbFirsatService;
 tmpFirsatThreads[1].logService = logService;
 tmpFirsatThreads[1].eposta = "meryemkaya@atlasposta.com";
 tmpThreads[1] = new Thread(tmpFirsatThreads[1]);
 tmpThreads[1].start();

 TimeUnit.SECONDS.sleep(3);

 tmpFirsatThreads[2] = new HbFirsatThread();
 tmpFirsatThreads[2].hbFirsatService = hbFirsatService;
 tmpFirsatThreads[2].logService = logService;
 tmpFirsatThreads[2].eposta = "ahmetkara@atlasposta.com";
 tmpThreads[2] = new Thread(tmpFirsatThreads[2]);
 tmpThreads[2].start();


 List<HbConf> hbConfList = hbConfRepository.findByHostname(hostname);
 for (HbConf hbConf : hbConfList) {
 if (hbConf.getThreadTypeEnum().equals(ThreadTypeEnum.HB_WORKER)) {
 Thread[] threads = new Thread[hbConf.getThreadCount()];
 HbWorkerThread[] hbWorkerThreadArr = new HbWorkerThread[hbConf.getThreadCount()];
 for (int i = 0; i < hbConf.getThreadCount(); i++) {
 hbWorkerThreadArr[i] = new HbWorkerThread();
 hbWorkerThreadArr[i].kategoriId = hbConf.getCategory();
 hbWorkerThreadArr[i].threadSirasi = i;
 hbWorkerThreadArr[i].workerThreadCount = hbConf.getThreadCount();
 hbWorkerThreadArr[i].hbKategoriRepository = hbKategoriRepository;
 hbWorkerThreadArr[i].hbUrunRepository = hbUrunRepository;
 hbWorkerThreadArr[i].hbWorkerService = hbWorkerService;
 threads[i] = new Thread(hbWorkerThreadArr[i]);
 threads[i].start();
 TimeUnit.SECONDS.sleep(3);
 }
 } else if (hbConf.getThreadTypeEnum().equals(ThreadTypeEnum.HB_SEARCHER)) {
 Thread[] threads = new Thread[hbConf.getThreadCount()];
 HbSearcherThread[] hbSearcherThreadArr = new HbSearcherThread[hbConf.getThreadCount()];
 for (int i = 0; i < hbConf.getThreadCount(); i++) {
 hbSearcherThreadArr[i] = new HbSearcherThread();
 hbSearcherThreadArr[i].kategoriId = hbConf.getCategory();
 hbSearcherThreadArr[i].hbKategoriRepository = hbKategoriRepository;
 hbSearcherThreadArr[i].threadSirasi = i;
 hbSearcherThreadArr[i].hbUrunRepository = hbUrunRepository;
 hbSearcherThreadArr[i].hbSearcherService = hbSearcherService;
 threads[i] = new Thread(hbSearcherThreadArr[i]);
 threads[i].start();
 TimeUnit.SECONDS.sleep(3);
 }
 } else if (hbConf.getThreadTypeEnum().equals(ThreadTypeEnum.HB_UYELIK_AC)) {
 Thread[] threads = new Thread[hbConf.getThreadCount()];
 HbUyelikAcThread[] hbUyelikAcThreads = new HbUyelikAcThread[hbConf.getThreadCount()];
 for (int i = 0; i < hbConf.getThreadCount(); i++) {
 hbUyelikAcThreads[i] = new HbUyelikAcThread();
 hbUyelikAcThreads[i].hbUyelikService = hbUyelikService;
 threads[i] = new Thread(hbUyelikAcThreads[i]);
 threads[i].start();
 TimeUnit.SECONDS.sleep(3);
 }
 } else if (hbConf.getThreadTypeEnum().equals(ThreadTypeEnum.HB_KUPON_KONTROL)) {
 Thread[] threads = new Thread[hbConf.getThreadCount()];
 HbKuponKontrolThread[] hbKuponKontrolThreads = new HbKuponKontrolThread[hbConf.getThreadCount()];
 for (int i = 0; i < hbConf.getThreadCount(); i++) {
 hbKuponKontrolThreads[i] = new HbKuponKontrolThread();
 hbKuponKontrolThreads[i].hbUyelikService = hbUyelikService;
 threads[i] = new Thread(hbKuponKontrolThreads[i]);
 threads[i].start();
 TimeUnit.SECONDS.sleep(3);
 }
 } else if (hbConf.getThreadTypeEnum().equals(ThreadTypeEnum.HB_NEW_SEARCHER) && hbConf.getThreadCount() > 0) {
 Thread[] threads = new Thread[hbConf.getThreadCount()];
 HbNewSearcherThread[] hbNewSearcherThreads = new HbNewSearcherThread[hbConf.getThreadCount()];
 for (int i = 0; i < hbConf.getThreadCount(); i++) {
 hbNewSearcherThreads[i] = new HbNewSearcherThread();
 hbNewSearcherThreads[i].hbKategoriRepository = hbKategoriRepository;
 hbNewSearcherThreads[i].hbKahramanRepository = hbKahramanRepository;
 hbNewSearcherThreads[i].threadSirasi = i;
 hbNewSearcherThreads[i].kategoriId = hbConf.getCategory();
 hbNewSearcherThreads[i].hbUrunToplaFonksiyon = hbUrunToplaFonksiyon;
 hbNewSearcherThreads[i].logService = logService;
 threads[i] = new Thread(hbNewSearcherThreads[i]);
 threads[i].start();
 TimeUnit.SECONDS.sleep(3);
 }
 }
 }
 */
        //HB END

        //AMZ START
        /**
         Thread[] threadsManuel = new Thread[1];
         AmzWorkerManuelThread[] amzWorkerManuelThreads = new AmzWorkerManuelThread[1];
         amzWorkerManuelThreads[0] = new AmzWorkerManuelThread();
         threadsManuel[0] = new Thread(amzWorkerManuelThreads[0]);
         threadsManuel[0].start();
         TimeUnit.SECONDS.sleep(3);
         */
/**
 List<AmzConf> amzConfList = amzConfRepository.findByHostname(hostname);
 for (AmzConf amzConf: amzConfList) {
 if (amzConf.getThreadTypeEnum().equals(ThreadTypeEnum.AMZ_SEARCHER) && amzConf.getThreadCount() > 0) {
 Thread[] threads = new Thread[amzConf.getThreadCount()];
 AmzSearcherThread[] amzSearcherThreadArr = new AmzSearcherThread[amzConf.getThreadCount()];
 for (int i = 0; i < amzConf.getThreadCount(); i++) {
 amzSearcherThreadArr[i] = new AmzSearcherThread();
 amzSearcherThreadArr[i].amzEkranOkumaService = amzEkranOkumaService;
 amzSearcherThreadArr[i].threadSirasi = i;
 amzSearcherThreadArr[i].amzKategoriRepository = amzKategoriRepository;
 threads[i] = new Thread(amzSearcherThreadArr[i]);
 threads[i].start();
 TimeUnit.SECONDS.sleep(3);
 }
 } else if (amzConf.getThreadTypeEnum().equals(ThreadTypeEnum.AMZ_WORKER) && amzConf.getThreadCount() > 0) {
 Thread[] threads = new Thread[amzConf.getThreadCount()];
 AmzWorkerThread[] amzWorkerThreadArr = new AmzWorkerThread[amzConf.getThreadCount()];
 for (int i = 0; i < amzConf.getThreadCount(); i++) {
 amzWorkerThreadArr[i] = new AmzWorkerThread();
 amzWorkerThreadArr[i].kategoriId = amzConf.getCategory();
 amzWorkerThreadArr[i].threadSirasi = i;
 amzWorkerThreadArr[i].workerThreadCount = amzConf.getThreadCount();
 amzWorkerThreadArr[i].amzKategoriRepository = amzKategoriRepository;
 amzWorkerThreadArr[i].amzUrunRepository = amzUrunRepository;
 amzWorkerThreadArr[i].amzEkranOkumaService = amzEkranOkumaService;
 amzWorkerThreadArr[i].amzWorkerService = amzWorkerService;
 threads[i] = new Thread(amzWorkerThreadArr[i]);
 threads[i].start();
 TimeUnit.SECONDS.sleep(3);
 }
 } else if (amzConf.getThreadTypeEnum().equals(ThreadTypeEnum.AMZ_NEW_SEARCHER) && amzConf.getThreadCount() > 0) {
 Thread[] threads = new Thread[amzConf.getThreadCount()];
 AmzNewSearcherThread[] amzNewSearcherThreads = new AmzNewSearcherThread[amzConf.getThreadCount()];
 for (int i = 0; i < amzConf.getThreadCount(); i++) {
 amzNewSearcherThreads[i] = new AmzNewSearcherThread();
 amzNewSearcherThreads[i].amzKategoriRepository = amzKategoriRepository;
 amzNewSearcherThreads[i].threadSirasi = i;
 amzNewSearcherThreads[i].kategoriId = amzConf.getCategory();
 amzNewSearcherThreads[i].amzUrunToplaFonksiyon = amzUrunToplaFonksiyon;
 threads[i] = new Thread(amzNewSearcherThreads[i]);
 threads[i].start();
 TimeUnit.SECONDS.sleep(3);
 }
 } else if (amzConf.getThreadTypeEnum().equals(ThreadTypeEnum.AMZ_NEW_MENU_SEARCHER) && amzConf.getThreadCount() > 0) {
 Thread[] threads = new Thread[amzConf.getThreadCount()];
 AmzNewMenuSearcherThread[] amzNewSearcherThreads = new AmzNewMenuSearcherThread[amzConf.getThreadCount()];
 for (int i = 0; i < amzConf.getThreadCount(); i++) {
 amzNewSearcherThreads[i] = new AmzNewMenuSearcherThread();
 amzNewSearcherThreads[i].amzKategoriRepository = amzKategoriRepository;
 amzNewSearcherThreads[i].threadSirasi = i;
 amzNewSearcherThreads[i].kategoriId = amzConf.getCategory();
 amzNewSearcherThreads[i].amzUrunToplaFonksiyon = amzUrunToplaFonksiyon;
 threads[i] = new Thread(amzNewSearcherThreads[i]);
 threads[i].start();
 TimeUnit.SECONDS.sleep(3);
 }
 }
 }*/
        //AMZ END

        //A101 START
        /**
         List<A101Conf> a101ConfList = a101ConfRepository.findByHostname(hostname);
         for (A101Conf a101Conf: a101ConfList) {
         if (a101Conf.getThreadTypeEnum().equals(ThreadTypeEnum.A101_SEARCHER) && a101Conf.getThreadCount() > 0) {
         Thread[] threads = new Thread[a101Conf.getThreadCount()];
         A101SearcherThread[] a101SearcherThreads = new A101SearcherThread[a101Conf.getThreadCount()];
         for (int i = 0; i < a101Conf.getThreadCount(); i++) {
         a101SearcherThreads[i] = new A101SearcherThread();
         a101SearcherThreads[i].a101KategoriRepository = a101KategoriRepository;
         a101SearcherThreads[i].threadSirasi = i;
         a101SearcherThreads[i].kategoriId = a101Conf.getCategory();
         a101SearcherThreads[i].a101UrunToplaFonksiyon = a101UrunToplaFonksiyon;
         a101SearcherThreads[i].logService = logService;
         threads[i] = new Thread(a101SearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //A101 END

        //BIM START
        /**
         List<BimConf> bimConfList = bimConfRepository.findByHostname(hostname);
         for (BimConf bimConf: bimConfList) {
         if (bimConf.getThreadTypeEnum().equals(ThreadTypeEnum.BIM_SEARCHER) && bimConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[bimConf.getThreadCount()];
         BimSearcherThread[] bimSearcherThreads = new BimSearcherThread[bimConf.getThreadCount()];
         for (int i = 0; i < bimConf.getThreadCount(); i++) {
         bimSearcherThreads[i] = new BimSearcherThread();
         bimSearcherThreads[i].bimKategoriRepository = bimKategoriRepository;
         bimSearcherThreads[i].threadSirasi = i;
         bimSearcherThreads[i].kategoriId = bimConf.getCategory();
         bimSearcherThreads[i].bimUrunToplaFonksiyon = bimUrunToplaFonksiyon;
         threads[i] = new Thread(bimSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //BIM END

        //TKN START
        /**
         List<TknConf> tknConfList = tknConfRepository.findByHostname(hostname);
         for (TknConf tknConf: tknConfList) {
         if (tknConf.getThreadTypeEnum().equals(ThreadTypeEnum.TKN_SEARCHER) && tknConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[tknConf.getThreadCount()];
         TknSearcherThread[] tknSearcherThreads = new TknSearcherThread[tknConf.getThreadCount()];
         for (int i = 0; i < tknConf.getThreadCount(); i++) {
         tknSearcherThreads[i] = new TknSearcherThread();
         tknSearcherThreads[i].tknKategoriRepository = tknKategoriRepository;
         tknSearcherThreads[i].threadSirasi = i;
         tknSearcherThreads[i].kategoriId = tknConf.getCategory();
         tknSearcherThreads[i].tknEkranOkumaService = tknEkranOkumaService;
         tknSearcherThreads[i].logService = logService;
         threads[i] = new Thread(tknSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }  else if (tknConf.getThreadTypeEnum().equals(ThreadTypeEnum.TKN_WORKER) && tknConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[tknConf.getThreadCount()];
         TknWorkerThread[] tknWorkerThreads = new TknWorkerThread[tknConf.getThreadCount()];
         for (int i = 0; i < tknConf.getThreadCount(); i++) {
         tknWorkerThreads[i] = new TknWorkerThread();
         tknWorkerThreads[i].threadSirasi = i;
         tknWorkerThreads[i].workerThreadCount = tknConf.getThreadCount();
         tknWorkerThreads[i].tknEkranOkumaService = tknEkranOkumaService;
         tknWorkerThreads[i].tknUrunRepository = tknUrunRepository;
         threads[i] = new Thread(tknWorkerThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //TKN END

        //TEFAL START
        /**
         List<TefalConf> tefalConfList = tefalConfRepository.findByHostname(hostname);
         for (TefalConf tefalConf: tefalConfList) {
         if (tefalConf.getThreadTypeEnum().equals(ThreadTypeEnum.TEFAL_SEARCHER) && tefalConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[tefalConf.getThreadCount()];
         TefalSearcherThread[] tefalSearcherThreads = new TefalSearcherThread[tefalConf.getThreadCount()];
         for (int i = 0; i < tefalConf.getThreadCount(); i++) {
         tefalSearcherThreads[i] = new TefalSearcherThread();
         tefalSearcherThreads[i].tefalKategoriRepository = tefalKategoriRepository;
         tefalSearcherThreads[i].threadSirasi = i;
         tefalSearcherThreads[i].kategoriId = tefalConf.getCategory();
         tefalSearcherThreads[i].tefalEkranOkumaService = tefalEkranOkumaService;
         tefalSearcherThreads[i].logService = logService;
         threads[i] = new Thread(tefalSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //TEFAL END

        //TURKCELL START
        /**
         List<TurkcellConf> turkcellConfList = turkcellConfRepository.findByHostname(hostname);
         for (TurkcellConf turkcellConf: turkcellConfList) {
         if (turkcellConf.getThreadTypeEnum().equals(ThreadTypeEnum.TURKCELL_SEARCHER) && turkcellConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[turkcellConf.getThreadCount()];
         TurkcellSearcherThread[] turkcellSearcherThreads = new TurkcellSearcherThread[turkcellConf.getThreadCount()];
         for (int i = 0; i < turkcellConf.getThreadCount(); i++) {
         turkcellSearcherThreads[i] = new TurkcellSearcherThread();
         turkcellSearcherThreads[i].turkcellKategoriRepository = turkcellKategoriRepository;
         turkcellSearcherThreads[i].threadSirasi = i;
         turkcellSearcherThreads[i].kategoriId = turkcellConf.getCategory();
         turkcellSearcherThreads[i].turkcellEkranOkumaService = turkcellEkranOkumaService;
         turkcellSearcherThreads[i].logService = logService;
         threads[i] = new Thread(turkcellSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //TURKCELL END

        //GC START
        /**
         List<GcConf> gcConfList = gcConfRepository.findByHostname(hostname);
         for (GcConf gcConf: gcConfList) {
         if (gcConf.getThreadTypeEnum().equals(ThreadTypeEnum.GC_SEARCHER) && gcConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[gcConf.getThreadCount()];
         GcSearcherThread[] gcSearcherThreads = new GcSearcherThread[gcConf.getThreadCount()];
         for (int i = 0; i < gcConf.getThreadCount(); i++) {
         gcSearcherThreads[i] = new GcSearcherThread();
         gcSearcherThreads[i].gcKategoriRepository = gcKategoriRepository;
         gcSearcherThreads[i].threadSirasi = i;
         gcSearcherThreads[i].kategoriId = gcConf.getCategory();
         gcSearcherThreads[i].gcEkranOkumaService = gcEkranOkumaService;
         gcSearcherThreads[i].logService = logService;
         threads[i] = new Thread(gcSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //GC END

        //MD START
        /**
         List<MdConf> mdConfList = mdConfRepository.findByHostname(hostname);
         for (MdConf mdConf: mdConfList) {
         if (mdConf.getThreadTypeEnum().equals(ThreadTypeEnum.MD_SEARCHER) && mdConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[mdConf.getThreadCount()];
         MdSearcherThread[] mdSearcherThreads = new MdSearcherThread[mdConf.getThreadCount()];
         for (int i = 0; i < mdConf.getThreadCount(); i++) {
         mdSearcherThreads[i] = new MdSearcherThread();
         mdSearcherThreads[i].mdKategoriRepository = mdKategoriRepository;
         mdSearcherThreads[i].threadSirasi = i;
         mdSearcherThreads[i].kategoriId = mdConf.getCategory();
         mdSearcherThreads[i].mdEkranOkumaService = mdEkranOkumaService;
         mdSearcherThreads[i].logService = logService;
         threads[i] = new Thread(mdSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //MD END

        //VATAN START
        /**
         List<VatanConf> vatanConfList = vatanConfRepository.findByHostname(hostname);
         for (VatanConf vatanConf: vatanConfList) {
         if (vatanConf.getThreadTypeEnum().equals(ThreadTypeEnum.VATAN_SEARCHER) && vatanConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[vatanConf.getThreadCount()];
         VatanSearcherThread[] vatanSearcherThreads = new VatanSearcherThread[vatanConf.getThreadCount()];
         for (int i = 0; i < vatanConf.getThreadCount(); i++) {
         vatanSearcherThreads[i] = new VatanSearcherThread();
         vatanSearcherThreads[i].vatanKategoriRepository = vatanKategoriRepository;
         vatanSearcherThreads[i].threadSirasi = i;
         vatanSearcherThreads[i].kategoriId = vatanConf.getCategory();
         vatanSearcherThreads[i].vatanEkranOkumaService = vatanEkranOkumaService;
         vatanSearcherThreads[i].logService = logService;
         threads[i] = new Thread(vatanSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //VATAN END

        //DYSON START
        /**
         List<DysonConf> dysonConfList = dysonConfRepository.findByHostname(hostname);
         for (DysonConf dysonConf: dysonConfList) {
         if (dysonConf.getThreadTypeEnum().equals(ThreadTypeEnum.DYSON_SEARCHER) && dysonConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[dysonConf.getThreadCount()];
         DysonSearcherThread[] dysonSearcherThreads = new DysonSearcherThread[dysonConf.getThreadCount()];
         for (int i = 0; i < dysonConf.getThreadCount(); i++) {
         dysonSearcherThreads[i] = new DysonSearcherThread();
         dysonSearcherThreads[i].dysonKategoriRepository = dysonKategoriRepository;
         dysonSearcherThreads[i].threadSirasi = i;
         dysonSearcherThreads[i].kategoriId = dysonConf.getCategory();
         dysonSearcherThreads[i].dysonEkranOkumaService = dysonEkranOkumaService;
         dysonSearcherThreads[i].logService = logService;
         threads[i] = new Thread(dysonSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //DYSON END

        //TY START
        /**
         List<TrendyolConf> trendyolConfList = trendyolConfRepository.findByHostname(hostname);
         for (TrendyolConf trendyolConf: trendyolConfList) {
         if (trendyolConf.getThreadTypeEnum().equals(ThreadTypeEnum.TY_KUPON_KONTROL) && trendyolConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[1];
         TrendyolKuponThread[] trendyolKuponThreadArr = new TrendyolKuponThread[1];
         trendyolKuponThreadArr[0] = new TrendyolKuponThread();
         trendyolKuponThreadArr[0].trendyolUtilsInterface = trendyolUtilsInterface;
         trendyolKuponThreadArr[0].trendyolUyelikRepository = trendyolUyelikRepository;
         threads[0] = new Thread(trendyolKuponThreadArr[0]);
         threads[0].start();
         //                Thread[] threads = new Thread[1];
         //                TrendyolKuponEskiThread[] trendyolKuponEskiThreadArr = new TrendyolKuponEskiThread[1];
         //                trendyolKuponEskiThreadArr[0] = new TrendyolKuponEskiThread();
         //                trendyolKuponEskiThreadArr[0].trendyolUtilsInterface = trendyolUtilsInterface;
         //                trendyolKuponEskiThreadArr[0].trendyolUyelikEskiRepository = trendyolUyelikEskiRepository;
         //                threads[0] = new Thread(trendyolKuponEskiThreadArr[0]);
         //                threads[0].start();
         } else if (trendyolConf.getThreadTypeEnum().equals(ThreadTypeEnum.TY_HESAP_ACMA) && trendyolConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[1];
         TrendyolHesapAcThread[] trendyolHesapAcThreadArr = new TrendyolHesapAcThread[1];
         trendyolHesapAcThreadArr[0] = new TrendyolHesapAcThread();
         trendyolHesapAcThreadArr[0].trendyolUtilsInterface = trendyolUtilsInterface;
         threads[0] = new Thread(trendyolHesapAcThreadArr[0]);
         threads[0].start();
         } else if (trendyolConf.getThreadTypeEnum().equals(ThreadTypeEnum.TY_HESAP_SEPET_EKLE) && trendyolConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[1];
         TrendyolHesapSepetEkleThread[] trendyolHesapSepetEkleThreadArr = new TrendyolHesapSepetEkleThread[1];
         trendyolHesapSepetEkleThreadArr[0] = new TrendyolHesapSepetEkleThread();
         trendyolHesapSepetEkleThreadArr[0].trendyolUtilsInterface = trendyolUtilsInterface;
         trendyolHesapSepetEkleThreadArr[0].trendyolUyelikRepository = trendyolUyelikRepository;
         threads[0] = new Thread(trendyolHesapSepetEkleThreadArr[0]);
         threads[0].start();
         } else if (trendyolConf.getThreadTypeEnum().equals(ThreadTypeEnum.TY_SEARCHER)) {
         Thread[] threads = new Thread[trendyolConf.getThreadCount()];
         TrendyolSearcherThread[] tySearcherThreadArr = new TrendyolSearcherThread[trendyolConf.getThreadCount()];
         for (int i = 0; i < trendyolConf.getThreadCount(); i++) {
         tySearcherThreadArr[i] = new TrendyolSearcherThread();
         tySearcherThreadArr[i].kategoriId = trendyolConf.getCategory();
         tySearcherThreadArr[i].trendyolKategoriRepository = trendyolKategoriRepository;
         tySearcherThreadArr[i].trendyolKahramanRepository = trendyolKahramanRepository;
         tySearcherThreadArr[i].threadSirasi = i;
         tySearcherThreadArr[i].trendyolUrunToplaFonksiyon = trendyolUrunToplaFonksiyon;
         tySearcherThreadArr[i].logService = logService;
         threads[i] = new Thread(tySearcherThreadArr[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         } else if (trendyolConf.getThreadTypeEnum().equals(ThreadTypeEnum.TY_SEARCHER_STAR)) {
         Thread[] threads = new Thread[trendyolConf.getThreadCount()];
         TrendyolYildizThread[] tyYildizThreadArr = new TrendyolYildizThread[trendyolConf.getThreadCount()];
         for (int i = 0; i < trendyolConf.getThreadCount(); i++) {
         tyYildizThreadArr[i] = new TrendyolYildizThread();
         tyYildizThreadArr[i].kategoriId = trendyolConf.getCategory();
         tyYildizThreadArr[i].trendyolKategoriRepository = trendyolKategoriRepository;
         tyYildizThreadArr[i].trendyolUrunToplaFonksiyon = trendyolUrunToplaFonksiyon;
         threads[i] = new Thread(tyYildizThreadArr[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }
         */
        //TY END

        //MEDIAMARKT START
        /**
         List<MmConf> mmConfList = mmConfRepository.findByHostname(hostname);
         for (MmConf mmConf: mmConfList) {
         if (mmConf.getThreadTypeEnum().equals(ThreadTypeEnum.MM_HESAP_ACMA) && mmConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[1];
         MmHesapAcmaThread[] mmHesapAcmaThreadArr = new MmHesapAcmaThread[1];
         mmHesapAcmaThreadArr[0] = new MmHesapAcmaThread();
         mmHesapAcmaThreadArr[0].mmEkranOkumaService = mmEkranOkumaService;
         threads[0] = new Thread(mmHesapAcmaThreadArr[0]);
         threads[0].start();
         } else if (mmConf.getThreadTypeEnum().equals(ThreadTypeEnum.MM_SEARCHER) && mmConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[mmConf.getThreadCount()];
         MmSearcherThread[] mmSearcherThreads = new MmSearcherThread[mmConf.getThreadCount()];
         for (int i = 0; i < mmConf.getThreadCount(); i++) {
         mmSearcherThreads[i] = new MmSearcherThread();
         mmSearcherThreads[i].mmKategoriRepository = mmKategoriRepository;
         mmSearcherThreads[i].threadSirasi = i;
         mmSearcherThreads[i].kategoriId = mmConf.getCategory();
         mmSearcherThreads[i].mmEkranOkumaService = mmEkranOkumaService;
         mmSearcherThreads[i].logService = logService;
         threads[i] = new Thread(mmSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //MEDIAMARKT END

        //MEDIAMARKT START
        /**
         List<MsConf> msConfList = msConfRepository.findByHostname(hostname);
         for (MsConf msConf: msConfList) {
         if (msConf.getThreadTypeEnum().equals(ThreadTypeEnum.MS_SEARCHER) && msConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[msConf.getThreadCount()];
         MsSearcherThread[] msSearcherThreads = new MsSearcherThread[msConf.getThreadCount()];
         for (int i = 0; i < msConf.getThreadCount(); i++) {
         msSearcherThreads[i] = new MsSearcherThread();
         msSearcherThreads[i].msKategoriRepository = msKategoriRepository;
         msSearcherThreads[i].threadSirasi = i;
         msSearcherThreads[i].kategoriId = msConf.getCategory();
         msSearcherThreads[i].msEkranOkumaService = msEkranOkumaService;
         threads[i] = new Thread(msSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //MEDIAMARKT END

        //ITOPYA START
        /**
         List<ItopyaConf> itopyaConfList = itopyaConfRepository.findByHostname(hostname);
         for (ItopyaConf itopyaConf: itopyaConfList) {
         if (itopyaConf.getThreadTypeEnum().equals(ThreadTypeEnum.ITOPYA_SEARCHER) && itopyaConf.getThreadCount() > 0) {
         Thread[] threads = new Thread[itopyaConf.getThreadCount()];
         ItopyaSearcherThread[] itopyaSearcherThreads = new ItopyaSearcherThread[itopyaConf.getThreadCount()];
         for (int i = 0; i < itopyaConf.getThreadCount(); i++) {
         itopyaSearcherThreads[i] = new ItopyaSearcherThread();
         itopyaSearcherThreads[i].itopyaKategoriRepository = itopyaKategoriRepository;
         itopyaSearcherThreads[i].threadSirasi = i;
         itopyaSearcherThreads[i].kategoriId = itopyaConf.getCategory();
         itopyaSearcherThreads[i].itopyaUrunToplaFonksiyon = itopyaUrunToplaFonksiyon;
         itopyaSearcherThreads[i].logService = logService;
         threads[i] = new Thread(itopyaSearcherThreads[i]);
         threads[i].start();
         TimeUnit.SECONDS.sleep(3);
         }
         }
         }*/
        //ITOPYA END
    }

    public void headerListDoldur() {
        List<Header> headerList = headerRepository.findAll();
        for (Header header : headerList) {
            //AmzGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            TknGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            //InsGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            TrendyolGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            A101GenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            MmGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            //MsGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            HbGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            //BimGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            ItopyaGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            TefalGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            VatanGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            DysonGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            YeniAmazonGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            TurkcellGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            GcGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            MdGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
            KrcGenelServiceImpl.putHeaderHashMap(header.getId(), header.getHeader());
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