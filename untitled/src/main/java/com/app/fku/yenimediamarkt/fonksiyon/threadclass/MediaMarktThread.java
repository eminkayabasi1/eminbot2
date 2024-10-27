package com.app.fku.yenimediamarkt.fonksiyon.threadclass;

import com.app.fku.vatan.fonksiyon.service.VatanService;
import com.app.fku.yenimediamarkt.fonksiyon.service.MediaMarktService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaMarktThread implements Runnable {

    public static MediaMarktService mediaMarktService;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                for (int i = 0; i < 20; i++) {
                    mediaMarktService.sorgula();
                }
            } catch (Exception e) {
                System.out.println("MediaMarkt Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
