package com.app.fku.yemeksepeti.fonksiyon.impl;

import com.app.fku.genel.enums.MailSahipEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.yemeksepeti.entity.YmkUyelik;
import com.app.fku.yemeksepeti.fonksiyon.service.YmkEkranOkumaService;
import com.app.fku.yemeksepeti.fonksiyon.service.YmkGenelService;
import com.app.fku.yemeksepeti.repository.YmkUyelikRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class YmkEkranOkumaServiceImpl implements YmkEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    YmkGenelService ymkGenelService;

    @Autowired
    YmkUyelikRepository ymkUyelikRepository;

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
            //options.addArguments("--start-fullscreen");
            driver = new ChromeDriver(options);
            //Uye Ol START
            try {
                driver.get("https://www.yemeksepeti.com/ankara-uye-ol");
                Thread.sleep(BEKLEME_SURESI_3SN);

                String ad = genelService.yeniAdUret();
                String soyad = genelService.yeniSoyadUret();
                String mail = ad + "." + soyad + "@ilkmail.com";

                WebElement emailElement = driver.findElement(By.id("inputEmail"));
                emailElement.sendKeys(mail);

                WebElement sifreElement = driver.findElement(By.id("inputPassword"));
                sifreElement.sendKeys("179237kt");

                WebElement yenidenSifreElement = driver.findElement(By.id("inputRepeatPassword"));
                yenidenSifreElement.sendKeys("179237kt");

                WebElement isimElement = driver.findElement(By.id("inputFirstName"));
                isimElement.sendKeys(ad);

                WebElement soyisimElement = driver.findElement(By.id("inputLastName"));
                soyisimElement.sendKeys(soyad);

                WebElement dogumTarihiElement = driver.findElement(By.id("inputBirthDate"));
                dogumTarihiElement.sendKeys("26091995");

                WebElement semtElement = driver.findElement(By.className("select2-selection__rendered"));
                semtElement.click();
                Thread.sleep(BEKLEME_SURESI_1SN);

                List<WebElement> semtler = driver.findElements(By.className("select2-results__option"));
                for (WebElement semt: semtler) {
                    if (semt.getText().equals("Etimesgut (Piyade Mah.)")) {
                        semt.click();
                        break;
                    }
                }

                WebElement sozlesmeCheckElement = driver.findElement(By.id("inputAcceptEula"));
                sozlesmeCheckElement.click();

                WebElement emailSozlesmeCheckElement = driver.findElement(By.id("inputEmailNotification"));
                emailSozlesmeCheckElement.click();

                WebElement kayitButtonElement = driver.findElement(By.className("register-button"));
                kayitButtonElement.click();
                Thread.sleep(BEKLEME_SURESI_2SN);
                driver.close();

                YmkUyelik uyelik = new YmkUyelik();
                uyelik.setEposta(mail);
                uyelik.setKayitTarihi(new Date());
                uyelik.setMailSahipEnum(MailSahipEnum.KERIM);
                uyelik.setSifre("179237kt");
                ymkUyelikRepository.save(uyelik);
                ymkUyelikRepository.flush();
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
}