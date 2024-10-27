package com.app.fku.amazonx.fonksiyon.threadclass;

import com.app.fku.amazonx.fonksiyon.service.AmazonxService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AmazonxThread implements Runnable {

    public static AmazonxService amazonxService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    amazonxService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("Amazon Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
