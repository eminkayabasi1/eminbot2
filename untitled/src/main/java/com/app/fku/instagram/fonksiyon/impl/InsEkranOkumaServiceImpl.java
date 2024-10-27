package com.app.fku.instagram.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.hepsiburada.entity.HbUyelik;
import com.app.fku.instagram.entity.InsUyelik;
import com.app.fku.instagram.fonksiyon.service.InsEkranOkumaService;
import com.app.fku.instagram.fonksiyon.service.InsGenelService;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import com.sun.mail.imap.IMAPMessage;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

@Service
public class InsEkranOkumaServiceImpl implements InsEkranOkumaService {

    @Autowired
    GenelService genelService;

    @Autowired
    InsGenelService insGenelService;

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
    public void takipciKas() throws InterruptedException {

        WebDriver driver = null;
        InsUyelik insUyelik = null;
        try {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("lang=zh_CN.UTF-8");
            String headerStr = insGenelService.headerGetir();
            options.addArguments("User-Agent=\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36\"");
            driver = new ChromeDriver(options);
            //Uye Ol START
            try {
                insUyelik = hesapAc(driver);
            } catch (Exception e) {
                System.out.println("Üye Ol Ekranında Hata Aldım.");
                driver.close();
                throw new Exception();
            }
            //Uye Ol END
        } catch (Exception e) {
            takipciKas();
        }
    }

    public InsUyelik hesapAc(WebDriver driver) throws InterruptedException, MessagingException {
        driver.get("https://www.instagram.com/accounts/emailsignup/?hl=tr");
        Thread.sleep(BEKLEME_SURESI_5SN);

        String kullaniciAdi = genelService.yeniMailAdiUret();
        String mailAdi = kullaniciAdi + "@aykank.site";

        WebElement emailElement = driver.findElement(By.name("emailOrPhone"));
        emailElement.sendKeys(mailAdi);

        WebElement isimElement = driver.findElement(By.name("fullName"));
        isimElement.sendKeys("Emin Kayabaşı");

        WebElement userNameElement = driver.findElement(By.name("username"));
        userNameElement.sendKeys(kullaniciAdi);

        WebElement sifreElement = driver.findElement(By.name("password"));
        sifreElement.sendKeys("Aa123456");

        Thread.sleep(BEKLEME_SURESI_2SN);
        WebElement kayitButtonElement = driver.findElements(By.className("sqdOP")).get(3);
        kayitButtonElement.click();
        Thread.sleep(BEKLEME_SURESI_4SN);

        List<WebElement> dogumGunuElementList = driver.findElements(By.className("h144Z"));
        dogumGunuElementList.get(0).sendKeys("1");
        dogumGunuElementList.get(1).sendKeys("1");
        dogumGunuElementList.get(2).sendKeys("1995");

        driver.findElements(By.className("L3NKy")).get(0).click();
        Thread.sleep(BEKLEME_SURESI_20SN);

        String onayKodu = maildenOnayKoduGetir();
        WebElement dogrulamaKoduWebElement = driver.findElement(By.name("email_confirmation_code"));
        dogrulamaKoduWebElement.sendKeys(onayKodu);

        List<WebElement> dogrulamaButtonWebElementList = driver.findElements(By.className("y3zKF"));
        dogrulamaButtonWebElementList.get(1).click();
        return null;
    }

    public String maildenOnayKoduGetir() throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.ssl.trust", "imap.yandex.ru");
        Session session = Session.getDefaultInstance(props, null);
        Store store;
        store = session.getStore();
        store.connect("imap.yandex.ru", 993, "aykank.site@yandex.com", "Ay150185");
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE); // even though we only want to read, write persmission will allow us to set SEEN flag
        try {
            Message lastMessage = inbox.getMessage(inbox.getMessageCount());
            ((IMAPMessage)lastMessage).setPeek(true); // this is how you prevent automatic SEEN flag
            MimeMessage cmsg = new MimeMessage((MimeMessage)lastMessage); // this is how you deal with exception "Unable to load BODYSTRUCTURE"
//            printMessage(cmsg);
//            markMessageAsSeen(lastMessage, inbox);

            FlagTerm unreadFlag = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message unreadMessages[] = inbox.search(unreadFlag);
            String onayKodu = getOnayKodu(unreadMessages[unreadMessages.length - 1]);
            return onayKodu;
//            for (Message unreadMessage : unreadMessages) {
//                // SEEN flag here will be set automatically.
//                printMessage(unreadMessage);
//            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        } finally {
            inbox.close(true);
        }
        return "";
    }

    private String getOnayKodu(Message message) throws MessagingException, IOException {
        String[] onayKoduArr = getText(message).trim().split("text-align:center;padding-bottom:25px;\">");
        String onayKodu = onayKoduArr[1].substring(0,6);
        return onayKodu;
    }

    private String getText(Part p) throws
            MessagingException, IOException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }
        return null;
    }
}