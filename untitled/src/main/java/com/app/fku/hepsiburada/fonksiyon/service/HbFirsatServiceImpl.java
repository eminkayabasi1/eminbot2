package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.hepsiburada.entity.HbSepetAlim;
import com.app.fku.hepsiburada.fonksiyon.impl.HbFirsatService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.repository.HbSepetAlimRepository;
import com.app.fku.hepsiburada.repository.HbTelegramConfRepository;
import com.microsoft.playwright.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HbFirsatServiceImpl implements HbFirsatService {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    HbTelegramConfRepository hbTelegramConfRepository;

    @Autowired
    HbSepetAlimRepository hbSepetAlimRepository;

    @Override
    public void firsatBul(String eposta) throws IOException, InterruptedException {
        try (Playwright playwright = Playwright.create()) {
            List<String> argsList = new ArrayList<>();
            argsList.add("--disable-web-security");
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setArgs(argsList).setHeadless(false));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));
            Page page = context.newPage();
            page.navigate("https://www.akakce.com/arama/?q=" + eposta);

            for (; ; ) {
                try {
                    List<HbSepetAlim> hbSepetAlimList = hbSepetAlimRepository.findByEpostaAndDurum(eposta, 1);
                    if (hbSepetAlimList == null || hbSepetAlimList.size() == 0) {
                        Thread.sleep(2500);
                        continue;
                    }

                    HbSepetAlim hbSepetAlim = hbSepetAlimList.get(0);

                    genelService.telegramHbSepetGonder("İşlem bitti : " + eposta + " : " + hbSepetAlim.getHbNo(), "A");

                    String link = hbSepetAlim.getLink();
                    Integer adet = hbSepetAlim.getAdet();
                    alimYap(page, link, adet);

                    hbSepetAlim.setDurum(0);
                    hbSepetAlim.setSiparisTarihi(new Date());
                    hbSepetAlimRepository.save(hbSepetAlim);
                    genelService.telegramHbSepetGonder("İşlem bitti : " + eposta + " : " + hbSepetAlim.getHbNo(), "A");
                } catch (Exception e) {
                    System.out.println("Hata var sepet");
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void alimYap(Page page, String link, Integer adet) throws InterruptedException {

        page.navigate(link);

        //SEPETE EKLE
        for (; ; ) {
            try {
                page.locator("#addToCart").click();
                break;
            } catch (Exception e) {
                System.out.println("Hata-1");
            }
        }
        //SEPETE EKLE

        Thread.sleep(2000L);

        //SEPETE GİT
        for (; ; ) {
            try {
                page.navigate("https://checkout.hepsiburada.com/");
                break;
            } catch (Exception e) {
                System.out.println("Hata-2");
            }
        }
        //SEPETE GİT

        //ADETİ ARTTIR
        for (; ; ) {
            try {
                page.locator("input[name=quantity]").fill(adet + "");
                break;
            } catch (Exception e) {
                System.out.println("Hata-3");
            }
        }
        //ADETİ ARTTIR

        Thread.sleep(2000L);

        //DEVAM
        for (; ; ) {
            try {
                page.locator("#continue_step_btn").click();
                break;
            } catch (Exception e) {
                System.out.println("Hata-4");
            }
        }
        //DEVAM

        Thread.sleep(2000L);

        //HEPSİPAY BAKİYE CHECKBOX
        for (; ; ) {
            try {
                if (!page.locator(".installment_3nmcP").locator("input").elementHandles().get(0).isChecked()) {
                    page.locator(".installment_3nmcP").locator("input").elementHandles().get(0).check();
                }
                break;
            } catch (Exception e) {
                System.out.println("Hata-5");
            }
        }
        //HEPSİPAY BAKİYE CHECKBOX

        //CHECKBOX
        for (; ; ) {
            try {
                page.locator("#agreement_check").locator("input").elementHandles().get(0).check();
                break;
            } catch (Exception e) {
                System.out.println("Hata-6");
            }
        }
        //CHECKBOX

        //BİTİR
        for (; ; ) {
            try {
                page.locator("#continue_step_btn").click();
                break;
            } catch (Exception e) {
                System.out.println("Hata-7");
            }
        }
        //BİTİR

        System.out.println(page.title());

    }
}
