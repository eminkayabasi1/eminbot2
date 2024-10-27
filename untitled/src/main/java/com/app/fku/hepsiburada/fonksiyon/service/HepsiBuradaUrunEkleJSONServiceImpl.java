package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaSepetUrunEkleJSONService;
import com.app.fku.hepsiburada.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.openqa.selenium.json.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HepsiBuradaUrunEkleJSONServiceImpl implements HepsiBuradaSepetUrunEkleJSONService {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    private static final HashMap<String, HbTokenModel> hbTokenHashMap = new HashMap<>();
    private static final List<HbRequestMetadataModel> hbRequestMetadataModelList = new ArrayList<>();

    @Override
    public void sorgula() throws IOException, InterruptedException {
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzAwMzg1MDYsImV4cCI6MTczMDI5NzcwNiwiaWF0IjoxNzMwMDM4NTA2LCJVc2VySWQiOiIyOWU0ZGVkNi1kMzUxLTRmZGItOTA3Mi1kMmEwMTM1ZDBjZDIiLCJUaXRsZSI6IkVtaW4gS2F5YWJhc2kiLCJGaXJzdE5hbWUiOiJFbWluIiwiTGFzdE5hbWUiOiJLYXlhYmFzaSIsIkVtYWlsIjoiZW1pbmt5YnNAaWNsb3VkLmNvbSIsIklzQXV0aGVudGljYXRlZCI6IlRydWUiLCJBcHBLZXkiOiJBRjdGMkEzNy1DQzRCLTRGMUMtODdGRC1GRjM2NDJGNjdFQ0IiLCJQcm92aWRlciI6ImFwcGxlIiwiU2hhcmVEYXRhUGVybWlzc2lvbiI6IlRydWUiLCJwIjp7InQiOltdfX0.o7YqeR4uzq9d_wkxBQICgT1RFuM_e_81Fi_ZuCCZgn4",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzAwMzg1MDYsImV4cCI6MTczMDI5NzcwNiwiaWF0IjoxNzMwMDM4NTA2LCJVc2VySWQiOiIyOWU0ZGVkNi1kMzUxLTRmZGItOTA3Mi1kMmEwMTM1ZDBjZDIiLCJUaXRsZSI6IkVtaW4gS2F5YWJhc2kiLCJGaXJzdE5hbWUiOiJFbWluIiwiTGFzdE5hbWUiOiJLYXlhYmFzaSIsIkVtYWlsIjoiZW1pbmt5YnNAaWNsb3VkLmNvbSIsIklzQXV0aGVudGljYXRlZCI6IlRydWUiLCJBcHBLZXkiOiJBRjdGMkEzNy1DQzRCLTRGMUMtODdGRC1GRjM2NDJGNjdFQ0IiLCJQcm92aWRlciI6ImFwcGxlIiwiU2hhcmVEYXRhUGVybWlzc2lvbiI6IlRydWUiLCJwIjp7InQiOltdfX0.o7YqeR4uzq9d_wkxBQICgT1RFuM_e_81Fi_ZuCCZgn4"));//eminkybs@icloud.com - Ac102030
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzAwMzg2MTQsImV4cCI6MTczMDI5NzgxNCwiaWF0IjoxNzMwMDM4NjE0LCJVc2VySWQiOiIxYmRlNWI0Zi05MTAxLTQ3ZDMtYjBjOS1kYzM3OGQ3ZTZjOWUiLCJUaXRsZSI6IkVtaW4gS2F5YWJhxZ_EsSIsIkZpcnN0TmFtZSI6IkVtaW4iLCJMYXN0TmFtZSI6IktheWFiYcWfxLEiLCJFbWFpbCI6InRibXZzbnRyOW1AcHJpdmF0ZXJlbGF5LmFwcGxlaWQuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiYXBwbGUiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsInAiOnsidCI6W119fQ.jkqqmq8_MsnociGxsFFmCVZi2-kB8BPgoFIyKDIMYMo",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzAwMzg2MTQsImV4cCI6MTczMDI5NzgxNCwiaWF0IjoxNzMwMDM4NjE0LCJVc2VySWQiOiIxYmRlNWI0Zi05MTAxLTQ3ZDMtYjBjOS1kYzM3OGQ3ZTZjOWUiLCJUaXRsZSI6IkVtaW4gS2F5YWJhxZ_EsSIsIkZpcnN0TmFtZSI6IkVtaW4iLCJMYXN0TmFtZSI6IktheWFiYcWfxLEiLCJFbWFpbCI6InRibXZzbnRyOW1AcHJpdmF0ZXJlbGF5LmFwcGxlaWQuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiYXBwbGUiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsInAiOnsidCI6W119fQ.jkqqmq8_MsnociGxsFFmCVZi2-kB8BPgoFIyKDIMYMo"));//emin.bot.1@atlaspsota.com - Ac102030
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzAwMzg2ODksImV4cCI6MTczMDI5Nzg4OSwiaWF0IjoxNzMwMDM4Njg5LCJVc2VySWQiOiJlMTE2MzgzNy00ZDdiLTQ1MzgtODYyNS1jMjc5NjgxNTdmMGEiLCJUaXRsZSI6IkVtaW4gS2F5YWJhxZ_EsSIsIkZpcnN0TmFtZSI6IkVtaW4iLCJMYXN0TmFtZSI6IktheWFiYcWfxLEiLCJFbWFpbCI6Imh4NjRiMnBnaDdAcHJpdmF0ZXJlbGF5LmFwcGxlaWQuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiYXBwbGUiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsInAiOnsidCI6W119fQ.v9tzIRdUH1uaSb7pLM1lGjuwwrU4enHvNM4tSBmX0-0",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzAwMzg2ODksImV4cCI6MTczMDI5Nzg4OSwiaWF0IjoxNzMwMDM4Njg5LCJVc2VySWQiOiJlMTE2MzgzNy00ZDdiLTQ1MzgtODYyNS1jMjc5NjgxNTdmMGEiLCJUaXRsZSI6IkVtaW4gS2F5YWJhxZ_EsSIsIkZpcnN0TmFtZSI6IkVtaW4iLCJMYXN0TmFtZSI6IktheWFiYcWfxLEiLCJFbWFpbCI6Imh4NjRiMnBnaDdAcHJpdmF0ZXJlbGF5LmFwcGxlaWQuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiYXBwbGUiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsInAiOnsidCI6W119fQ.v9tzIRdUH1uaSb7pLM1lGjuwwrU4enHvNM4tSBmX0-0"));//emin.bot.2@atlasposta.com - Ac102030

        urunHashMapDoldur();
        for (; ; ) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("HBSepetEkleme hata geldi");
            }
        }
    }

    private void anaislem() throws InterruptedException, IOException {
        HashMap<String, HbSepetUrunModel> urunHashMap = new HashMap<>();

        for (;;) {
            List<HbSepetUrunModel> topluUrunList = new ArrayList<>();

            HashMap<String, HbSepetUrunModel> yeniUrunHashMap = new HashMap<>();
            for (HbTokenModel hbTokenModel: hbTokenHashMap.values()) {
                int urunSayisi = 0;
                List<HbSepetUrunModel> hbSepetUrunModelList = readJsonFromUrl(hbTokenModel);
                for (HbSepetUrunModel hbSepetUrunModel : hbSepetUrunModelList) {
                    if (hbSepetUrunModel.getPrice() == null || hbSepetUrunModel.getPrice().getAmount() == null || hbSepetUrunModel.getPrice().getAmount() == 0) {
                        continue;
                    }
                    urunSayisi++;
                    topluUrunList.add(hbSepetUrunModel);
                    yeniUrunHashMap.put(hbSepetUrunModel.getProduct().getSku(), hbSepetUrunModel);
                }
                hbTokenModel.setUrunSayisi(urunSayisi);
                hbTokenHashMap.put(hbTokenModel.getBearerTokent(), hbTokenModel);
            }

            urunHashMap = yeniUrunHashMap;

            for (HbRequestMetadataModel hbRequestMetadataModel: hbRequestMetadataModelList) {
                HbSepetUrunModel hbSepetUrunModel = urunHashMap.get(hbRequestMetadataModel.getSku());
                if (hbSepetUrunModel != null) {
                    //ürün sepette var
                } else {
                    //ürün sepette yok ekle
                    for (HbTokenModel hbTokenModel: hbTokenHashMap.values()) {
                        if (hbTokenModel.getUrunSayisi() < 49) {
                            sepeteEkle(hbTokenModel, hbRequestMetadataModel);
                            hbTokenModel.setUrunSayisi(hbTokenModel.getUrunSayisi() + 1);
                            hbTokenHashMap.put(hbTokenModel.getBearerTokent(), hbTokenModel);
                            break;
                        }
                    }
                }
            }
        }
    }

    public List<HbSepetUrunModel> readJsonFromUrl(HbTokenModel hbTokenModel) throws IOException, JsonException {

        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + hbTokenModel.getBearerTokent());
        headerMap.put("client-id", hbTokenModel.getClientId());
        headerMap.put("tenant-id", hbTokenModel.getTenantId());
        headerMap.put("Content-Type", "application/json; charset=UTF-8");
        headerMap.put("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Mobile Safari/537.36");
        String json =
                Jsoup
                        .connect("https://checkout.hepsiburada.com/api/basket/all")
                        //.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                        .headers(headerMap)
                        .ignoreContentType(true).execute().body();
        ObjectMapper mapper = new ObjectMapper();
        HbSepetGenel1Model hbSepetGenel1Model = mapper.readValue(json, HbSepetGenel1Model.class);
        return hbSepetGenel1Model.getResult().getBasket().getBasketItems();
    }

    public void sepeteEkle(HbTokenModel hbTokenModel, HbRequestMetadataModel metadata) throws IOException, JSONException {

        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + hbTokenModel.getBearerTokent());
        headerMap.put("client-Id", hbTokenModel.getClientId());
        headerMap.put("tenant-Id", hbTokenModel.getTenantId());
        headerMap.put("content-type", "application/json; charset=UTF-8");
        headerMap.put("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Mobile Safari/537.36");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        HbRequestProductModel product = new HbRequestProductModel();
        product.setMetadata(metadata);
        product.setQuantity("1");

        HbRequestGenelModel genelModel = new HbRequestGenelModel();
        genelModel.setProduct(product);

        String jsonStr = ow.writeValueAsString(genelModel);

        try {
            Jsoup
                    .connect("https://checkout.hepsiburada.com/api/basket")
                    //.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                    .headers(headerMap)
                    .method(Connection.Method.PUT)
                    .requestBody(jsonStr)
                    .ignoreContentType(true).execute().body();
        } catch (Exception e) {
            System.out.println(metadata.getSku() + " sepete eklenemedi.");
        }
    }

    public static void urunHashMapDoldur() {
        //Philips Kahve START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000N8NFY", "ca7a4cd6-ced4-477e-859d-dc4d1c3a74c5"));//Philips EP2220
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000N8NG0", "a213e9a1-29ae-466b-a86c-33b80a58072e"));//Philips EP2231
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000VF9XU", "99d7d243-46b5-4593-a25f-2e1798ad37a4"));//Philips EP3246
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000XGK5R", "65d78e3c-42dd-4a27-a9f6-33b96aeeeedd"));//Philips EP4346
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00000PN2T3", "e423b77f-2ed7-4a0e-b17c-c918764473ed"));//Philips EP5443
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00001AZ53L", "4ec3857c-acdc-4a21-9346-3b411e2fc837"));//Philips EP5447

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003GJJF7", "495733b4-de0a-491f-9c2f-979121d662de"));//Philips HD5120
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000JAG9Y", "a83b75ed-fdf0-4763-b47a-ddfe7bb779e7"));//Philips HD7459
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000CQNN5", "bc26aefa-e4e8-4d1e-a204-ed818a1bc510"));//Philips HD7461
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("MTPHILIPHD7462", "f11069a4-46db-415f-bcde-fd2cde811d70"));//Philips HD7462
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000052OLJE", "2ddffda7-593a-4cb9-9240-f07b5da25313"));//Philips HD7888

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00005AEESN", "7c5e1666-a867-4738-a16c-13387c71f004"));//Philips HDA150/60
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00005B5MP0", "9e4f8426-6af0-4479-853d-187724a78de7"));//Philips HDA150/61
        //Philips Kahve END

        //Philips Fritöz START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004OVGX2", "6c3322d3-17ba-43b3-8e84-314f1a0a4e65"));//Philips HD9243
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV0000117MUS", "96204be1-b742-442c-bd9d-fe5f47808d60"));//Philips HD9252
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004T1T40", "17295134-ecd4-4fbe-b321-b0847aa724a6"));//Philips HD9257
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000040CBJZ", "30613eaf-1206-44de-bef4-64136e7e41db"));//Philips HD9285
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000DGBRH", "6fca41cc-1cdb-424a-a4ea-3ed689739601"));//Philips HD9650
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000004VGKW", "b51b452f-c191-4a6d-ab4b-8800d55bfe15"));//Philips HD9867
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00002NQJ3X", "09782066-0e80-4d24-b1cb-03c28ced1caf"));//Philips HD9870
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004BGZ13", "d8f461ee-7ad1-446f-817f-51957ae667dc"));//Philips HD9880

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00002WS30Q", "c93c9bdf-efda-449d-8cc9-2a75b362518c"));//Philips HD9951
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000036S4YU", "6d8f5484-d066-44af-9fa9-b57bead937b1"));//Philips HD9952
        //Philips Fritöz END

        //Philips Torbasız Süpürge START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000DTSPJ", "b6f9e75b-9ed0-4a57-a4da-fe1e4fe1606d"));//Philips FC8781
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000DTSPL", "66d44e5d-45fa-4366-a73e-009c8cc4e4c3"));//Philips FC8785
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV000007GCWH", "d4da93db-821b-42e8-b84f-b95328dcd428"));//Philips FC9331
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000021FQV8", "9e699372-7eb5-43b9-abc5-68c3775d4e2e"));//Philips FC9749

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003QSE4E", "4cebb6ba-2e62-4494-aada-da3f75b99dfe"));//Philips XB2123
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004K3SLH", "763b8fea-2a58-47fb-85dd-5e2460268648"));//Philips XB7150
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004K3RVB", "90272934-7c06-4e2b-80f4-21b5426c214a"));//Philips XB7151
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000O0KAZ", "c15451ff-c53c-4250-ba5e-692c884f14c3"));//Philips XB9125
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000O0KBH", "505349fd-9daa-49e2-8b83-b58053ea3ead"));//Philips XB9155
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000O0KBR", "53fa6aca-879d-4ae4-b8ab-4619c0a54a4f"));//Philips XB9185
        //Philips Torbasız Süpürge END

        //Philips Dik Süpürge START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004BGZ15", "ea18b76c-a87d-4f22-b576-15832251d22d"));//Philips XC7040/01
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV000011NVZA", "8363ea82-f351-41df-9b49-b8dd0da92af3"));//Philips XC7043/01
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV0000077CI3", "9ca1a842-d7ce-4e62-ab60-06f9b1797634"));//Philips XC8049/01
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000052OKP0", "28f513a3-55b4-4c52-9a33-fa3ef42eb32f"));//Philips XC8057/01
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV0000246S9O", "a43b0292-c4d2-4a28-8f1f-b7b4bc236e49"));//Philips XC8349/01

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000052OLEH", "05fbf39b-6205-4b5b-9405-a9496567d650"));//Philips XW7110/01
        //Philips Dik Süpürge END

        //Philips Robot Süpürge START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000049CDAO", "d58189b6-b1e3-4278-8eca-0783adb764d3"));//Philips XU3000/01
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004A9Q4V", "e5fda4df-4305-420f-b1eb-bb5a015dafa4"));//Philips XU3000/02
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004A3HC8", "37a5dcb5-4bc5-4512-890a-6a041a1f0cea"));//Philips XU3110/02
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000044HDI7", "806f88cb-6327-43a7-a559-4a4e46d703e1"));//Philips XU7000/01
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000044HDI5", "94407476-72ce-4cfa-9f70-85c1d8659c78"));//Philips XU7000/02
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000044HDI9", "946d0a7d-a89c-4347-a9a1-522c35dda96a"));//Philips XU7100/02
        //Philips Robot Süpürge END

        //Philips Epilasyon START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000IGHRC", "c6824214-6db6-46fe-831a-bf2fbc67f926"));//Philips BRE225/05
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000IGHRG", "36dd080e-1ef8-42f2-a9c5-245b1ad92afb"));//Philips BRE245/05
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000IGHRI", "49f9f341-8e03-4ecc-a340-cb664b9a1e3f"));//Philips BRE255/05
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000X1SUS", "75b2b272-68e2-40e6-83c2-49fd13f2ca8c"));//Philips BRE700/05
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000X1SUQ", "d80d55ec-f465-4b07-b99a-8ff2afa7a47e"));//Philips BRE710/05
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000X1SUM", "ab4b405b-503b-44cb-ab5e-f689ebb648f2"));//Philips BRE730/05
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000BEQYE", "01b69774-2f7a-480d-a2cf-a1a5fb4e2fb9"));//Philips BRI950/00
        //Philips Epilasyon END

        //Philips Ütü START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000004VWIB", "848c330c-d2a3-457a-a6da-104ca188c452"));//Philips DST5010/10
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000040BAZN", "a843dfd1-daa0-40ef-9f4c-1d4aaa2773f3"));//Philips DST7020/20
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000040B9IU", "53720617-2071-4c08-bc39-db25d986cd3d"));//Philips DST7030/20
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003XBZIQ", "faad820d-58bf-44b5-a9db-51c52e54f66c"));//Philips DST7040/80
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003MWUIQ", "e706e70c-45eb-4366-9d10-48b9f210117b"));//Philips DST7510/80
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000036JU2J", "7244caff-73a3-46fe-8b09-af134f135036"));//Philips DST8021/30
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000036RV15", "e9dd3856-b1bb-411e-9b01-61273cd65c2e"));//Philips DST8050/20
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV0000169TMV", "c06b25fc-2357-401b-9145-a8aa4dcc1682"));//Philips GC628/80
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00000PDHXI", "a5821b20-a5dd-4b50-8ac8-867a03e1b6c7"));//Philips GC6842/30
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00000GQ4VO", "1e5ca463-0ab4-4d80-91d6-da47f90c3592"));//Philips GC810/20
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000029SF4C", "16c97f2b-e20d-42ef-bbe2-f672abf24efd"));//Philips STH7060/80
        //Philips Ütü END

        //Philips Tıraş Makinesi START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000028LC4D", "200f4df1-afc1-4f5d-bdd2-bb1a22998b5a"));//Philips BG1024/15
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000CI8YT", "bc76d442-f967-4e63-ba82-a06db5435ff4"));//Philips BT3206/14
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000004SMJ8", "228bdf3d-245e-43c5-9a55-251d66e93503"));//Philips HC3525/15
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000004SMJA", "cf06124b-6d05-4033-9f20-d41cd42193a1"));//Philips HC5630/15
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV000007UYMH", "dce7bf2b-a836-42de-8d29-94cc5e6ef5fb"));//Philips MG3710
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000BTG0X", "4da58340-9737-428f-8b40-f81a2a9333f2"));//Philips MG3720
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000XY9VS", "7ff91d76-8815-4cf7-ab55-2d2b1000b5e1"));//Philips MG5720
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000051HF8J", "eb0a952f-bf34-41e3-97f3-f5a6545fefde"));//Philips MG5920/15
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000051HF8L", "6a0308cc-f52e-43ed-87c7-fe73286b58f5"));//Philips MG5940/15
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000051HF8H", "bf7aad2e-9eea-4a3e-9c27-e27b18cee7ef"));//Philips MG7920/15
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000051HF8F", "acff0c3c-e954-422a-bcd5-62cfec160035"));//Philips MG7950/15
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00001AZ4HS", "d9962f47-3795-43e1-95be-94b221d6ae5f"));//Philips NT3650/16
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000D68UF", "1206a0b1-f4ef-4fea-b72b-4fd08ae71d7a"));//Philips QP220/51
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004A3HC6", "6036df0a-ceb0-4a48-acdd-0957d4883696"));//Philips QP2724/10
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000046IAT0", "d85f90e7-8a8e-433c-b766-4158df286b00"));//Philips QP2724/20
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004A3FIC", "824cab47-41c8-4d42-b9dc-914921d9991e"));//Philips QP2824/10
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003KN6D2", "a40d2e3a-a6e8-4238-b278-4de3c9e32f9a"));//Philips S5444/03
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000046IAT2", "b0fd32b6-e5e3-4bd8-913a-033b097cd1d2"));//Philips 5887/10
        //Philips Tıraş Makinesi END

        //Samsung Süpürge START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004VOIOT", "e1d28eec-408d-481d-8375-8b65e908240e"));//Samsung VS20B75ACR5
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004VOIOX", "13ff3893-c3cd-4ff8-a4fe-84c21871d7ef"));//Samsung VS25C9754QG
        //Samsung Süpürge END

        //Samsung Telefon START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000049G7D1", "bd68cfea-2fb8-4f1d-b70e-4f4b3f30a7b2"));//Samsung A24 128 Siyah

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000040I6N3", "849f982d-51b0-4fac-8af2-5ff7deb45928"));//Samsung A34 128 Açık Yeşil
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000040I5H7", "16f71620-0686-44db-a687-b1df3ba1a71a"));//Samsung A34 128 Gümüş
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000040I5H6", "b4989324-edf1-4a57-ba5e-84d04918b6dd"));//Samsung A34 128 Siyah

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000040I5HA", "ba11404e-1ae2-4369-b49f-8a346cb6deb5"));//Samsung A54 256 Beyaz
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000040I5CW", "4ab4bde6-c9fd-4054-b4e8-4ce9cb51406f"));//Samsung A54 256 Yeşil

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004WO01C", "13574e32-0dd2-478a-872f-26e833e6663e"));//Samsung M34 128 Gümüş
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004WO01B", "cbee4d81-f049-47c8-8fb2-712ce548188c"));//Samsung M34 128 Koyu Mavi
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004WO01A", "2bab7631-8157-4967-a9f0-180c69384c0a"));//Samsung M34 128 Mavi

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003Z341U", "827ef12b-48ab-4681-b73e-ea10dbd9dc0e"));//Samsung S23 128 Altın
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003Z341W", "309850e6-6ef4-409d-8e0e-789ef7111315"));//Samsung S23 128 Siyah
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003Z341V", "43a25d20-f6a3-4dfc-9a62-6050416b0cfe"));//Samsung S23 128 Yeşil

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003P2GFW", "d4ace9fd-4973-4253-a086-e320c0a49fcd"));//Samsung S23 Ultra 256 Siyah
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003P2GFV", "b6ad82d9-9716-47d2-b237-74d6e7d230b6"));//Samsung S23 Ultra 256 Yeşil
        //Samsung Telefon END

        //Apple iPhone START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV0000120X65", "2d435630-e5a0-4ec2-ba3a-344aa3ffe71c"));//Apple iPhone 11 64 Siyah
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV0000120X66", "4b8183f8-754f-4208-8d9f-772b1547179a"));//Apple iPhone 11 64 Beyaz
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV0000122JCR", "09057654-f506-4636-9756-111f685a1384"));//Apple iPhone 11 128 Beyaz
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV0000120X5Z", "c1cb0938-2f04-4928-9d4a-1430cb74248f"));//Apple iPhone 11 128 Siyah

        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00000ODHHO", "cbb863db-8b05-4ed0-bbcf-b68a9ddccb67"));//Apple iPhone 13 128 Beyaz
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00000ODHHV", "cce082cf-86c1-4e9b-926c-d20708a7e81d"));//Apple iPhone 13 128 Mavi
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00000ODHHZ", "92f794e8-3fc1-4828-bb7a-ab9b0650ad8f"));//Apple iPhone 13 128 Pembe
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00000ODHHF", "d7fe5a14-b256-4d96-8e10-1b0832644e3d"));//Apple iPhone 13 128 Siyah
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00001T9W5S", "92e8495b-80b1-406d-a9ca-0bf4ad2a24d8"));//Apple iPhone 13 128 Yeşil
        //Apple iPhone END

        //Tefal Ütü START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000FJESG", "a4fac859-04c3-4fab-a12e-884566e96c0f"));//Tefal FV5685
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000T25IW", "b565db39-94f6-4ecc-b977-12b4e00b35d0"));//Tefal FV5695
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00000OX4U9", "d6e65f14-d0f4-48c0-bd52-9575f5e019ae"));//Tefal FV6870
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00002XYB34", "ea041a15-4517-4280-b1ed-2415f0073e3a"));//Tefal GV9720
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000053RNYF", "875c10e8-d878-4782-9a28-fd5ee46ece28"));//Tefal SV4110
        //Tefal Ütü END

        //Arzum Ütü START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000457RP", "72a2c607-44b1-4d11-91f1-0326a7bbc4b9"));//Arzum AR684
        //Arzum Ütü END

        //Tefal Fritöz START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00001UF7SS", "f4225242-42f1-4710-9fce-f9c0da748dae"));//Tefal EY5018
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00003NMGYR", "24c5f738-b878-450b-ade2-d593f212b949"));//Tefal EY8018
        //Tefal Fritöz END

        //Tefal Tencere Tava START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00001XJPOK", "c17c70ce-bcce-48e7-811b-b283e2975444"));//Tefal L15392
        //Tefal Tencere Tava END

        //Grundig Çay-Kahve Makinesi START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000OAVZC", "c0223afc-6d83-4587-aeee-ef32a33b6faf"));//Grundig TM4961
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000045BTPB", "5ae2f2b8-88c7-450b-8dfd-9499bdf0ac7c"));//Grundig TKM1341
        //Grundig Çay-Kahve Makinesi END

        //Roborock Robot Süpürge START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00004UKU2J", "cb03e247-9de8-4764-b338-bfcec3bd1762"));//Roborock S7 Max Ultra Siyah İstasyonlu
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00002H4FKM", "7df3e1c5-4a41-461e-b8f9-821d504f8a2f"));//Roborock Q7 Max Plus Pro Beyaz
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV000055P4JM", "7828a6f6-f996-49bc-8454-c47fd73b2a67"));//Roborock Q8 Max Beyaz
        //Roborock Robot Süpürge END

        //Xiaomi Robot Süpürge START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00001OHNU7", "deebe87e-ef56-462d-8198-c2be631f70c6"));//Xiaomi Vacuum Mop 2 Beyaz
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00001I64SV", "6abdadd8-cf08-40e8-bf1c-f11ed0410f14"));//Xiaomi Vacuum Mop 2 Pro Siyah
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00001I64ST", "1f96f806-e2c0-4f8a-8715-12bf49b26a98"));//Xiaomi Vacuum Mop 2 Pro Beyaz
        //Xiaomi Robot Süpürge END

        //Xiaomi Hava Temizleme START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00002BONPZ", "73f535cf-e37e-447e-af1b-d07f7b92a4f0"));//Xiaomi Smart Air Purifier 4 Lite GI
        //Xiaomi Hava Temizleme END

        //Karaca Robot Süpürge START
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBCV00002Z4EJ9", "9dfafda2-b688-4f78-bfd3-459e0eb5cc24"));//Karaca Vantuz Off-Road RS007 2 in 1
        //Karaca Robot Süpürge END

        //DYSON
        hbRequestMetadataModelList.add(new HbRequestMetadataModel("HBV00000LZ4D1", "805c71af-73be-4033-b59a-c73109c17165"));//Karaca Vantuz Off-Road RS007 2 in 1
        //DYSON
    }
}
