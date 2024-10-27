package com.app.fku.hepsiburada.fonksiyon.threadclass;

import com.app.fku.hepsiburada.fonksiyon.impl.HbUyelikService;

public class HbKuponKontrolThread implements Runnable {

    public static HbUyelikService hbUyelikService;

    public void run() {
        for (int i = 1; i > 0; i ++) {
            try {
                hbUyelikService.kuponKontrol();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
