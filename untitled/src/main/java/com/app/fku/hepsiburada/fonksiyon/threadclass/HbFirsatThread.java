package com.app.fku.hepsiburada.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.hepsiburada.fonksiyon.impl.HbFirsatService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class HbFirsatThread implements Runnable {

    public static HbFirsatService hbFirsatService;
    public static LogService logService;
    public static String eposta;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String localeposta = eposta;
        System.out.println(localeposta);
        for (int j = 1; j > 0; j++) {
            try {
                hbFirsatService.firsatBul(localeposta);
            } catch (Exception e) {
                logService.hepsiBuradaLogYaz("HB Fırsat Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
