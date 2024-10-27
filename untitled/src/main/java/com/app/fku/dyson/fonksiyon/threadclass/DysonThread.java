package com.app.fku.dyson.fonksiyon.threadclass;

import com.app.fku.dyson.fonksiyon.service.DysonService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DysonThread implements Runnable {

    public static DysonService dysonService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    dysonService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("DYSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
