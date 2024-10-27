package com.app.fku.vatan.fonksiyon.impl;

import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
import com.app.fku.vatan.fonksiyon.service.VatanService;
import com.app.fku.vatan.model.VatanUrunModel;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
public class VatanServiceImpl implements VatanService {

    private static final List<LinkModel> urlList = new ArrayList<>();

    public void sorgula() throws InterruptedException {
        urlList.add(new LinkModel("https://www.vatanbilgisayar.com/elektrikli-ev-aletleri/?stk=true&srt=PU&page=", 0.97d, "Elektrikli Ev Aletleri"));//Elektrikli Ev Aletleri
        urlList.add(new LinkModel("https://www.vatanbilgisayar.com/kisisel-bakim-urunleri/?stk=true&srt=PU&page=", 0.97d, "Kişisel Bakım"));//Kişisel Bakım
        urlList.add(new LinkModel("https://www.vatanbilgisayar.com/mutfak-urunleri/?srt=PU&stk=true&page=", 0.97d, "Mutfak Ürünleri"));//Mutfak Ürünleri
        urlList.add(new LinkModel("https://www.vatanbilgisayar.com/cep-telefonu-modelleri/?stk=true&srt=PU&page=", 0.97d, "Cep Telefonu"));//Cep Telefonu
        for (;;) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("hata geldi vatan");
            }
        }
    }

    private static void anaislem() throws InterruptedException, IOException {
        HashMap<String, VatanUrunModel> urunHashMap = new HashMap<>();

        boolean ilkTur = true;
        for (;;) {

            List<VatanUrunModel> topluUrunList = new ArrayList<>();
            for (LinkModel linkModel: urlList) {
                Document doc = readJsonFromUrl(linkModel.getUrl() + "1");
                Integer piCount = Integer.valueOf(doc.getElementsByClass("wrapper-detailpage-header__text").get(0).childNodes().get(1).childNodes().get(2).childNodes().get(0).toString()) / 24;
                piCount = piCount + 1;

                Elements urunElements = doc.getElementById("productsLoad").getElementsByClass("product-list");

                for (Element urunElement: urunElements) {
                    VatanUrunModel vatanUrunModel = new VatanUrunModel();

                    vatanUrunModel.setUrl("https://www.vatanbilgisayar.com" + urunElement.getElementsByClass("product-list__link").get(0).attributes().get("href"));
                    vatanUrunModel.setUrunId(urunElement.getElementsByClass("product-list__link").get(0).attributes().get("href"));
                    if (urunElement.getElementsByClass("price-basket-camp").size() > 0) {
                        //geceye özel
                        vatanUrunModel.setGeceyeOzelMi(true);
                        vatanUrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("price-basket-camp").get(0).getElementById("priceHelper").childNodes().get(0).toString().trim().split(" ")[0].replace(".", "")));
                    } else {
                        vatanUrunModel.setGeceyeOzelMi(false);
                        vatanUrunModel.setFiyat(Long.valueOf(urunElements.get(0).getElementsByClass("product-list__price").get(0).childNodes().get(0).toString().trim().replace(".", "")));
                    }

                    vatanUrunModel.setUrunAdi(urunElement.getElementsByClass("product-list__product-name").get(0).childNodes().get(1).childNodes().get(0).toString());
                    vatanUrunModel.setKategoriAdi(linkModel.getKategoriAdi());
                    vatanUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                    topluUrunList.add(vatanUrunModel);
                }

                for (int i = 2; i <= piCount; i++) {
                    doc = readJsonFromUrl(linkModel.getUrl() + i);
                    urunElements = doc.getElementById("productsLoad").getElementsByClass("product-list");

                    for (Element urunElement: urunElements) {
                        VatanUrunModel vatanUrunModel = new VatanUrunModel();

                        vatanUrunModel.setUrl("https://www.vatanbilgisayar.com" + urunElement.getElementsByClass("product-list__link").get(0).attributes().get("href"));
                        vatanUrunModel.setUrunId(urunElement.getElementsByClass("product-list__link").get(0).attributes().get("href"));
                        if (urunElement.getElementsByClass("price-basket-camp").size() > 0) {
                            //geceye özel
                            vatanUrunModel.setGeceyeOzelMi(true);
                            vatanUrunModel.setFiyat(Long.valueOf(urunElement.getElementsByClass("price-basket-camp").get(0).getElementById("priceHelper").childNodes().get(0).toString().trim().split(" ")[0].replace(".", "")));
                        } else {
                            vatanUrunModel.setGeceyeOzelMi(false);
                            vatanUrunModel.setFiyat(Long.valueOf(urunElements.get(0).getElementsByClass("product-list__price").get(0).childNodes().get(0).toString().trim().replace(".", "")));
                        }

                        vatanUrunModel.setUrunAdi(urunElement.getElementsByClass("product-list__product-name").get(0).childNodes().get(1).childNodes().get(0).toString());
                        vatanUrunModel.setKategoriAdi(linkModel.getKategoriAdi());
                        vatanUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                        topluUrunList.add(vatanUrunModel);
                    }
                }
            }

            for (VatanUrunModel vatanUrunModel: topluUrunList) {
                VatanUrunModel eskiVatanUrunModel = urunHashMap.get(vatanUrunModel.getUrunId());
                if (eskiVatanUrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (vatanUrunModel.getFiyat() < eskiVatanUrunModel.getFiyat() * vatanUrunModel.getIndirimOrani() && !ilkTur) {
                        String mesaj = "" +
                                "İndirim%0A" +
                                "" + vatanUrunModel.getKategoriAdi() + "%0A" +
                                "" + vatanUrunModel.getUrunAdi() + "%0A" +
                                "Eski Fiyat: " + eskiVatanUrunModel.getFiyat() + "%0A" +
                                "Yeni Fiyat: " + vatanUrunModel.getFiyat() + "%0A" +
                                "Gece Fırsatı Mı: " + (vatanUrunModel.getGeceyeOzelMi() ? "Evet" : "Hayır") + "%0A" +
                                "Link:" + vatanUrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4172489698");
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün%0A" +
                                "" + vatanUrunModel.getKategoriAdi() + "%0A" +
                                "" + vatanUrunModel.getUrunAdi() + "%0A" +
                                "Fiyat: " + vatanUrunModel.getFiyat() + "%0A" +
                                "Gece Fırsatı Mı: " + (vatanUrunModel.getGeceyeOzelMi() ? "Evet" : "Hayır") + "%0A" +
                                "Link:" + vatanUrunModel.getUrl();

                        telegramMesajGonder(mesaj, "-4172489698");
                    }
                }

                urunHashMap.put(vatanUrunModel.getUrunId(), vatanUrunModel);
            }

            ilkTur = false;
            System.out.println("Vatan: " + urunHashMap.size() + "\n");
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
