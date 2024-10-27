package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.enums.MailSahipEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.hepsiburada.entity.HbUyelik;
import com.app.fku.hepsiburada.fonksiyon.impl.HbUyelikService;
import com.app.fku.hepsiburada.repository.HbKategoriRepository;
import com.app.fku.hepsiburada.repository.HbUrunRepository;
import com.app.fku.hepsiburada.repository.HbUyelikRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.app.fku.genel.fonksiyon.YandexMail.readYandexLastMail;

@Service
public class HbUyelikServiceImpl implements HbUyelikService {

    @Autowired
    GenelService genelService;

    @Autowired
    HbUyelikRepository hbUyelikRepository;

    public String chromeDriverPath = "C:\\ChromeDriver\\105\\chromedriver_win32\\chromedriver.exe";

    public Long BEKLEME_SURESI_6SN = 6000L;
    public Long BEKLEME_SURESI_5SN = 5000L;
    public Long BEKLEME_SURESI_4SN = 4000L;
    public Long BEKLEME_SURESI_3SN = 3000L;
    public Long BEKLEME_SURESI_2SN = 2000L;
    public Long BEKLEME_SURESI_1SN = 1000L;

    @Override
    public void uyelikAc() throws MessagingException, IOException {
        String ad = genelService.yeniAdUret();
        String soyad = genelService.yeniSoyadUret();

        WebDriver driver = null;
        try {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("--start-fullscreen");
            driver = new ChromeDriver(options);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            driver.get("https://giris.hepsiburada.com/?ReturnUrl=https%3A%2F%2Foauth.hepsiburada.com%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3DSPA%26redirect_uri%3Dhttps%253A%252F%252Fwww.hepsiburada.com%252Fuyelik%252Fcallback%26response_type%3Dcode%26scope%3Dopenid%2520profile%26state%3De9edb195a12b4228a9d3f784ac065eef%26code_challenge%3DZj9Di5g5h0LQBcBW4mVboagsupN5vQC5vMbnGU9vzWA%26code_challenge_method%3DS256%26response_mode%3Dquery%26ActivePage%3DSIGN_UP%26oidcReturnUrl%3Dhttps%253A%252F%252Fwww.hepsiburada.com%252F");
            Thread.sleep(BEKLEME_SURESI_3SN);

            WebElement mailElement = driver.findElement(By.id("txtEmail"));
            mailElement.sendKeys(ad + "." + soyad + "@aykank.site");

            Thread.sleep(BEKLEME_SURESI_1SN);

            WebElement devamButtonElement = driver.findElement(By.id("btnSignUpSubmit"));
            devamButtonElement.click();

            Thread.sleep(BEKLEME_SURESI_1SN);

            driver.close();

            Thread.sleep(BEKLEME_SURESI_4SN);

            String mesaj = readYandexLastMail("aykank.site@yandex.com", "Ay150185");

            String tekSeferlikLink = tekSeferlikLinkGetir(mesaj);

            driver = new ChromeDriver(options);
            driver.get(tekSeferlikLink);

            Thread.sleep(BEKLEME_SURESI_4SN);

            WebElement adElement = driver.findElement(By.id("txtName"));
            adElement.sendKeys(ad);

            WebElement soyadElement = driver.findElement(By.id("txtSurname"));
            soyadElement.sendKeys(soyad);

            WebElement sifreElement = driver.findElement(By.id("txtNewPassEmail"));
            sifreElement.sendKeys("Cc123456");

            WebElement checkElement = driver.findElement(By.id("checkSubscribeEmail"));
            checkElement.click();

            Thread.sleep(BEKLEME_SURESI_1SN);

            WebElement kayitButtonElement = driver.findElement(By.id("btnSignUpSubmit"));
            kayitButtonElement.click();

            Thread.sleep(BEKLEME_SURESI_5SN);

            driver.close();

            HbUyelik hbUyelik = new HbUyelik();
            hbUyelik.setEposta(ad + "." + soyad + "@aykank.site");
            hbUyelik.setSifre("Cc123456");
            hbUyelik.setKayitTarihi(new Date());
            hbUyelik.setAdresVarMi(EvetHayirEnum.HAYIR);
            hbUyelik.setMailSahipEnum(MailSahipEnum.EMIN);
            hbUyelikRepository.save(hbUyelik);

        } catch (Exception e) {
            driver.close();
        }
    }

    private String tekSeferlikLinkGetir(String mesaj) {
        String[] msjArr = mesaj.split("<a href=\"");
        for (int i = 0; i < msjArr.length; i++) {
            if (msjArr[i].contains("-seferlik-")) {
                return msjArr[i].split("\"")[0];
            }
        }
        return "";
    }

    @Override
    public void kuponKontrol() {
        List<HbUyelik> hbUyelikList = hbUyelikRepository.findByMailSahipEnum(MailSahipEnum.EMIN);
        for (HbUyelik hbUyelik: hbUyelikList) {
            kuponKontrol(hbUyelik);
        }
    }

    private void kuponKontrol(HbUyelik hbUyelik) {
        WebDriver driver = null;
        try {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions().setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe").setHeadless(false);
            driver = new ChromeDriver(options);

            JavascriptExecutor js = (JavascriptExecutor) driver;

            driver.get("https://giris.hepsiburada.com/?ReturnUrl=https%3A%2F%2Foauth.hepsiburada.com%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3DSPA%26redirect_uri%3Dhttps%253A%252F%252Fwww.hepsiburada.com%252Fuyelik%252Fcallback%26response_type%3Dcode%26scope%3Dopenid%2520profile%26state%3D8e29ef0556c44a6e8bed154ae041dcdc%26code_challenge%3DQ2Rkn0FsrUJaev1Wv2hgSd6rYarW6UuK8ODgVeNfn1U%26code_challenge_method%3DS256%26response_mode%3Dquery%26ActivePage%3DPURE_LOGIN%26oidcReturnUrl%3Dhttps%253A%252F%252Fwww.hepsiburada.com%252F");
            Thread.sleep(BEKLEME_SURESI_3SN);

            System.out.println("");

            WebElement mailElement = driver.findElement(By.id("txtUserName"));
            mailElement.sendKeys("emin.ahmet.hb.2@aykank.site");

            Thread.sleep(BEKLEME_SURESI_1SN);

            WebElement loginButtonElement = driver.findElement(By.id("btnLogin"));
            loginButtonElement.click();
        } catch (Exception e) {
            driver.close();
        }
    }
}
