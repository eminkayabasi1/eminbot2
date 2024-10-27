package com.app.fku.n11.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.n11.fonksiyon.service.N11JSONService;
import com.app.fku.n11.model.N11UrunModel;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class N11JSONServiceImpl implements N11JSONService {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    private static final List<LinkModel> urlList = new ArrayList<>();

    public void sorgula() throws InterruptedException {
        urlList.add(new LinkModel("https://www.n11.com/searchCategoryForPagination/1000373?srt=PRICE_LOW&sellerId=4003098-183360&hdfl=cats_m_sellerId_ppf&pg=", 0.97d, "Elektrikli Ev Aletleri"));//Elektrikli Ev Aletleri
        urlList.add(new LinkModel("https://www.n11.com/searchCategoryForPagination/1001262?srt=PRICE_LOW&hdfl=cats_m_sellerId_ppf&sellerId=4003098-183360&pg=", 0.97d, "Mutfak Gereçleri"));//Mutfak Gereçleri
        urlList.add(new LinkModel("https://www.n11.com/searchCategoryForPagination/1000472?srt=PRICE_LOW&hdfl=cats_m_sellerId_ppf&sellerId=4003098&pg=", 0.97d, "Telefon Aksesuar"));//Telefon Aksesuar
        urlList.add(new LinkModel("https://www.n11.com/searchCategoryForPagination/1000210?srt=PRICE_LOW&hdfl=cats_m_sellerId_ppf&sellerId=4003098&pg=", 0.97d, "Bilgisayar"));//Bilgisayar
        for (;;) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("hata geldi n11");
            }
        }
    }

    private static void anaislem() throws InterruptedException, IOException {
        HashMap<String, N11UrunModel> urunHashMap = new HashMap<>();

        boolean ilkTur = true;
        for (;;) {

            List<N11UrunModel> topluUrunList = new ArrayList<>();
            for (LinkModel linkModel: urlList) {
                Document doc = readJsonFromUrl(linkModel.getUrl() + "1");
                Integer piCount = Integer.valueOf(doc.getElementById("listingInfiniteScrollResult").getElementsByClass("columnContent").get(0).attributes().get("data-searchcount")) / 24;
                piCount = piCount + 1;

                Elements urunElements = doc.getElementById("listingInfiniteScrollResult").getElementsByClass("columnContent");

                for (Element urunElement: urunElements) {
                    N11UrunModel n11UrunModel = new N11UrunModel();
                    if (!urunElement.getElementsByClass("sellerNickName").get(0).attributes().get("value").equals("n11") && !urunElement.getElementsByClass("sellerNickName").get(0).attributes().get("value").equals("Tefal")) {
                        continue;
                    }
                    n11UrunModel.setUrl(urunElement.getElementsByClass("plink").get(0).attributes().get("href"));
                    n11UrunModel.setUrunId(urunElement.getElementsByClass("plink").get(0).attributes().get("data-id"));
                    n11UrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("newPrice").get(0).childNodes().get(1).childNodes().get(0).toString().replace(".", "").split(" ")[0].split(",")[0]));
                    n11UrunModel.setUrunAdi(urunElement.getElementsByClass("productName").get(0).childNodes().get(0).toString());
                    n11UrunModel.setSatici(urunElement.getElementsByClass("sellerNickName").get(0).attributes().get("value"));
                    n11UrunModel.setKategoriAdi(linkModel.getKategoriAdi());
                    n11UrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                    topluUrunList.add(n11UrunModel);
                }

                for (int i = 2; i <= piCount; i++) {
                    try {
                        doc = readJsonFromUrl(linkModel.getUrl() + i);
                        urunElements = doc.getElementById("listingInfiniteScrollResult").getElementsByClass("columnContent");
                        for (Element urunElement: urunElements) {
                            if (!urunElement.getElementsByClass("sellerNickName").get(0).attributes().get("value").equals("n11") && !urunElement.getElementsByClass("sellerNickName").get(0).attributes().get("value").equals("Tefal")) {
                                continue;
                            }
                            N11UrunModel n11UrunModel = new N11UrunModel();
                            n11UrunModel.setUrl(urunElement.getElementsByClass("plink").get(0).attributes().get("href"));
                            n11UrunModel.setUrunId(urunElement.getElementsByClass("plink").get(0).attributes().get("data-id"));
                            n11UrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("newPrice").get(0).childNodes().get(1).childNodes().get(0).toString().replace(".", "").split(" ")[0].split(",")[0]));
                            n11UrunModel.setUrunAdi(urunElement.getElementsByClass("productName").get(0).childNodes().get(0).toString());
                            n11UrunModel.setSatici(urunElement.getElementsByClass("sellerNickName").get(0).attributes().get("value"));
                            n11UrunModel.setKategoriAdi(linkModel.getKategoriAdi());
                            n11UrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                            topluUrunList.add(n11UrunModel);
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
            }

            for (N11UrunModel n11UrunModel: topluUrunList) {
                N11UrunModel eskiN11UrunModel = urunHashMap.get(n11UrunModel.getUrunId());
                if (eskiN11UrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (n11UrunModel.getFiyat() < eskiN11UrunModel.getFiyat() * n11UrunModel.getIndirimOrani() && !ilkTur) {
                        String mesaj = "" +
                                "İndirim%0A" +
                                "" + n11UrunModel.getKategoriAdi() + "%0A" +
                                "" + n11UrunModel.getUrunAdi() + "%0A" +
                                "Eski Fiyat: " + eskiN11UrunModel.getFiyat() + "%0A" +
                                "Yeni Fiyat: " + n11UrunModel.getFiyat() + "%0A" +
                                "Satici: " + n11UrunModel.getSatici() + "%0A" +
                                "Link:" + n11UrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4105197987");
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün%0A" +
                                "" + n11UrunModel.getKategoriAdi() + "%0A" +
                                "" + n11UrunModel.getUrunAdi() + "%0A" +
                                "Fiyat: " + n11UrunModel.getFiyat() + "%0A" +
                                "Satici: " + n11UrunModel.getSatici() + "%0A" +
                                "Link:" + n11UrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4105197987");
                    }
                }

                urunHashMap.put(n11UrunModel.getUrunId(), n11UrunModel);
            }

            ilkTur = false;
            System.out.println("N11: " + urunHashMap.size() + "\n");
        }
    }

    public static Document readJsonFromUrl(String url) throws IOException, JSONException {
        RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        url = url + "&" + gen.nextString() + "=" + gen.nextString();

        return Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                .referrer("http://www.google.com")
                .timeout(12000)
                .followRedirects(true)
                .ignoreContentType(true)
                .get();
    }


    public static void telegramMesajGonder(String mesaj, String chatId) throws IOException, InterruptedException {
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
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";    //Add Telegram token//
        String token = "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM";
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
