package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaSepetJSONService;
import com.app.fku.hepsiburada.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.openqa.selenium.json.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HepsiBuradaJSONServiceImpl implements HepsiBuradaSepetJSONService {

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

        for (; ; ) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("HBSepet hata geldi");
            }
        }
    }

    private void anaislem() throws InterruptedException, IOException {
        HashMap<String, HbSepetUrunModel> urunHashMap = new HashMap<>();

        boolean ilkTur = true;
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
                }
                hbTokenModel.setUrunSayisi(urunSayisi);
                hbTokenHashMap.put(hbTokenModel.getBearerTokent(), hbTokenModel);
            }

            for (HbSepetUrunModel hbSepetUrunModel : topluUrunList) {
                HbSepetUrunModel eskiHbUrunModel = urunHashMap.get(hbSepetUrunModel.getProduct().getSku());
                if (eskiHbUrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (hbSepetUrunModel.getPrice().getAmount() < eskiHbUrunModel.getPrice().getAmount() && !ilkTur) {
                        String mesaj = "" +
                                "İndirim%0A" +
                                "" + hbSepetUrunModel.getProduct().getName() + "%0A" +
                                "Eski Fiyat: " + eskiHbUrunModel.getPrice().getAmount() + "%0A" +
                                "Yeni Fiyat: " + hbSepetUrunModel.getPrice().getAmount() + "%0A" +
                                "Link:" + hbSepetUrunModel.getProduct().getUrl();

                        telegramMesajGonder(mesaj, "-4160976358");
                        telegramMesajGonder(mesaj, "-4101368331");
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün%0A" +
                                "" + hbSepetUrunModel.getProduct().getName() + "%0A" +
                                "Fiyat Fiyat: " + hbSepetUrunModel.getPrice().getAmount() + "%0A" +
                                "Link:" + hbSepetUrunModel.getProduct().getUrl();

                        telegramMesajGonder(mesaj, "-4160976358");
                        telegramMesajGonder(mesaj, "-4101368331");
                    }
                }

                yeniUrunHashMap.put(hbSepetUrunModel.getProduct().getSku(), hbSepetUrunModel);
            }

            ilkTur = false;
            urunHashMap = yeniUrunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, String chatId) throws IOException, InterruptedException {
        genelService.telegramMesajGonder(mesaj, chatId, "1", "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM");
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
}
