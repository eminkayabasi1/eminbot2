package com.app.fku.hepsiburada.fonksiyon.threadclass;

import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaJSONService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolJSONService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HepsiBuradaJSONThread implements Runnable {

    public static HepsiBuradaJSONService hepsiBuradaJSONService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    hepsiBuradaJSONService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("HB JSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
