package com.app.fku;

import com.microsoft.playwright.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Amazon {

    public static void main(String args[]) {

        try (Playwright playwright = Playwright.create()) {
            List<String> argsList = new ArrayList<>();
            argsList.add("--disable-web-security");
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setArgs(argsList).setHeadless(false));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));

            /**
             Page arzumPage = context.newPage();
             HashMap<String, Long> arzumUrunHashMap = new HashMap<>();
             Page philipsPage = context.newPage();
             HashMap<String, Long> philipsUrunHashMap = new HashMap<>();
             Page msiPage = context.newPage();
             HashMap<String, Long> msiUrunHashMap = new HashMap<>();
             Page tefalPage = context.newPage();
             HashMap<String, Long> tefalUrunHashMap = new HashMap<>();
             Page karacaPage = context.newPage();
             HashMap<String, Long> karacaUrunHashMap = new HashMap<>();
             Page samsungPage = context.newPage();
             HashMap<String, Long> samsungUrunHashMap = new HashMap<>();
             Page iphoneStorePage = context.newPage();
             HashMap<String, Long> iphoneStoreUrunHashMap = new HashMap<>();
             Page appleWatchStorePage = context.newPage();
             HashMap<String, Long> appleWatchStoreUrunHashMap = new HashMap<>();
             Page macbookStorePage = context.newPage();
             HashMap<String, Long> macbookStoreUrunHashMap = new HashMap<>();
             Page samsungTelefonStorePage = context.newPage();
             HashMap<String, Long> samsungTelefonStoreUrunHashMap = new HashMap<>();
             Page tefalUtuStorePage = context.newPage();
             HashMap<String, Long> tefalUtuStoreUrunHashMap = new HashMap<>();
             Page tefalPisirmeStorePage = context.newPage();
             HashMap<String, Long> tefalPisirmeStoreUrunHashMap = new HashMap<>();
             Page tefalBlendStorePage = context.newPage();
             HashMap<String, Long> tefalBlendStoreUrunHashMap = new HashMap<>();
             Page tefalTencereStorePage = context.newPage();
             HashMap<String, Long> tefalTencereStoreUrunHashMap = new HashMap<>();*/

            Page philipsStorePage = context.newPage();
            HashMap<String, Long> philipsStoreUrunHashMap = new HashMap<>();
            Page braunStorePage = context.newPage();
            HashMap<String, Long> braunStoreUrunHashMap = new HashMap<>();
            Page samsungStorePage = context.newPage();
            HashMap<String, Long> samsungStoreUrunHashMap = new HashMap<>();
            Page iphoneStorePage = context.newPage();
            HashMap<String, Long> iphoneStoreUrunHashMap = new HashMap<>();

            int sayac = 0;
            for (; ; ) {
                /**
                 arzumUrunHashMap = kontrolEt(arzumPage, "https://www.amazon.com.tr/deal/713cb50a?showVariations=true&pf_rd_r=K4FNKR50QY0290TQ3J8Z&pf_rd_t=Events&pf_rd_i=okuladonus&pf_rd_p=b2fe87a3-8f51-41c0-8020-fd4924c83f44&pf_rd_s=slot-6&ref=dlx_okula_gd_dcl_tlt_7_713cb50a_dt_sl6_44&sortOption=price-desc-rank", arzumUrunHashMap, "Arzum Mutfak ve Kişisel Bakım Ürünleri", sayac);
                 philipsUrunHashMap = kontrolEt(philipsPage, "https://www.amazon.com.tr/deal/eaa868df?showVariations=true&pf_rd_r=K4FNKR50QY0290TQ3J8Z&pf_rd_t=Events&pf_rd_i=okuladonus&pf_rd_p=b2fe87a3-8f51-41c0-8020-fd4924c83f44&pf_rd_s=slot-6&ref=dlx_okula_gd_dcl_tlt_0_eaa868df_dt_sl6_44", philipsUrunHashMap, "Philips Ev Aletleri", sayac);
                 msiUrunHashMap = kontrolEt(msiPage, "https://www.amazon.com.tr/deal/c74dde66?showVariations=true&moreDeals=d6fefac2&pf_rd_r=MFRJ9YDWHYPJDGDJ4GCS&pf_rd_t=Events&pf_rd_i=okuladonus&pf_rd_p=b2fe87a3-8f51-41c0-8020-fd4924c83f44&pf_rd_s=slot-6&ref=dlx_okula_gd_dcl_tlt_11_c74dde66_dt_sl6_44&sortOption=relevanceblender", msiUrunHashMap, "Dell, MSI, Samsung, ASUS, Gigabyte Ürünleri", sayac);
                 tefalUrunHashMap = kontrolEt(tefalPage, "https://www.amazon.com.tr/deal/6e823775?showVariations=true&pf_rd_r=MFRJ9YDWHYPJDGDJ4GCS&pf_rd_t=Events&pf_rd_i=okuladonus&pf_rd_p=b2fe87a3-8f51-41c0-8020-fd4924c83f44&pf_rd_s=slot-6&ref=dlx_okula_gd_dcl_tlt_15_6e823775_dt_sl6_44", tefalUrunHashMap, "Tefal Ürünleri", sayac);
                 karacaUrunHashMap = kontrolEt(karacaPage, "https://www.amazon.com.tr/deal/03117bbe?showVariations=true&pf_rd_r=MFRJ9YDWHYPJDGDJ4GCS&pf_rd_t=Events&pf_rd_i=okuladonus&pf_rd_p=b2fe87a3-8f51-41c0-8020-fd4924c83f44&pf_rd_s=slot-6&ref=dlx_okula_gd_dcl_img_16_03117bbe_dt_sl6_44", karacaUrunHashMap, "Karaca küçük ev aletleri", sayac);
                 samsungUrunHashMap = kontrolEt(samsungPage, "https://www.amazon.com.tr/deal/0c01ada8?showVariations=true&pf_rd_r=MFRJ9YDWHYPJDGDJ4GCS&pf_rd_t=Events&pf_rd_i=okuladonus&pf_rd_p=b2fe87a3-8f51-41c0-8020-fd4924c83f44&pf_rd_s=slot-6&ref=dlx_okula_gd_dcl_tlt_4_0c01ada8_dt_sl6_44", samsungUrunHashMap, "Samsung Akıllı Telefonlar", sayac);
                 iphoneStoreUrunHashMap = kontrolEtStore(iphoneStorePage,"https://www.amazon.com.tr/stores/page/37693D46-6D9F-4033-A48D-8D826598DA3D?ingress=2", iphoneStoreUrunHashMap, "iPhone Store", sayac);
                 appleWatchStoreUrunHashMap = kontrolEtStore(appleWatchStorePage,"https://www.amazon.com.tr/stores/page/D5C26571-676E-471E-902F-AF6B2AE7240F?ingress=2", appleWatchStoreUrunHashMap, "Apple Watch Store", sayac);
                 macbookStoreUrunHashMap = kontrolEtStore(macbookStorePage,"https://www.amazon.com.tr/stores/page/DC78B9FC-AEE7-4999-A7B0-E0BCA797B6CC?ingress=2", macbookStoreUrunHashMap, "Macbook Store", sayac);
                 samsungTelefonStoreUrunHashMap = kontrolEtStore(samsungTelefonStorePage,"https://www.amazon.com.tr/stores/page/E0431754-C6AE-4DD3-B150-FF2E9D817036?ingress=2", samsungTelefonStoreUrunHashMap, "Samsung Store Telefon", sayac);
                 tefalUtuStoreUrunHashMap = kontrolEtStore(tefalUtuStorePage,"https://www.amazon.com.tr/stores/page/E4E3A2F8-D365-4225-89FD-F6BF88E90844?ingress=2", tefalUtuStoreUrunHashMap, "Tefal Store Ütü", sayac);
                 tefalPisirmeStoreUrunHashMap = kontrolEtStore(tefalPisirmeStorePage,"https://www.amazon.com.tr/stores/page/59F581A2-E25A-4338-A41C-48331EFDBB94?ingress=2", tefalPisirmeStoreUrunHashMap, "Tefal Store Pişirme", sayac);
                 tefalBlendStoreUrunHashMap = kontrolEtStore(tefalBlendStorePage,"https://www.amazon.com.tr/stores/page/D7609F8C-A2D5-4371-B38D-385726E68C15?ingress=2", tefalBlendStoreUrunHashMap, "Tefal Blend Pişirme", sayac);
                 tefalTencereStoreUrunHashMap = kontrolEtStore(tefalTencereStorePage,"https://www.amazon.com.tr/stores/page/49A8EB8E-A078-494A-BB24-3D2D97FC538A?ingress=2", tefalTencereStoreUrunHashMap, "Tefal Tencere Pişirme", sayac);*/
                philipsStoreUrunHashMap = kontrolEtStore(philipsStorePage, "https://www.amazon.com.tr/stores/page/110AA19D-10F2-4F3C-8FF8-FE504A471F26/search?ingress=2&terms=philips", philipsStoreUrunHashMap, "Philips Store", sayac);
                braunStoreUrunHashMap = kontrolEtStore(braunStorePage, "https://www.amazon.com.tr/stores/page/0345F6D4-313A-4B69-BB28-974ED345B0C2/search?ingress=2&terms=braun", braunStoreUrunHashMap, "Braun Store", sayac);
                samsungStoreUrunHashMap = kontrolEtStore(samsungStorePage, "https://www.amazon.com.tr/stores/page/E0431754-C6AE-4DD3-B150-FF2E9D817036?ingress=2", samsungStoreUrunHashMap, "Samsung Store Telefon", sayac);
                iphoneStoreUrunHashMap = kontrolEtStore(iphoneStorePage, "https://www.amazon.com.tr/stores/page/37693D46-6D9F-4033-A48D-8D826598DA3D?ingress=2", iphoneStoreUrunHashMap, "iPhone Store Telefon", sayac);
                sayac = 1;
            }

        } catch (Exception e) {
            System.out.println("asd");
            e.printStackTrace();
        }
    }

    public static HashMap<String, Long> kontrolEt(Page page, String url, HashMap<String, Long> urunHashMap, String threadName, int sayac) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        HashMap<String, Long> yeniUrunHashMap = new HashMap<>();

        System.out.println(threadName + " - " + sdf.format(new Date()) + " - " + urunHashMap.size());
        int sayfaSayac = 1;
        int sifirSayac = 0;
        page.navigate(url);
        for (int m = 1; m > 0; m++) {
            List<Locator> urunLocatorList = page.locator(".a-list-normal").all();
            if (urunLocatorList.size() == 0) {
                sifirSayac++;
                if (sifirSayac == 6) {
                    return urunHashMap;
                }
                page.navigate(url);
                continue;
            }
            for (Locator li : urunLocatorList) {
                String href = li.locator(".a-link-normal").all().get(0).getAttribute("href");
                String amzNo = href.split("dp/")[1].split("\\?")[0];
                List<Locator> priceLocatorList = li.locator(".a-price-whole").all();
                if (priceLocatorList.size() == 0) {
                    continue;
                }
                Long fiyat = Long.valueOf(priceLocatorList.get(0).innerText().split(",")[0].replace(".", "").trim());

                Long eskiFiyat = urunHashMap.get(amzNo);
                if (eskiFiyat != null) {
                    if (fiyat < eskiFiyat) {
                        //İndirim - Mesaj At
                        try {
                            telegramMesajGonder("İndirim : " + eskiFiyat + " : " + fiyat + " : www.amazon.com.tr/a/dp/" + amzNo, sayac);
                        } catch (Exception e) {
                            System.out.println("Telegram hata");
                        }
                    }
                } else {
                    //Yeni Ürün - Mesaj At
                    try {
                        telegramMesajGonder("Yeni Ürün : " + fiyat + " : www.amazon.com.tr/a/dp/" + amzNo, sayac);
                    } catch (Exception e) {
                        System.out.println("Telegram hata");
                    }
                }

                urunHashMap.put(amzNo, fiyat);
            }

            if (page.locator(".a-pagination").all().size() == 0) {
                //sayfa yok
                break;
            }

            if (page.locator(".a-last.a-disabled").all().size() == 0) {
                sayfaSayac++;
                page.navigate(url + "&pageNum=" + sayfaSayac);
            } else {
                break;
            }
        }
        return urunHashMap;
    }

    public static HashMap<String, Long> kontrolEtStore(Page page, String url, HashMap<String, Long> urunHashMap, String threadName, int sayac) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");

        System.out.println(threadName + " - " + sdf.format(new Date()) + " - " + urunHashMap.size());
        page.navigate(url);
        for (; ; ) {
            try {
                List<Locator> dahaFazlaLocatorList = page.locator("div[data-testid=grid-more-button]").all();
                if (dahaFazlaLocatorList.size() == 0) {
                    break;
                }
                dahaFazlaLocatorList.get(0).click();
                Thread.sleep(1500);
            } catch (Exception e) {
                System.out.println("More Button Hata");
            }
        }

        List<Locator> urunLocatorList = page.locator("li[data-testid=product-grid-item]").all();
        for (Locator li : urunLocatorList) {
            if (li.locator("img[alt=Prime]").all().size() == 0) {
                continue;
            }

            String href = li.locator(".Overlay__overlay__LloCU.ProductGridItem__overlay__IQ3Kw").getAttribute("href");
            String amzNo = href.split("dp/")[1].split("\\?")[0];

            List<Locator> priceLocatorList = li.locator(".Price__whole__mQGs5").all();
            if (priceLocatorList.size() == 0) {
                continue;
            }

            Long fiyat = Long.valueOf(priceLocatorList.get(0).innerText().split(",")[0].replace(".", "").trim());
            Long eskiFiyat = urunHashMap.get(amzNo);
            if (eskiFiyat != null) {
                if (fiyat < eskiFiyat) {
                    //İndirim - Mesaj At
                    try {
                        telegramMesajGonder("İndirim : " + eskiFiyat + " : " + fiyat + " : www.amazon.com.tr/a/dp/" + amzNo, sayac);
                    } catch (Exception e) {
                        System.out.println("Telegram hata");
                    }
                }
            } else {
                //Yeni Ürün - Mesaj At
                try {
                    telegramMesajGonder("Yeni Ürün : " + fiyat + " : www.amazon.com.tr/a/dp/" + amzNo, sayac);
                } catch (Exception e) {
                    System.out.println("Telegram hata");
                }
            }

            urunHashMap.put(amzNo, fiyat);
        }
        return urunHashMap;
    }

    public static void telegramMesajGonder(String mesaj, int sayac) throws IOException, InterruptedException {
        if (sayac == 0) {
            return;
        }

        String chatId = "-948987566";
        String token = "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM";

        //System.out.println(urunId + " " + chatId + " mesaj gönderdim. " + urunId);
        mesaj = mesaj.replace(" h", "xh");
        mesaj = mesaj.replace(" H", "xh");
        mesaj = mesaj.replace("ş", "s");
        mesaj = mesaj.replace("Ş", "s");
        mesaj = mesaj.replace("ı", "i");
        mesaj = mesaj.replace("İ", "i");
        mesaj = mesaj.replace("ğ", "g");
        mesaj = mesaj.replace("Ğ", "g");
        mesaj = mesaj.replace("ö", "o");
        mesaj = mesaj.replace("Ö", "o");
        mesaj = mesaj.replace("Ç", "c");
        mesaj = mesaj.replace("ç", "c");
        mesaj = mesaj.replace("Ü", "u");
        mesaj = mesaj.replace("ü", "u");
        mesaj = mesaj.replace("&", "");
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
        //Add Telegram token
        //        String apiToken = "1518724507:AAElzNxKZyZi_9oIGiX9tybXCS6Xd4JXRnk";//emincansubot
        //        String apiToken = "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM";//fkumesajbot
        //        String apiToken = "1610924512:AAFdxtkwC0zSCyZnmWrmN4Gixj4JIgNO-6U";
        String text = mesaj;
        urlString = String.format(urlString, token, chatId, text);
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            Thread.sleep(10000L);
        }
    }
}
