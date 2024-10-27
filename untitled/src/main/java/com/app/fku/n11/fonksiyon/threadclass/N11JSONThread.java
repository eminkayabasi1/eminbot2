package com.app.fku.n11.fonksiyon.threadclass;

import com.app.fku.n11.fonksiyon.service.N11JSONService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class N11JSONThread implements Runnable {

    public static N11JSONService n11JSONService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    n11JSONService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("N11 JSON Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
