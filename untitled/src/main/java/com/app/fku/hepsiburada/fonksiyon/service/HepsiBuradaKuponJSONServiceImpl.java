package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaKuponJSONService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaSepetJSONService;
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
public class HepsiBuradaKuponJSONServiceImpl implements HepsiBuradaKuponJSONService {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    static
    GenelService genelService;

    @Autowired
    LogService logService;

    private static final HashMap<String, HbTokenModel> hbTokenHashMap = new HashMap<>();
    private static final List<HbRequestKuponModel> hbKuponRequestModelList = new ArrayList<>();

    @Override
    public void sorgula() throws IOException, InterruptedException {
        kuponListDoldur();
        for (;;) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("hata geldi");
            }
        }
    }

    private void anaislem() throws InterruptedException, IOException {
        HbTokenModel hbTokenModel = new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MTM4MjQ4ODksImV4cCI6MTcxNDA4NDA4OSwiaWF0IjoxNzEzODI0ODg5LCJVc2VySWQiOiJmZjcwNmM4My1mNzY2LTRjNTgtYTFkZi0zMWJlNjI4ZDQ0OTAiLCJUaXRsZSI6IkVtaW4gS2F5YWJhxZ_EsSIsIkZpcnN0TmFtZSI6IkVtaW4iLCJMYXN0TmFtZSI6IktheWFiYcWfxLEiLCJFbWFpbCI6ImVtaW4uYWhtZXQuaGIuMkBheWthbmsuc2l0ZSIsIklzQXV0aGVudGljYXRlZCI6IlRydWUiLCJBcHBLZXkiOiJBRjdGMkEzNy1DQzRCLTRGMUMtODdGRC1GRjM2NDJGNjdFQ0IiLCJQcm92aWRlciI6IkhlcHNpYnVyYWRhIiwiU2hhcmVEYXRhUGVybWlzc2lvbiI6IlRydWUiLCJwIjp7InQiOltdLCJlIjoiUnpISEFVUHMxbGFWc2pobkE1VUhiU25mVnhBeXp6SkZzZU1WU1dxTVNCRT0ifX0.MUnTnOa4YI5u_Xbs_ty0Xvntb7pRwn_1B3uUVTlWwVk");//emin.ahmet.hb.2@aykank.site

        for (HbRequestKuponModel hbKuponRequestModel: hbKuponRequestModelList) {
            kuponEkle(hbTokenModel, hbKuponRequestModel);
        }
    }

    private static void telegramMesajGonder(String mesaj, String chatId) throws IOException, InterruptedException {
        genelService.telegramMesajGonder(mesaj, chatId, "1", "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM");
    }

    public static void kuponEkle(HbTokenModel hbTokenModel, HbRequestKuponModel hbKuponRequestModel) throws IOException, JSONException, InterruptedException {

        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "BEARER " + hbTokenModel.getBearerTokent());
        //headerMap.put("Client-Id", hbTokenModel.getClientId());
        //headerMap.put("Tenant-Id", hbTokenModel.getTenantId());
        headerMap.put("Content-Type", "application/json;charset=UTF-8");
        headerMap.put("origin", "https://checkout.hepsiburada.com");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String jsonStr = ow.writeValueAsString(hbKuponRequestModel);

        Boolean kuponEklendiMi = true;
        try {
            Jsoup
                    .connect("https://obiwan-gw.hepsiburada.com/api/v1/giftcert/useGiftCert")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                    .headers(headerMap)
                    .method(Connection.Method.POST)
                    .requestBody(jsonStr)
                    .ignoreContentType(true).execute().body();
        } catch (Exception e) {
            System.out.println(hbKuponRequestModel.getCode() + " eklenemedi");
            kuponEklendiMi = false;
        }

        if (kuponEklendiMi) {
            telegramMesajGonder(hbKuponRequestModel.getCode() + " aktif", "-4159238415");
        }
    }

    public static void kuponListDoldur() {
        /**
        hbKuponRequestModelList.add(new HbRequestKuponModel("PHILIPS10"));
        hbKuponRequestModelList.add(new HbRequestKuponModel("PHILIPS15"));
        hbKuponRequestModelList.add(new HbRequestKuponModel("PHILIPS20"));
        hbKuponRequestModelList.add(new HbRequestKuponModel("PHILIPS1"));
        */hbKuponRequestModelList.add(new HbRequestKuponModel("PHILIPS100"));
    }
}
