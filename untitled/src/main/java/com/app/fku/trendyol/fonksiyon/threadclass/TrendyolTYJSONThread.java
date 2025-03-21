package com.app.fku.trendyol.fonksiyon.threadclass;

import com.app.fku.trendyol.fonksiyon.service.TrendyolJSONService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolTYJSONService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TrendyolTYJSONThread implements Runnable {

    public static TrendyolTYJSONService trendyolTYJSONService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    trendyolTYJSONService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("Ty TYJSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
