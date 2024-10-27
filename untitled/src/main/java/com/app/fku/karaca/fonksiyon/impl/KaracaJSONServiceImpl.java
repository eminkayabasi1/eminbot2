package com.app.fku.karaca.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.karaca.fonksiyon.service.KaracaJSONService;
import com.app.fku.karaca.model.KaracaGenelModel;
import com.app.fku.karaca.model.KaracaUrunModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.openqa.selenium.json.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class KaracaJSONServiceImpl implements KaracaJSONService {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;


    private static final List<LinkModel> urlList = new ArrayList<>();

    @Override
    public void sorgula() throws InterruptedException {
        //urlList.add(new LinkModel("https://www.karaca.com/marka/karaca-home?a%5B52%5D=Nevresim+Tak%C4%B1m%C4%B1&sort=p.price&q=&order=ASC&qb=&json=true", 1.00d, 4114700595L)); //Nevresim %0 Z Y
        urlList.add(new LinkModel("https://www.karaca.com/sana-ozel-surpriz-firsatlar?p%5Bp%5D=950-35000&m%5Bm%5D=1&sort=p.price&order=DESC&json=true", 0.84d, 4592159290L)); //Sana Özel %15
        urlList.add(new LinkModel("https://www.karaca.com/marka/karaca?p%5Bp%5D=1000-60000&sort=p.price&q=&order=ASC&qb=&json=true", 1.00d, 4095397196L)); //Karaca %0// X Y
        urlList.add(new LinkModel("https://www.karaca.com/marka/emsan?p%5Bp%5D=1000-60000&sort=p.price&q=&order=ASC&qb=&json=true", 1.00d, 4095397196L));  //Emsan %0 X Y
        urlList.add(new LinkModel("https://www.karaca.com/marka/jumbo?p%5Bp%5D=1000-60000&sort=p.price&q=&order=ASC&qb=&json=true", 1.00d, 4095397196L));  //Jumbo %0 X Y
        urlList.add(new LinkModel("https://www.karaca.com/marka/homend?p%5Bp%5D=1000-60000&sort=p.price&q=&order=DESC&qb=&json=true", 1.00d, 4095397196L));  //Homend %0 X Y
        urlList.add(new LinkModel("https://www.karaca.com/1-alana-1-bedava-urunler?p%5Bp%5D=500-50000&m%5Bm%5D=2%2C131&sort=p.price&q=&order=DESC&json=true", 1.00d, 4564751973L));  //KaracaHome Sarah 1alana1bedava %0 X Y
        try {
            anaislem();
        } catch (Exception e) {
            System.out.println("hata geldi karaca");
        }

    }

    private void anaislem() throws InterruptedException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        HashMap<String, KaracaUrunModel> urunHashMap = new HashMap<>();
        boolean ilkTur = true;
        for (; ; ) {
            List<KaracaUrunModel> topluUrunList = new ArrayList<>();
            HashMap<String, KaracaUrunModel> yeniUrunHashMap = new HashMap<>();
            for (LinkModel linkModel : urlList) {
                KaracaGenelModel karacaGenelModel = readJsonFromUrl(linkModel.getUrl() + "&page=1");
                Integer piCount = karacaGenelModel.getTotal();
                List<KaracaUrunModel> karacaUrunModelList = karacaGenelModel.getProducts();
                for (KaracaUrunModel karacaUrunModel : karacaUrunModelList) {
                    if (karacaUrunModel.getName().contains("Porselen") || karacaUrunModel.getName().contains("porselen") || karacaUrunModel.getName().contains("PORSELEN") ||
                            karacaUrunModel.getName().contains("Fincan") || karacaUrunModel.getName().contains("FİNCAN") || karacaUrunModel.getName().contains("fincan")
                    || karacaUrunModel.getName().contains("Yemek Takımı") || karacaUrunModel.getName().contains("YEMEK TAKIMI")
                    || karacaUrunModel.getName().contains("YemekTakımı") || karacaUrunModel.getName().contains("YEMEKTAKIMI")) {
                        continue;
                    }
                    karacaUrunModel.setFiyat(Integer.valueOf(karacaUrunModel.getUnFormattedPrice().split("\\.")[0]));
                    karacaUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                    karacaUrunModel.setBirlikteAlKazanMi(birlikteAlKazanMi(karacaUrunModel.getBadgeList()));
                    karacaUrunModel.setTelegramChatId(linkModel.getTelegramChatId());
                    topluUrunList.add(karacaUrunModel);
                }

                for (int i = 2; i <= piCount; i++) {
                    karacaGenelModel = readJsonFromUrl(linkModel.getUrl() + "&page=" + i);
                    karacaUrunModelList = karacaGenelModel.getProducts();
                    for (KaracaUrunModel karacaUrunModel : karacaUrunModelList) {
                        if (karacaUrunModel.getName().contains("Porselen") || karacaUrunModel.getName().contains("porselen") || karacaUrunModel.getName().contains("PORSELEN") ||
                                karacaUrunModel.getName().contains("Fincan") || karacaUrunModel.getName().contains("FİNCAN") || karacaUrunModel.getName().contains("fincan")
                                || karacaUrunModel.getName().contains("Yemek Takımı") || karacaUrunModel.getName().contains("YEMEK TAKIMI")
                                || karacaUrunModel.getName().contains("YemekTakımı") || karacaUrunModel.getName().contains("YEMEKTAKIMI")) {
                            continue;
                        }
                        karacaUrunModel.setFiyat(Integer.valueOf(karacaUrunModel.getUnFormattedPrice().split("\\.")[0]));
                        karacaUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                        karacaUrunModel.setBirlikteAlKazanMi(birlikteAlKazanMi(karacaUrunModel.getBadgeList()));
                        karacaUrunModel.setTelegramChatId(linkModel.getTelegramChatId());
                        topluUrunList.add(karacaUrunModel);
                    }
                }
            }
            for (KaracaUrunModel karacaUrunModel : topluUrunList) {
                KaracaUrunModel eskiKaracaUrunModel = urunHashMap.get(karacaUrunModel.getProduct_id());
                if (eskiKaracaUrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (karacaUrunModel.getFiyat() < eskiKaracaUrunModel.getFiyat() * eskiKaracaUrunModel.getIndirimOrani() && !ilkTur) {
                        //İndirim
                        String mesaj = "" +
                                "İndirim%0A" +
                                "" + karacaUrunModel.getName() + "%0A" +
                                "Eski Fiyat: " + eskiKaracaUrunModel.getUnFormattedPrice() + "%0A" +
                                "Yeni Fiyat: " + karacaUrunModel.getUnFormattedPrice() + "%0A" +
                                "Birlikte Al Kazan:" + (eskiKaracaUrunModel.getBirlikteAlKazanMi() ? "Evet" : "Hayır") + ":::" + (karacaUrunModel.getBirlikteAlKazanMi() ? "Evet" : "Hayır") + "%0A" +
                                "Link:" + karacaUrunModel.getHref();
                        telegramMesajGonder(mesaj, "-" + karacaUrunModel.getTelegramChatId());
                    } else if (karacaUrunModel.getFiyat() > eskiKaracaUrunModel.getFiyat() && !ilkTur) {
                        //Zam
                        String mesaj = "" +
                                "Zam%0A" +
                                "" + karacaUrunModel.getName() + "%0A" +
                                "Eski Fiyat: " + eskiKaracaUrunModel.getUnFormattedPrice() + "%0A" +
                                "Yeni Fiyat: " + karacaUrunModel.getUnFormattedPrice() + "%0A" +
                                "Birlikte Al Kazan:" + (eskiKaracaUrunModel.getBirlikteAlKazanMi() ? "Evet" : "Hayır") + ":::" + (karacaUrunModel.getBirlikteAlKazanMi() ? "Evet" : "Hayır") + "%0A" +
                                "Link:" + karacaUrunModel.getHref();
                        telegramMesajGonder(mesaj, "-4157690423");//ZAM
                    } else if (!eskiKaracaUrunModel.getBirlikteAlKazanMi().equals(karacaUrunModel.getBirlikteAlKazanMi()) && !ilkTur) {
                        //Birlikte Al
                        String mesaj = "" +
                                "İndirim%0A" +
                                "" + karacaUrunModel.getName() + "%0A" +
                                "Fiyat: " + karacaUrunModel.getUnFormattedPrice() + "%0A" +
                                "Birlikte Al Kazan:" + (eskiKaracaUrunModel.getBirlikteAlKazanMi() ? "Evet" : "Hayır") + ":::" + (karacaUrunModel.getBirlikteAlKazanMi() ? "Evet" : "Hayır") + "%0A" +
                                "Link:" + karacaUrunModel.getHref();
                        telegramMesajGonder(mesaj, "-" + karacaUrunModel.getTelegramChatId());
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün%0A" +
                                "" + karacaUrunModel.getName() + "%0A" +
                                "Fiyat: " + karacaUrunModel.getUnFormattedPrice() + "%0A" +
                                "Birlikte Al Kazan:" + (karacaUrunModel.getBirlikteAlKazanMi() ? "Evet" : "Hayır") + "%0A" +
                                "Link:" + karacaUrunModel.getHref();
                        telegramMesajGonder(mesaj, "-" + karacaUrunModel.getTelegramChatId());
                    }
                }

                yeniUrunHashMap.put(karacaUrunModel.getProduct_id(), karacaUrunModel);
            }

            ilkTur = false;
            urunHashMap = yeniUrunHashMap;
            System.out.println("Karaca : " + urunHashMap.size() + " " + sdf.format(new Date()) + "\n");
        }

    }

    private void telegramMesajGonder(String mesaj, String chatId) throws IOException, InterruptedException {
        genelService.telegramMesajGonder(mesaj, chatId, "1", "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM");
    }

    public static KaracaGenelModel readJsonFromUrl(String url) throws IOException, JsonException {
        for (;;) {
            try {
                RandomString gen = new RandomString(8, ThreadLocalRandom.current());
                //url = url + "&" + gen.nextString() + "=" + gen.nextString();
                String json = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                        .referrer("http://www.google.com")
                        .timeout(12000)
                        .followRedirects(true)
                        .ignoreContentType(true)
                        .execute()
                        .body();
                ObjectMapper mapper = new ObjectMapper();
                KaracaGenelModel karacaGenelModel = mapper.readValue(json, KaracaGenelModel.class);
                return karacaGenelModel;
            } catch (Exception e) {
                System.out.println("Karaca : URL Hata : " + url);
            }
        }
    }


    private static Boolean birlikteAlKazanMi(List<String> badgeList) {
        for (String badge : badgeList) {
            if (badge.equals("Birlikte Al Kazan")) {
                return true;
            }
        }
        return false;
    }
}
