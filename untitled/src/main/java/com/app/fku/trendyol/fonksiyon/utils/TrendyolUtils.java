package com.app.fku.trendyol.fonksiyon.utils;

import com.app.fku.genel.enums.MailSahipEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.repository.MailAdRepository;
import com.app.fku.genel.repository.MailSoyadRepository;
import com.app.fku.trendyol.entity.TrendyolKupon;
import com.app.fku.trendyol.entity.TrendyolUyelik;
import com.app.fku.trendyol.entity.TrendyolUyelikEski;
import com.app.fku.trendyol.repository.TrendyolKuponRepository;
import com.app.fku.trendyol.repository.TrendyolUyelikEskiRepository;
import com.app.fku.trendyol.repository.TrendyolUyelikRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TrendyolUtils implements  TrendyolUtilsInterface {

    @Autowired
    TrendyolKuponRepository trendyolKuponRepository;

    @Autowired
    TrendyolUyelikRepository trendyolUyelikRepository;

    @Autowired
    TrendyolUyelikEskiRepository trendyolUyelikEskiRepository;

    @Autowired
    GenelService genelService;

    public String chromeDriverPath = "C:\\GoogleChromeDriver\\chromedriver_win32\\chromedriver.exe";

    public List<TrendyolKupon> kuponKontrolEtme(TrendyolUyelik trendyolUyelik) throws InterruptedException {
        WebDriver driver = null;
        System.setProperty("webdriver.chrome.driver",chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("https://www.trendyol.com/giris");
        Thread.sleep(3000L);

//        for (int i = 1; i > 0; i++) {
//            try {
//                WebElement popupClose = driver.findElement(By.className("fancybox-item"));
//                popupClose.click();
//            } catch (Exception e) {
//                Thread.sleep(1000L);
//            }
//        }

        for (int i = 1; i > 0; i++) {
            try {
                WebElement emailTxt = driver.findElement(By.id("login-email"));
                emailTxt.sendKeys(trendyolUyelik.getEposta());

                WebElement sifreTxt = driver.findElement(By.id("login-password-input"));
                sifreTxt.sendKeys(trendyolUyelik.getSifre());

                List<WebElement> loginButtonList = driver.findElements(By.className("submit"));
                loginButtonList.get(0).click();
                break;
            } catch (Exception e) {
                System.out.println("Hata 2");
                Thread.sleep(3000L);
            }
        }
        Thread.sleep(3000L);
        driver.get("https://www.trendyol.com/Hesabim/IndirimKuponlari");
        Thread.sleep(3000L);

        for (int i = 1; i > 0; i++) {
            try {
                List<WebElement> kuponBoxContainerList = driver.findElements(By.className("couponBoxContainer"));
                if (kuponBoxContainerList == null || kuponBoxContainerList.size() < 1) {
                    //kupon yok
                    break;
                } else {
                    //kupon var
                    for (WebElement couponBoxContainer: kuponBoxContainerList) {
                        WebElement titleElement = couponBoxContainer.findElement(By.className("title"));
                        String kuponAdi = titleElement.getText();

                        WebElement couponBox = couponBoxContainer.findElement(By.className("coupon-box"));
                        String couponBoxStr = couponBox.getText();
                        String[] couponBoxStrArr = couponBoxStr.split("\n");
                        String baslangicTarihiStr = "";
                        String bitisTarihiStr = "";
                        String minTutarStr = "";
                        if (couponBoxStrArr.length == 7) {
                            baslangicTarihiStr = couponBoxStrArr[2];
                            bitisTarihiStr = couponBoxStrArr[4];
                            minTutarStr = couponBoxStrArr[6];
                        } else if (couponBoxStrArr.length == 6) {
                            baslangicTarihiStr = couponBoxStrArr[1];
                            bitisTarihiStr = couponBoxStrArr[3];
                            minTutarStr = couponBoxStrArr[5];
                        }

                        Date baslangicTarihiDate = null;
                        if (baslangicTarihiStr != null && !baslangicTarihiStr.equals("")) {
                            baslangicTarihiDate = new SimpleDateFormat("dd-MM-yyyy").parse(baslangicTarihiStr);
                        }

                        Date bitisTarihiDate = null;
                        if (bitisTarihiStr != null && !bitisTarihiStr.equals("")) {
                            bitisTarihiDate = new SimpleDateFormat("dd-MM-yyyy").parse(bitisTarihiStr);
                        }

                        Double minTutarDouble = 0D;
                        if (minTutarStr != null && !minTutarStr.equals("")) {
                            String[] minTutarStrArr = minTutarStr.split(" ");
                            minTutarDouble = Double.valueOf(minTutarStrArr[0]);
                        }

                        WebElement discountAmountBox = couponBoxContainer.findElement(By.className("discount-amount-box"));
                        String indirimTutarStr = discountAmountBox.getText();
                        String[] indirimStrArr = indirimTutarStr.split("\n");
                        String[] indirimStrArr2 = indirimStrArr[0].split(" ");
                        Double indirimTutarDouble = Double.valueOf(indirimStrArr2[0]);

                        TrendyolKupon trendyolKupon = new TrendyolKupon();
                        trendyolKupon.setEposta(trendyolUyelik.getEposta());
                        trendyolKupon.setSifre(trendyolUyelik.getSifre());
                        trendyolKupon.setKuponAdi(kuponAdi);
                        trendyolKupon.setBaslangicTarihi(baslangicTarihiDate);
                        trendyolKupon.setBitisTarihi(bitisTarihiDate);
                        trendyolKupon.setMinTutar(minTutarDouble);
                        trendyolKupon.setIndirim(indirimTutarDouble);
                        trendyolKupon.setKayitTarihi(new Date());

                        trendyolKuponRepository.save(trendyolKupon);
                        trendyolKuponRepository.flush();
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Hata 3");
                Thread.sleep(3000L);
            }
        }

        trendyolUyelik.setSonKontrolTarihi(new Date());
        trendyolUyelikRepository.save(trendyolUyelik);
        trendyolUyelikRepository.flush();
        driver.close();
        return null;
    }

    public List<TrendyolKupon> kuponKontrolEtme(TrendyolUyelikEski trendyolUyelikEski) throws InterruptedException {
        WebDriver driver = null;
        System.setProperty("webdriver.chrome.driver",chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("https://www.trendyol.com/giris");
        Thread.sleep(3000L);

//        for (int i = 1; i > 0; i++) {
//            try {
//                WebElement popupClose = driver.findElement(By.className("fancybox-item"));
//                popupClose.click();
//            } catch (Exception e) {
//                Thread.sleep(1000L);
//            }
//        }

        for (int i = 1; i > 0; i++) {
            try {
                WebElement emailTxt = driver.findElement(By.id("login-email"));
                emailTxt.sendKeys(trendyolUyelikEski.getEposta());

                WebElement sifreTxt = driver.findElement(By.id("login-password-input"));
                sifreTxt.sendKeys(trendyolUyelikEski.getSifre());

                List<WebElement> loginButtonList = driver.findElements(By.className("submit"));
                loginButtonList.get(0).click();
                break;
            } catch (Exception e) {
                System.out.println("Hata 2");
                Thread.sleep(3000L);
            }
        }
        Thread.sleep(3000L);
        driver.get("https://www.trendyol.com/Hesabim/IndirimKuponlari");
        Thread.sleep(3000L);

        for (int i = 1; i > 0; i++) {
            try {
                List<WebElement> kuponBoxContainerList = driver.findElements(By.className("couponBoxContainer"));
                if (kuponBoxContainerList == null || kuponBoxContainerList.size() < 1) {
                    //kupon yok
                    break;
                } else {
                    //kupon var
                    for (WebElement couponBoxContainer: kuponBoxContainerList) {
                        WebElement titleElement = couponBoxContainer.findElement(By.className("title"));
                        String kuponAdi = titleElement.getText();

                        WebElement couponBox = couponBoxContainer.findElement(By.className("coupon-box"));
                        String couponBoxStr = couponBox.getText();
                        String[] couponBoxStrArr = couponBoxStr.split("\n");
                        String baslangicTarihiStr = "";
                        String bitisTarihiStr = "";
                        String minTutarStr = "";
                        if (couponBoxStrArr.length == 7) {
                            baslangicTarihiStr = couponBoxStrArr[2];
                            bitisTarihiStr = couponBoxStrArr[4];
                            minTutarStr = couponBoxStrArr[6];
                        } else if (couponBoxStrArr.length == 6) {
                            baslangicTarihiStr = couponBoxStrArr[1];
                            bitisTarihiStr = couponBoxStrArr[3];
                            minTutarStr = couponBoxStrArr[5];
                        }

                        Date baslangicTarihiDate = null;
                        if (baslangicTarihiStr != null && !baslangicTarihiStr.equals("")) {
                            baslangicTarihiDate = new SimpleDateFormat("dd-MM-yyyy").parse(baslangicTarihiStr);
                        }

                        Date bitisTarihiDate = null;
                        if (bitisTarihiStr != null && !bitisTarihiStr.equals("")) {
                            bitisTarihiDate = new SimpleDateFormat("dd-MM-yyyy").parse(bitisTarihiStr);
                        }

                        Double minTutarDouble = 0D;
                        if (minTutarStr != null && !minTutarStr.equals("")) {
                            String[] minTutarStrArr = minTutarStr.split(" ");
                            minTutarDouble = Double.valueOf(minTutarStrArr[0]);
                        }

                        WebElement discountAmountBox = couponBoxContainer.findElement(By.className("discount-amount-box"));
                        String indirimTutarStr = discountAmountBox.getText();
                        String[] indirimStrArr = indirimTutarStr.split("\n");
                        String[] indirimStrArr2 = indirimStrArr[0].split(" ");
                        Double indirimTutarDouble = Double.valueOf(indirimStrArr2[0]);

                        TrendyolKupon trendyolKupon = new TrendyolKupon();
                        trendyolKupon.setEposta(trendyolUyelikEski.getEposta());
                        trendyolKupon.setSifre(trendyolUyelikEski.getSifre());
                        trendyolKupon.setKuponAdi(kuponAdi);
                        trendyolKupon.setBaslangicTarihi(baslangicTarihiDate);
                        trendyolKupon.setBitisTarihi(bitisTarihiDate);
                        trendyolKupon.setMinTutar(minTutarDouble);
                        trendyolKupon.setIndirim(indirimTutarDouble);
                        trendyolKupon.setKayitTarihi(new Date());

                        trendyolKuponRepository.save(trendyolKupon);
                        trendyolKuponRepository.flush();
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Hata 3");
                Thread.sleep(3000L);
            }
        }

        trendyolUyelikEski.setSonKontrolTarihi(new Date());
        trendyolUyelikEskiRepository.save(trendyolUyelikEski);
        trendyolUyelikEskiRepository.flush();
        driver.close();
        return null;
    }

    public void hesapAcma() throws InterruptedException {
        WebDriver driver = null;
        System.setProperty("webdriver.chrome.driver",chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("https://www.trendyol.com/uyelik?cb=https%3A%2F%2Fwww.trendyol.com%2F");
        Thread.sleep(3000L);

        String mailAdi = genelService.yeniMailAdiUret();
        mailAdi = mailAdi + "@ilkmail.com";

        WebElement emailElement = driver.findElement(By.id("register-email"));
        emailElement.sendKeys(mailAdi);

        WebElement pswElement = driver.findElement(By.id("register-password-input"));
        pswElement.sendKeys("179237kt");

        WebElement cinsiyetElement = driver.findElement(By.className("male"));
        cinsiyetElement.click();

        WebElement checkElement = driver.findElement(By.className("q-checkbox-wrapper"));
        checkElement.click();

        List<WebElement> kaydetButtonElementList = driver.findElements(By.className("submit"));
        kaydetButtonElementList.get(0).click();
        Thread.sleep(3000L);

        TrendyolUyelik trendyolUyelik = new TrendyolUyelik();
        trendyolUyelik.setEposta(mailAdi);
        trendyolUyelik.setSifre("179237kt");
        trendyolUyelik.setKayitTarihi(new Date());
        trendyolUyelik.setSonKontrolTarihi(new Date());
        trendyolUyelik.setMailSahipEnum(MailSahipEnum.KERIM);
        trendyolUyelikRepository.save(trendyolUyelik);
        trendyolUyelikRepository.flush();

        driver.close();
    }

    @Override
    public boolean uyeligeSepetEkle(TrendyolUyelik trendyolUyelik) throws InterruptedException {

        WebDriver driver = null;
        System.setProperty("webdriver.chrome.driver",chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);

        for (int i = 1; i > 0; i++) {
            try {
                driver.get("https://www.trendyol.com/giris");
                Thread.sleep(3000L);

                WebElement emailTxt = driver.findElement(By.id("login-email"));
                emailTxt.sendKeys(trendyolUyelik.getEposta());

                WebElement sifreTxt = driver.findElement(By.id("login-password-input"));
                sifreTxt.sendKeys(trendyolUyelik.getSifre());

                List<WebElement> loginButtonList = driver.findElements(By.className("submit"));
                loginButtonList.get(0).click();
                break;
            } catch (Exception e) {
                System.out.println("Hata 2");
                Thread.sleep(3000L);
            }
        }

        sepeteUrunEkle(driver, "https://www.trendyol.com/steam/a-p-40275311");//steam cüzdan
        sepeteUrunEkle(driver, "https://www.trendyol.com/apple/a-p-65149494");//iphone 11

        Thread.sleep(3000L);
        driver.close();

        return true;
    }

    private void sepeteUrunEkle(WebDriver driver, String urunLink) throws InterruptedException {
        int sayac = 0;
        for (int i = 1; i > 0; i++) {
            if (sayac == 10) {
                break;
            }
            try {
                Thread.sleep(3000L);
                driver.get(urunLink);
                Thread.sleep(3000L);

                WebElement sepeteEkleBtn = driver.findElement(By.className("add-to-bs"));
                sepeteEkleBtn.click();
                break;
            } catch (Exception e) {
                System.out.println("Hata 3");
                sayac++;
            }
        }
    }
}
