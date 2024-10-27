package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.LinkModel;
import com.app.fku.genel.utils.RandomString;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaJSONService;
import com.app.fku.hepsiburada.model.HbGenelModel;
import com.app.fku.hepsiburada.model.HbnUrunModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
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
public class HepsiBuradaSepetJSONServiceImpl implements HepsiBuradaJSONService {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    private final List<LinkModel> urlList = new ArrayList<>();

    @Override
    public void sorgula() throws IOException, InterruptedException {
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/9f299a7e-1dd1-4b36-a873-ddb0311263a5/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/6d15bcb0-5f0f-446b-9511-0e456462d9aa/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/63b5b683-d161-407e-b362-56a18de5a4c3/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/00d4db4b-c52a-4b51-82c4-5e88a55dad0b/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/569dd83b-f85a-4406-a956-0f4fb9598828/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/08ad584c-9186-4bb0-8d0d-c5f8867cbbb0/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/ed4a5029-dd81-43e9-bede-ebb7a10a74f8/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/1313325a-785c-4f8b-9ca6-efd55de6cbfc/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/98f59e2c-2484-4c65-b1f9-d84c7d1ae825/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/9bdf41fa-4fdc-46b9-9838-c4bd3071f492/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/2a3ca783-1308-49c5-8d7b-19237dab3578/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/532ffbd0-9b44-4d82-bb0a-f9e17594f454/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/4e3ff575-c5d9-4767-af62-153ac03ed819/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/73d101d7-e8b7-461c-8522-c54b9cf2fbcb/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/62436e89-1206-49bd-970b-01de964b7896/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/0a20a7a1-9e27-4946-ab82-51a58370509e/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/857b84c5-b9b5-4a18-bcd1-ee961f566ae0/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/03bb71c6-01da-43b5-8d9f-662f64e6ef4b/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/753d83a9-9094-4f6a-bfcd-eec96b2e1400/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/78bb9501-889e-4fd4-9ac5-740339ac0669/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/43de47d5-21bd-4bab-b9f1-39d1f5580533/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/de4d1ece-5054-44f8-8132-db4292c7a33c/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/91e661bd-e778-4561-99fe-b74f9ec4f7f4/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/fa44d76a-1723-439b-8a7c-4a0d85fe594f/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/077b6d95-641f-437a-8eaf-70084df1015f/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/cfdf2f75-b730-4b1c-9507-c7ffab9f06ac/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/5a296c12-221a-4ca6-b38f-1ace74952673/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/08d657f7-38ef-4249-8468-199407d45652/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/44fab581-dcf4-462a-9e87-42f01cc5e4ab/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/e3b7bde9-69d5-4a21-b36a-be8bd05ad34e/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/ba635f55-34f5-43e2-9a10-b8cbf2059653/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/db41d43f-1a91-46bd-b2f0-a29b479111e1/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/520637fa-0a3f-49a9-842c-43f1fa3bde72/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/2f7826ee-ede8-4f55-8bf9-fb89c1fde158/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/84768d76-5570-4b0a-b266-ef66797ab453/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/c63dfb3f-c322-476b-814c-d2043d7b9da2/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/da544e7a-eeee-47e2-a882-bfe7db0c2e2a/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/8a0c40ea-4ffe-476d-8299-8dfbd50dbb6d/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/171db4bf-3224-4bad-957c-11e53ac72edc/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/89bfbbae-c82e-4327-9633-47b3f9acfcad/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/478ccd2b-d731-434f-b0eb-b471a7603309/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/e617a070-c3cb-4d6e-9ab1-120bbee50636/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/d3a98d08-8853-4330-b4a3-0d9da3ed3bd4/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/21752dd7-8a0c-49c9-a362-8e9250d05183/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/1204982c-da7f-4497-b957-b841e18ba1fc/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/b611c61e-073e-4422-a28c-aca61299895a/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/8c1d55aa-49f6-4a9b-ba1f-90dd58e255bc/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/4c9428d0-033b-41f9-a10a-a7b04c066eb1/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/49efec4d-8c5d-4add-9639-21d8a91f8f55/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/be127e4d-7627-442e-868f-9647cc5431c5/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/cabedcc2-9c2b-427c-91fe-2e0c5311132d/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/6a633304-ca0f-44eb-b8e7-a7048676ec02/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/95b6a5ae-fdf4-41be-803e-76d8f4a58751/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/de4d1ece-5054-44f8-8132-db4292c7a33c/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/3cfe2388-44e3-4d7b-91f6-8c5db699d379/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/7137ab4b-a7c8-4b69-996e-0cacc01f6b19/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/169c2366-e579-4ff2-84f0-db24a6113133/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/07495658-029d-4c61-b509-b8e4a6931642/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/43762d22-baf9-4b92-88c5-6c96f9015856/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/70bcd8e3-b1ec-421b-a3bd-9d6ff10fb77b/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/163f308e-278f-488a-b31f-d1b0295af644/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/22f5ee1f-d2bd-46b1-a2c5-611f6cbb7aa4/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/95e7ae23-72f5-4a94-959a-a28fd41fef60/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/86421a68-b7dc-4eb7-95f9-d07c81711baa/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));
        urlList.add(new LinkModel("https://listelerim-gw.hepsiburada.com/mylist-api/v1/shared-list/03fc6002-a7d8-4f9b-ab3b-077bb7e83d12/items?limit=800&offset=0&sortId=0&filter=InStock", 0.97d));


        for (; ; ) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("hata geldi hb beğeni");
            }
        }
    }

    private void anaislem() throws InterruptedException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        HashMap<String, HbnUrunModel> urunHashMap = new HashMap<>();
        boolean ilkTur = true;
        for (; ; ) {
            List<HbnUrunModel> topluUrunList = new ArrayList<>();
            HashMap<String, HbnUrunModel> yeniUrunHashMap = new HashMap<>();
            for (LinkModel linkModel : urlList) {
                List<HbnUrunModel> hbUrunModelList = readJsonFromUrl(linkModel.getUrl());
                for (HbnUrunModel hbUrunModel : hbUrunModelList) {
                    if (hbUrunModel.getFinalPriceOnSale() == 0) {
                        continue;
                    }
                    if (hbUrunModel.getMerchantName() != null) {
                        if (hbUrunModel.getMerchantName().equals("Hepsiburada") ||
                                hbUrunModel.getMerchantName().equals("Tefal - Rowenta - WMF Türkiye") ||
                                //hbUrunModel.getMerchantName().equals("Karaca") ||
                                hbUrunModel.getMerchantName().equals("Dyson Türkiye") ||
                                hbUrunModel.getMerchantName().equals("Atlas Bilişim")) {

                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                    hbUrunModel.setIndirimOrani(linkModel.getIndirimOrani());
                    hbUrunModel.setTelegramChatId(linkModel.getTelegramChatId());
                    topluUrunList.add(hbUrunModel);
                }
            }

            for (HbnUrunModel hbUrunModel : topluUrunList) {
                HbnUrunModel eskiHbUrunModel = urunHashMap.get(hbUrunModel.getSku());
                if (eskiHbUrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (hbUrunModel.getFinalPriceOnSale() < eskiHbUrunModel.getFinalPriceOnSale() * eskiHbUrunModel.getIndirimOrani() && !ilkTur) {
                        String mesaj = "" +
                                "İndirim%0A" +
                                "" + hbUrunModel.getCatalogName() + " - " + hbUrunModel.getBrand() + "%0A" +
                                "" + hbUrunModel.getName() + "%0A" +
                                "Eski Fiyat: " + eskiHbUrunModel.getFinalPriceOnSale() + "%0A" +
                                "Yeni Fiyat: " + hbUrunModel.getFinalPriceOnSale() + "%0A" +
                                "Satıcı: " + eskiHbUrunModel.getMerchantName() + " - " + hbUrunModel.getMerchantName() + "%0A" +
                                "Link:" + hbUrunModel.getUrl();
                        if (hbUrunModel.getTelegramChatId() != null) {
                            telegramMesajGonder(mesaj, "-" + hbUrunModel.getTelegramChatId());
                        } else {
                            telegramMesajGonder(mesaj, "-4129223336");
                            telegramMesajGonder(mesaj, "-4547587594");
                        }
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün%0A" +
                                "" + hbUrunModel.getCatalogName() + " - " + hbUrunModel.getBrand() + "%0A" +
                                "" + hbUrunModel.getName() + "%0A" +
                                "Fiyat Fiyat: " + hbUrunModel.getFinalPriceOnSale() + "%0A" +
                                "Satıcı: " + hbUrunModel.getMerchantName() + "%0A" +
                                "Link:" + hbUrunModel.getUrl();
                        if (hbUrunModel.getTelegramChatId() != null) {
                            telegramMesajGonder(mesaj, "-" + hbUrunModel.getTelegramChatId());
                        } else {
                            telegramMesajGonder(mesaj, "-4129223336");
                            telegramMesajGonder(mesaj, "-4547587594");
                        }
                    }
                }

                yeniUrunHashMap.put(hbUrunModel.getSku(), hbUrunModel);
            }

            ilkTur = false;
            urunHashMap = yeniUrunHashMap;
            System.out.println("HB: " + urunHashMap.size() + " : " + sdf.format(new Date()) + "\n");
        }
    }

    private void telegramMesajGonder(String mesaj, String chatId) throws IOException, InterruptedException {
        genelService.telegramMesajGonder(mesaj, chatId, "1", "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM");
    }

    public static List<HbnUrunModel> readJsonFromUrl(String url) throws IOException, JsonException {
        //RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        //url = url + "&" + gen.nextString() + "=" + gen.nextString();

        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36");
        headerMap.put("sec-ch-ua", "\"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Brave\";v=\"128\"");
        headerMap.put("Accept", "*/*");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Connection", "keep-alive");

        String json =
                Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                        .method(Connection.Method.GET)
                        .headers(headerMap)
                        .ignoreContentType(true)
                        .followRedirects(true)
                        .ignoreHttpErrors(true)
                        //.timeout(3000)
                        .execute().body();
        ObjectMapper mapper = new ObjectMapper();
        HbGenelModel hbGenelModel = new HbGenelModel();
        hbGenelModel = mapper.readValue(json, HbGenelModel.class);
        return hbGenelModel.getItems();
    }
}
