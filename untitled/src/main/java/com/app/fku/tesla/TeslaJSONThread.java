package com.app.fku.tesla;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TeslaJSONThread implements Runnable {

    public static TeslaJSONService teslaJSONService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    teslaJSONService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("Ty TYJSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
