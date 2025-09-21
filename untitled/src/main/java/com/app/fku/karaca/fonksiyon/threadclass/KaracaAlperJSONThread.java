package com.app.fku.karaca.fonksiyon.threadclass;

import com.app.fku.karaca.fonksiyon.service.KaracaAlperJSONService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KaracaAlperJSONThread implements Runnable {

    public static KaracaAlperJSONService karacaAlperJSONService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    karacaAlperJSONService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("Karaca JSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
