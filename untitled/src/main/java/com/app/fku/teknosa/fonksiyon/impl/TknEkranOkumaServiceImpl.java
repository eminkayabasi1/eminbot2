package com.app.fku.teknosa.fonksiyon.impl;

import com.app.fku.a101.entity.A101Kategori;
import com.app.fku.a101.entity.A101TelegramConf;
import com.app.fku.amazon.entity.AmzFiyat;
import com.app.fku.amazon.entity.AmzKategori;
import com.app.fku.amazon.entity.AmzUrun;
import com.app.fku.amazon.entity.AmzWorkerIstatistik;
import com.app.fku.amazon.fonksiyon.service.AmzEkranOkumaService;
import com.app.fku.amazon.fonksiyon.service.AmzGenelService;
import com.app.fku.amazon.repository.AmzFiyatRepository;
import com.app.fku.amazon.repository.AmzUrunRepository;
import com.app.fku.amazon.repository.AmzWorkerIstatistikRepository;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.teknosa.entity.*;
import com.app.fku.teknosa.fonksiyon.service.TknEkranOkumaService;
import com.app.fku.teknosa.fonksiyon.service.TknGenelService;
import com.app.fku.teknosa.repository.TknFiyatRepository;
import com.app.fku.teknosa.repository.TknTelegramConfRepository;
import com.app.fku.teknosa.repository.TknUrunRepository;
import com.app.fku.teknosa.repository.TknWorkerIstatistikRepository;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TknEkranOkumaServiceImpl implements TknEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    TknUrunRepository tknUrunRepository;

    @Autowired
    TknWorkerIstatistikRepository tknWorkerIstatistikRepository;

    @Autowired
    TknFiyatRepository tknFiyatRepository;

    @Autowired
    TknGenelService tknGenelService;

    @Autowired
    LogService logService;

    @Autowired
    TknTelegramConfRepository tknTelegramConfRepository;

    public int sayac = 0;

    @Override
    public HashMap<String, GenelUrunModel> kategoriIleUrunBul(TknKategori tknKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi, HashMap<String, String> havaleHashMap) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        if (yeniUrunHashMap == null) {
            yeniUrunHashMap = new HashMap<>();
        }

        try {
            int page = pageNumber - 1;

            Document tknDoc = null;
            for (int i = 1; i > 0; i++) {
                if (tknKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    tknDoc = tknGenelService.urleGit(tknKategori.getSayfaAdresi() + "&page=" + page);
                } else {
                    tknDoc = tknGenelService.urleGit(tknKategori.getSayfaAdresi() + "?page=" + page);
                }

                if (tknDoc == null) {
                    logService.teknosaLogYaz("Teknosa Hata aAA");
                    Thread.sleep(4000);
                } else {
                    break;
                }
            }

            int urunSayisi = Integer.valueOf(tknDoc.getElementsByClass("plp-info").get(0).childNodes().get(0).toString().split("Sonuç")[0].trim());
            if (urunSayisi % 20 == 0) {
                maxPageNumber = (urunSayisi / 20);
            } else {
                maxPageNumber = (urunSayisi / 20) + 1;
            }

            Elements productElements = tknDoc.getElementsByClass("prd");
            for (Element element: productElements) {
                Attributes productAttr = element.attributes();
                String tknNo = productAttr.get("data-product-id");
                String model = productAttr.get("data-product-name");

                GenelUrunModel genelUrunModel = urunHashMap.get(tknNo);
                 String fiyatStr = element.getElementsByClass("prd-prc2").get(0).childNodes().get(1).childNodes().get(0).toString().trim().split(" ")[0].replace(".", "").split(",")[0];
                 Double yeniFiyat = Double.valueOf(fiyatStr);

                 if (yeniFiyat < 501) {
                     continue;
                 }

                 if (genelUrunModel == null) {
                    //yeni gelmiş ürün
                     GenelUrunModel yeniUrunModel = new GenelUrunModel();
                     yeniUrunModel.setModel(model);
                     yeniUrunModel.setFiyat(yeniFiyat);
                     yeniUrunModel.setMinFiyat(yeniFiyat);
                     yeniUrunModel.setMinFiyatTarihi(new Date());
                     yeniUrunModel.setSonMesajTarihi(new Date());
                     yeniUrunHashMap.put(tknNo, yeniUrunModel);

                    if (!ilkMi) {
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                tknKategori.getKategoriAdi() + "%0A" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + model + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + "https://www.teknosa.com/a-p-" + tknNo + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, tknKategori, model, 0L, yeniFiyat.longValue());

                        havaleYap(tknNo, havaleHashMap);
                    }
                } else {
                    //zaten var olan ürün
                    if (yeniFiyat < genelUrunModel.getFiyat()) {
                        //mesaj at
                        long diff = new Date().getTime() - genelUrunModel.getSonMesajTarihi().getTime();
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                        if (minutes > 10) {
                            String akakceLink = model.replace(" ", "%2B");
                            akakceLink = akakceLink.replace("+", "");

                            Double indirim = genelUrunModel.getFiyat() - yeniFiyat;
                            Double indYuzde = 100 * indirim / genelUrunModel.getFiyat();
                            String mesaj = "" +
                                    tknKategori.getKategoriAdi() + "%0A" +
                                    "Ürün Adı: " + model + "%0A" +
                                    "Eski Fiyat: " + nf.format(genelUrunModel.getFiyat()) + "%0A" +
                                    "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                    "Min Fiyat: " + nf.format(genelUrunModel.getMinFiyat()) + "%0A" +
                                    "Min Fiyat Tarihi: " + sdf.format(genelUrunModel.getMinFiyatTarihi()) + "%0A" +
                                    "İndirim Yüzde: " + nf.format(indYuzde) + "%0A" +
                                    "İndirim: " + nf.format(indirim) + "%0A" +
                                    "Ürün Link:" + "https://www.teknosa.com/a-p-" + tknNo + "%0A" +
                                    "Tarih: " + sdf.format(new Date()) + "%0A" +
                                    "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                    "***Generated By Emin KAYABASI***";

                            telegramMesajGonder(mesaj, tknKategori, model, indYuzde.longValue(), yeniFiyat.longValue());
                            genelUrunModel.setSonMesajTarihi(new Date());

                            havaleYap(tknNo, havaleHashMap);
                        }

                    }
                     genelUrunModel.setFiyat(yeniFiyat);
                     if (yeniFiyat < genelUrunModel.getMinFiyat()) {
                         genelUrunModel.setMinFiyat(yeniFiyat);
                         genelUrunModel.setMinFiyatTarihi(new Date());
                     }

                     yeniUrunHashMap.put(tknNo, genelUrunModel);
                }
            }

            if (pageNumber < maxPageNumber) {
                yeniUrunHashMap = kategoriIleUrunBul(tknKategori, 0, pageNumber + 1, maxPageNumber, urunHashMap, yeniUrunHashMap, ilkMi, havaleHashMap);
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            logService.teknosaLogYaz("Teknosa Hata");
            Thread.sleep(3000L);
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, TknKategori tknKategori, String urunId, Long indYuzde, Long fiyat) throws IOException, InterruptedException {
        List<TknTelegramConf> tknTelegramConfList = tknTelegramConfRepository.findByTknKategori(tknKategori);

        for (TknTelegramConf tknTelegramConf: tknTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, tknTelegramConf.getTelegramId(), urunId, "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM");
        }

        if (indYuzde > 40) {
            genelService.telegramBombaGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("DYSON")) {
            genelService.telegramDysonGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("AIRFRYER") ||
                urunId.toUpperCase().contains("AİRFRYER") ||
                urunId.toUpperCase().contains("AIR FRYER") ||
                urunId.toUpperCase().contains("AİR FRYER") ||
                urunId.toUpperCase().contains("FRITOZ") ||
                urunId.toUpperCase().contains("FRİTOZ") ||
                urunId.toUpperCase().contains("FRİTÖZ") ||
                urunId.toUpperCase().contains("FRITÖZ")) {
            genelService.telegramFritozGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("FISSLER") ||
                urunId.toUpperCase().contains("FİSSLER")) {
            genelService.telegramFisslerGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("PLAYSTATION") ||
                urunId.toUpperCase().contains("PLAYSTATİON") ||
                urunId.toUpperCase().contains("PLAY STATION") ||
                urunId.toUpperCase().contains("PLAY STATİON")) {
            genelService.telegramPlayStationGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("IPHONE 14") ||
                urunId.toUpperCase().contains("İPHONE 14") ||
                urunId.toUpperCase().contains("IPHONE14") ||
                urunId.toUpperCase().contains("İPHONE14")) {
            genelService.telegramIphone14Gonder(mesaj, urunId);
        }

        if (fiyat <= 50) {
            genelService.telegramFiyatHatasiGonder(mesaj, urunId);
        }
    }

    @Override
    public HashMap<String, GenelUrunModel> sayfadanOku(TknUrun tknUrun, HashMap<String, GenelUrunModel> hashMap, boolean ilkMi) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        try {
            Document tknDoc = tknGenelService.urleGit("https://www.teknosa.com/a-p-" + tknUrun.getTknNo() + "?shopId=teknosa");
            if (tknDoc == null) {
                logService.teknosaLogYaz("Teknosa Hata aAA");
                throw new Exception();
            }

            GenelUrunModel genelUrunModel = hashMap.get(tknUrun.getTknNo());

            Double fiyat = null;
            try {
                String fiyatStr = tknDoc.getElementsByClass("pdp-buy").get(0).getElementsByClass("pdp-prc2").get(0).childNodes().get(1).childNodes().get(0).toString();
                fiyat = new Double(fiyatStr.split(" ")[0].replace(".", ""));
            } catch (Exception e) {

            }

            if (fiyat == null) {
                hashMap.remove(tknUrun.getTknNo());
                return hashMap;
            }

            boolean sepetVarMi = false;
            String sepetMesaj = "";
            Element kampanyaElement = tknDoc.getElementById("pdp-promos");
            if (kampanyaElement != null) {
                List<Node> nodeList = kampanyaElement.childNodes().get(3).childNodes().get(3).childNodes();
                for (Node node: nodeList) {
                    try {
                        String kmpStr = node.childNodes().get(1).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString();
                        if (kmpStr.contains("Sepette")) {
                            int indirimOrani = Integer.valueOf(kmpStr.split("Sepette %")[1].split(" ")[0]);
                            fiyat = fiyat * (100 - indirimOrani) / 100;
                            sepetVarMi = true;
                            sepetMesaj = kmpStr.trim().replace("%", "");
                            break;
                        }
                    } catch (Exception e) {

                    }
                }
            }

            if (genelUrunModel == null) {
                //yeni ürün
                genelUrunModel = new GenelUrunModel();
                genelUrunModel.setModel(tknUrun.getModel());
                genelUrunModel.setFiyat(fiyat);
                genelUrunModel.setMinFiyat(fiyat);
                genelUrunModel.setMinFiyatTarihi(new Date());
                genelUrunModel.setSonMesajTarihi(new Date());

                if (!ilkMi) {
                    //yeni stok
                    String akakceLink = genelUrunModel.getModel().replace(" ", "%2B");
                    akakceLink = akakceLink.replace("+", "");

                    String mesaj = "" +
                            //tknUrun.getTknKategori().getKategoriAdi() + "%0A" +
                            "YENİ ÜRÜN%0A" +
                            "Ürün Adı: " + genelUrunModel.getModel() + "%0A" +
                            "Fiyat: " + nf.format(genelUrunModel.getFiyat()) + "%0A" +
                            "Ürün Link:" + "https://www.teknosa.com/a-p-" + tknUrun.getTknNo() + "?shopId=teknosa" + " %0A " +
                            "Tarih: " + sdf.format(new Date()) + "%0A" +
                            "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                            "***Generated By Emin KAYABASI***";

                    telegramMesajGonder(mesaj, tknUrun.getTknKategori(), tknUrun.getTknNo(), 0L, genelUrunModel.getFiyat().longValue());
                }

                hashMap.put(tknUrun.getTknNo(), genelUrunModel);
            } else {
                if (fiyat < genelUrunModel.getFiyat()) {
                    //indirim - mesaj at
                    String akakceLink = tknUrun.getModel().replace(" ", "%2B");
                    akakceLink = akakceLink.replace("+", "");

                    Double indirim = genelUrunModel.getFiyat() - fiyat;
                    String mesaj = "" +
                            //tknUrun.getTknKategori().getKategoriAdi() + "%0A" +
                            "Ürün Adı: " + genelUrunModel.getModel() + "%0A" +
                            "Sepet Msj: " + sepetMesaj + "%0A" +
                            "Eski Fiyat: " + nf.format(genelUrunModel.getFiyat()) + "%0A" +
                            "Yeni Fiyat: " + nf.format(fiyat) + "%0A" +
                            "Min Fiyat: " + nf.format(genelUrunModel.getMinFiyat()) + "%0A" +
                            "Min Fiyat Tarihi: " + sdf.format(genelUrunModel.getMinFiyatTarihi()) + "%0A" +
                            "İndirim: " + nf.format(indirim) + "%0A" +
                            "Ürün Link:" + "https://www.teknosa.com/a-p-" + tknUrun.getTknNo() + "?shopId=teknosa" + " %0A" +
                            "Tarih: " + sdf.format(new Date()) + "%0A" +
                            "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                            "***Generated By Emin KAYABASI***";

                    telegramMesajGonder(mesaj, tknUrun.getTknKategori(), tknUrun.getTknNo(), 0L, fiyat.longValue());

                    genelUrunModel.setSonMesajTarihi(new Date());
                }
                genelUrunModel.setFiyat(fiyat);
                if (fiyat < genelUrunModel.getMinFiyat()) {
                    genelUrunModel.setMinFiyat(fiyat);
                    genelUrunModel.setMinFiyatTarihi(new Date());
                }
                hashMap.remove(tknUrun.getTknNo());
                hashMap.put(tknUrun.getTknNo(), genelUrunModel);
            }

        } catch (Exception e) {
            hashMap.remove(tknUrun.getTknNo());
        }

        return hashMap;
    }

    public void havaleYap(String tknNo, HashMap<String, String> havaleHashMap) {
        String urun = havaleHashMap.get(tknNo);
        if (urun != null && !urun.equals("")) {
            new Thread(() -> {
                FirefoxDriver driver = null;
                try {
                    String TEKNOSA_KULLANICI_ADI = "cansu.teknosa.1@atlasposta.com";
                    String TEKNOSA_SIFRE = "Aa123456";

                    String mesaj = "Teknosa havale başladı: " + tknNo;
                    genelService.telegramMesajGonder(mesaj, "-886102747", tknNo, "5828325192:AAHjrFwYllHRW-gbYLOwAKvcV_8IxsJ1iP0");

                    String chromeDriverPath = "C:\\ChromeDriver\\firefox2\\geckodriver.exe";
                    String firefoxPath = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";

                    System.setProperty("webdriver.gecko.driver", chromeDriverPath);
                    FirefoxOptions options = new FirefoxOptions();
                    options.setBinary(firefoxPath);
                    driver = new FirefoxDriver(options);
                    driver.manage().deleteAllCookies();

                    driver.get("https://www.teknosa.com/login");
                    Thread.sleep(3000L);
                    WebElement userNameElement = driver.findElement(By.id("j_username1"));
                    userNameElement.sendKeys(TEKNOSA_KULLANICI_ADI);

                    WebElement devamButtonElement = driver.findElement(By.id("newLoginStepSecond"));
                    devamButtonElement.click();
                    Thread.sleep(2000L);

                    WebElement passwordElement = driver.findElement(By.id("j_password"));
                    passwordElement.sendKeys(TEKNOSA_SIFRE);

                    WebElement girisButtonElement = driver.findElement(By.id("customerLoginButton"));
                    girisButtonElement.click();
                    Thread.sleep(2000L);

                    for (int i = 0 ; i < 10; i++) {
                        driver.get("https://www.teknosa.com/merhaba-p-" + tknNo + "?shopId=teknosa");
                        Thread.sleep(3000L);

                        List<WebElement> popUpCloseElementList = driver.findElements(By.className("n-popup__close"));
                        if (popUpCloseElementList != null && popUpCloseElementList.size() > 0) {
                            popUpCloseElementList.get(0).click();
                        }

                        if (driver.findElement(By.className("pdp-seller-info")).getText().contains("TEKNOSA")) {
                            WebElement sepeteEkleButtonElement = driver.findElement(By.id("addToCartButton"));
                            sepeteEkleButtonElement.click();
                            for (int j = 1; j > 0; j++) {
                                try {
                                    WebElement sepetElement = driver.findElement(By.id("mnp-added-to-cart"));
                                    if (sepetElement != null) {
                                        Thread.sleep(1000L);
                                        break;
                                    }
                                } catch (Exception e) {

                                }
                            }

                            driver.get("https://www.teknosa.com/sepet");
                            Thread.sleep(3000L);

                            driver.findElements(By.className("cart-sum-cta")).get(0).click();
                            Thread.sleep(3000L);

                            driver.findElement(By.id("asf_diff_address")).click();
                            driver.findElement(By.id("cart_sum_confirm")).click();
                            Thread.sleep(1000L);

                            driver.findElement(By.id("paymentButtonSearch")).click();
                            Thread.sleep(1000L);

                            for (int j = 1; j > 0; j++) {
                                try {
                                    driver.findElements(By.className("pop-transfer")).get(0).click();
                                    break;
                                } catch (Exception e) {

                                }
                            }
                            Thread.sleep(1000L);

                            driver.findElement(By.id("transfer_select")).click();
                            Thread.sleep(1000L);

                            driver.findElement(By.id("paymentButtonSearch")).click();
                            Thread.sleep(5000L);

                            mesaj = "Ürün Havale İle Alındı: " + TEKNOSA_KULLANICI_ADI;
                            genelService.telegramMesajGonder(mesaj, "-886102747", tknNo, "5828325192:AAHjrFwYllHRW-gbYLOwAKvcV_8IxsJ1iP0");
                            driver.close();
                            break;
                        } else {
                        }
                    }
                    driver.close();
                } catch (Exception e) {
                    driver.close();
                }
            }).start();
        }
    }
}