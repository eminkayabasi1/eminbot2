package com.app.fku.medimarkt.fonksiyon.impl;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.enums.MailSahipEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.medimarkt.entity.MmKategori;
import com.app.fku.medimarkt.entity.MmTelegramConf;
import com.app.fku.medimarkt.entity.MmUyelik;
import com.app.fku.medimarkt.fonksiyon.service.MmEkranOkumaService;
import com.app.fku.medimarkt.fonksiyon.service.MmGenelService;
import com.app.fku.medimarkt.repository.MmTelegramConfRepository;
import com.app.fku.medimarkt.repository.MmUyelikRepository;
import com.app.fku.teknosa.entity.TknKategori;
import com.app.fku.teknosa.entity.TknTelegramConf;
import com.app.fku.teknosa.repository.TknTelegramConfRepository;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MmEkranOkumaServiceImpl implements MmEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    MmGenelService mmGenelService;

    @Autowired
    MmUyelikRepository mmUyelikRepository;

    @Autowired
    MmTelegramConfRepository mmTelegramConfRepository;

    @Autowired
    LogService logService;


    public String chromeDriverPath = "C:\\GoogleChromeDriver\\chromedriver_win32\\chromedriver.exe";
    public Long BEKLEME_SURESI_20SN = 20000L;
    public Long BEKLEME_SURESI_10SN = 10000L;
    public Long BEKLEME_SURESI_6SN = 6000L;
    public Long BEKLEME_SURESI_5SN = 5000L;
    public Long BEKLEME_SURESI_4SN = 4000L;
    public Long BEKLEME_SURESI_3SN = 3000L;
    public Long BEKLEME_SURESI_2SN = 2000L;
    public Long BEKLEME_SURESI_1SN = 1000L;

    @Override
    public void hesapAcma() throws InterruptedException {

        WebDriver driver = null;
        try {
            System.setProperty("webdriver.chrome.driver",chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-fullscreen");
            driver = new ChromeDriver(options);
            JavascriptExecutor js = (JavascriptExecutor)driver;
            //Uye Ol START
            try {
                driver.get("https://www.mediamarkt.com.tr/webapp/wcs/stores/servlet/MultiChannelMARegister");
                Thread.sleep(BEKLEME_SURESI_3SN);

                String ad = genelService.yeniAdUret();
                String soyad = genelService.yeniSoyadUret();
                String mail = ad + "." + soyad + "@akposta.com";

                //js.executeScript("alert('Sealmlar')");
//                js.executeScript("document.getElementById('icon-close-button-1618836031107').click()");

//                List<WebElement> reklamElementList = driver.findElements(By.id("icon-close-button-1618836031107"));
//                if (reklamElementList.size() > 0) {
//                    reklamElementList.get(0).click();
//                }

                WebElement isimElement = driver.findElement(By.id("register-firstname"));
                isimElement.sendKeys("Halil");

                WebElement soyisimElement = driver.findElement(By.id("register-lastname"));
                soyisimElement.sendKeys("Kayabaşı");

                WebElement telefonElement = driver.findElement(By.id("phone2"));
                telefonElement.sendKeys("05075042552");

                WebElement emailElement = driver.findElement(By.id("register-email"));
                emailElement.sendKeys(mail);

                WebElement sifreElement = driver.findElement(By.id("register-password"));
                sifreElement.sendKeys("Aa123456");

                WebElement yenidenSifreElement = driver.findElement(By.id("register-password-confirm"));
                yenidenSifreElement.sendKeys("Aa123456");

                for (int i = 1; i > 0; i++) {
                    boolean durum = false;
                    try {
                        List<WebElement> firsatlarElementList = driver.findElements(By.id("register-newsletter"));
                        if (firsatlarElementList.size() > 0) {
                            //firsatlarElement.submit();
                            firsatlarElementList.get(0).click();
                            durum = true;
                        }
                    } catch (Exception e) {
                        System.out.println("1");
                        WebElement reklamElement = driver.findElement(By.className("responsive"));
                        reklamElement.click();
                    }
                    if (durum) {
                        break;
                    }
                }

                for (int i = 1; i > 0; i++) {
                    boolean durum = false;
                    try {
                        List<WebElement> mmClupElementList = driver.findElements(By.id("register-loyaltyClub"));
                        if (mmClupElementList.size() > 0) {
                            //mmClupElement.submit();
                            mmClupElementList.get(0).click();
                            durum = true;
                        }
                    } catch (Exception e) {
                        System.out.println("2");
                    }
                    if (durum) {
                        break;
                    }
                }

                WebElement mmClupTelElement = driver.findElement(By.id("loyltyMobile"));
                mmClupTelElement.sendKeys("5075042552");

                WebElement mmAdresElement = driver.findElement(By.id("loyaltyStreet"));
                mmAdresElement.sendKeys("Ahimesut Mahallesi 1891.cadde Burç2 Blokları");

                WebElement mmAdresNumElement = driver.findElement(By.id("loyaltyStreetNum"));
                mmAdresNumElement.sendKeys("A/12");

                Select mmSelectSehir = new Select(driver.findElement(By.id("rc-city")));
                mmSelectSehir.selectByIndex(7);
                Thread.sleep(5000);

                Select mmSelectIlce = new Select(driver.findElement(By.id("rc-district")));
                mmSelectIlce.selectByIndex(10);
                Thread.sleep(5000);

                Select mmSelectMahalle = new Select(driver.findElement(By.id("rc-county")));
                mmSelectMahalle.selectByIndex(31);
                Thread.sleep(5000);

                Select mmSelectDogumGun = new Select(driver.findElement(By.name("birthdayDay")));
                mmSelectDogumGun.selectByIndex(1);
                Thread.sleep(5000);

                Select mmSelectDogumAy = new Select(driver.findElement(By.name("birthdayMonth")));
                mmSelectDogumAy.selectByIndex(6);
                Thread.sleep(5000);

                Select mmSelectDogumYil = new Select(driver.findElement(By.name("birthdayYear")));
                mmSelectDogumYil.selectByIndex(14);
                Thread.sleep(5000);

                Select mmSelectFavoriMagaza = new Select(driver.findElement(By.id("register-card-market")));
                mmSelectFavoriMagaza.selectByIndex(14);

                for (int i = 1; i > 0; i++) {
                    List<WebElement> mmClupEpostaElementList = driver.findElements(By.id("notify-agreement"));
                    if (mmClupEpostaElementList.size() > 0) {
                        //mmClupEpostaElement.submit();
                        mmClupEpostaElementList.get(0).click();
                        break;
                    }
                }

                for (int i = 1; i > 0; i++) {
                    List<WebElement> mmClupOnayElementList = driver.findElements(By.id("notify-marketing"));
                    if (mmClupOnayElementList.size() > 0) {
                        //mmClupOnayElement.submit();
                        mmClupOnayElementList.get(0).click();
                        break;
                    }
                }

                for (int i = 1; i > 0; i++) {
                    List<WebElement> gizlilikElementList = driver.findElements(By.id("policy-acceptance"));
                    if (gizlilikElementList.size() > 0) {
                        //gizlilikElement.submit();
                        gizlilikElementList.get(0).click();
                        break;
                    }
                }

                List<WebElement> kayitButtonElementList = driver.findElements(By.id("my-account-register-submit"));
                for (int i = 1; i > 0; i++) {
                    if (kayitButtonElementList.size() > 0) {
                        kayitButtonElementList.get(0).click();
                        break;
                    }
                }

                Thread.sleep(BEKLEME_SURESI_2SN);
                driver.close();

                MmUyelik uyelik = new MmUyelik();
                uyelik.setEposta(mail);
                uyelik.setKayitTarihi(new Date());
                uyelik.setMailSahipEnum(MailSahipEnum.SULEYMAN);
                uyelik.setSifre("Aa123456");
                uyelik.setClubMu(EvetHayirEnum.EVET);
                mmUyelikRepository.save(uyelik);
                mmUyelikRepository.flush();
            } catch (Exception e) {
                System.out.println("Üye Ol Ekranında Hata Aldım.");
                driver.close();
                throw new Exception();
            }
            //Uye Ol END
        } catch (Exception e) {
            hesapAcma();
        }
    }

    @Override
    public HashMap<String, GenelUrunModel> kategoriIleUrunBul(MmKategori mmKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        if (yeniUrunHashMap == null) {
            yeniUrunHashMap = new HashMap<>();
        }

        try {
            Document mmDoc = null;

            for (int i = 1; i > 0; i++) {

                try {
                    if (mmKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                        mmDoc = mmGenelService.urleGit(mmKategori.getSayfaAdresi() + "&page=" + pageNumber);
                    } else {
                        mmDoc = mmGenelService.urleGit(mmKategori.getSayfaAdresi() + "?page=" + pageNumber);
                    }

                    if (mmDoc == null) {
                        logService.mmLogYaz("MediaMarkt Hata aAA 1");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    logService.mmLogYaz("MediaMarkt Hata aAA 2");
                }
                Thread.sleep(10000L);
            }


            if (maxPageNumber == 1) {
                String pageStr = "";
                try {
                    pageStr = mmDoc.getElementById("category").getElementsByClass("cf").get(0).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString();
                } catch (Exception e) {
                    pageStr = mmDoc.getElementById("category").getElementsByClass("cf").get(1).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString();
                }

                pageStr = pageStr.split("\\(")[1].split("\\)")[0];
                int urunSayisi = Integer.valueOf(pageStr);
                if (urunSayisi % 36 == 0) {
                    maxPageNumber = (urunSayisi / 36);
                } else {
                    maxPageNumber = (urunSayisi / 36) + 1;
                }

            }

            Elements productElements = mmDoc.getElementsByClass("product-wrapper");
            /**Elements productElements2 = mmDoc2.getElementsByClass("product-wrapper");
            Elements productElements3 = mmDoc3.getElementsByClass("product-wrapper");
            for (Element element: productElements2) {
                productElements.add(element);
            }
            for (Element element: productElements3) {
                productElements.add(element);
            }*/
            for (Element element: productElements) {
                Attributes productAttr = element.attributes();
                String mmNo = productAttr.get("data-modelnumber");

                GenelUrunModel genelUrunModel = urunHashMap.get(mmNo);
                String model = "";
                try {
                    model = element.getElementsByClass("content").get(0).childNodes().get(3).childNodes().get(1).childNodes().get(0).toString();
                } catch (Exception e) {
                    model = element.getElementsByClass("content").get(0).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString();
                }

                if (model.contains("Outlet")) {
                    continue;
                }

                Elements fiyatElements = element.getElementsByClass("product-price alt");
                if (fiyatElements == null ||fiyatElements.size() == 0) {
                    continue;
                }
                String fiyatStr = element.getElementsByClass("product-price alt").get(0).childNodes().get(1).childNodes().get(1).childNodes().get(1).childNodes().get(0).toString().trim().split(",")[0];
                Double yeniFiyat = Double.valueOf(fiyatStr);

                if (genelUrunModel == null) {
                    //yeni gelmiş ürün
                    GenelUrunModel yeniUrunModel = new GenelUrunModel();
                    yeniUrunModel.setModel(model);
                    yeniUrunModel.setFiyat(yeniFiyat);
                    yeniUrunModel.setMinFiyat(yeniFiyat);
                    yeniUrunModel.setMinFiyatTarihi(new Date());
                    yeniUrunModel.setSonMesajTarihi(new Date());
                    yeniUrunHashMap.put(mmNo, yeniUrunModel);

                    if (!ilkMi) {
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                //mmKategori.getKategoriAdi() + "%0A" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + model + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + "https://www.mediamarkt.com.tr/tr/product/a-" + mmNo + ".html %0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, mmKategori, model, 0L, yeniFiyat.longValue());
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

                            if (indYuzde > 5d) {
                                String mesaj = "" +
                                        mmKategori.getKategoriAdi() + "%0A" +
                                        "Ürün Adı: " + model + "%0A" +
                                        "Eski Fiyat: " + nf.format(genelUrunModel.getFiyat()) + "%0A" +
                                        "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                        "Min Fiyat: " + nf.format(genelUrunModel.getMinFiyat()) + "%0A" +
                                        "Min Fiyat Tarihi: " + sdf.format(genelUrunModel.getMinFiyatTarihi()) + "%0A" +
                                        "İndirim: " + nf.format(indirim) + "%0A" +
                                        "İndirim Yüzde: " + nf.format(indYuzde) + "%0A" +
                                        "Ürün Link:" + "https://www.mediamarkt.com.tr/tr/product/a-" + mmNo + ".html %0A" +
                                        "Tarih: " + sdf.format(new Date()) + "%0A" +
                                        "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                        "***Generated By Emin KAYABASI***";

                                telegramMesajGonder(mesaj, mmKategori, model, indYuzde.longValue(), yeniFiyat.longValue());
                                genelUrunModel.setSonMesajTarihi(new Date());
                            }
                        }
                    }

                    genelUrunModel.setFiyat(yeniFiyat);
                    if (yeniFiyat < genelUrunModel.getMinFiyat()) {
                        genelUrunModel.setMinFiyat(yeniFiyat);
                        genelUrunModel.setMinFiyatTarihi(new Date());
                    }

                    //urunHashMap.remove(mmNo);
                    yeniUrunHashMap.put(mmNo, genelUrunModel);
                }
            }

            if (pageNumber < maxPageNumber) {
                yeniUrunHashMap = kategoriIleUrunBul(mmKategori, 0, pageNumber + 1, maxPageNumber, urunHashMap, yeniUrunHashMap, ilkMi);
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            Thread.sleep(3000L);
            logService.mmLogYaz("MediaMarkt Hata");
            if (hataCount < 10) {
                kategoriIleUrunBul(mmKategori, hataCount++, pageNumber + 1, 1, urunHashMap, yeniUrunHashMap, ilkMi);
            }
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, MmKategori mmKategori, String urunId, Long indYuzde, Long fiyat) throws IOException, InterruptedException {
        List<MmTelegramConf> mmTelegramConfList = mmTelegramConfRepository.findByMmKategori(mmKategori);

        for (MmTelegramConf mmTelegramConf: mmTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, mmTelegramConf.getTelegramId(), urunId, "5790074374:AAGvZHBJr7HL06r_jqOmPgTK8sNsYKLv0-w");
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
}