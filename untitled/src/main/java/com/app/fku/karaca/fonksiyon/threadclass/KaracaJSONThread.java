package com.app.fku.karaca.fonksiyon.threadclass;

import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaJSONService;
import com.app.fku.karaca.fonksiyon.service.KaracaJSONService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KaracaJSONThread implements Runnable {

    public static KaracaJSONService karacaJSONService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    karacaJSONService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("Karaca JSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
