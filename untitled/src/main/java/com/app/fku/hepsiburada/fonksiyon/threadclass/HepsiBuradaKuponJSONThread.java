package com.app.fku.hepsiburada.fonksiyon.threadclass;

import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaKuponJSONService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaSepetJSONService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HepsiBuradaKuponJSONThread implements Runnable {

    public static HepsiBuradaKuponJSONService hepsiBuradaKuponJSONService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    hepsiBuradaKuponJSONService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("HB Kupon JSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
