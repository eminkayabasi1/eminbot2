package com.app.fku.trendyol.fonksiyon.threadclass;

import com.app.fku.trendyol.fonksiyon.service.Trendyol5AlJSONService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Trendyol5AlJSONThread implements Runnable {

    public static Trendyol5AlJSONService trendyol5AlJSONService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    trendyol5AlJSONService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("Ty JSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
