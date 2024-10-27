package com.app.fku.arzum.fonksiyon.threadclass;

import com.app.fku.arzum.fonksiyon.service.ArzumService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArzumThread implements Runnable {

    public static ArzumService arzumService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    arzumService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("Arzum Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
