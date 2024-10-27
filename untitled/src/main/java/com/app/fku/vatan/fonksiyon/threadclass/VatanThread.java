package com.app.fku.vatan.fonksiyon.threadclass;

import com.app.fku.vatan.fonksiyon.service.VatanService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VatanThread implements Runnable {

    public static VatanService vatanService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    vatanService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("Vatan Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
