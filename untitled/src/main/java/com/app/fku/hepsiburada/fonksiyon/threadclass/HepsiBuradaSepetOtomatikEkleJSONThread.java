package com.app.fku.hepsiburada.fonksiyon.threadclass;

import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaSepetOtomatikEkleJSONService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HepsiBuradaSepetOtomatikEkleJSONThread implements Runnable {

    public static HepsiBuradaSepetOtomatikEkleJSONService hepsiBuradaSepetOtomatikEkleJSONService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    hepsiBuradaSepetOtomatikEkleJSONService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("HB Sepet UE JSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
