package com.app.fku.hepsiburada.fonksiyon.threadclass;

import com.app.fku.hepsiburada.fonksiyon.impl.HbUyelikService;

public class HbUyelikAcThread implements Runnable {

    public static HbUyelikService hbUyelikService;

    public void run() {
        for (int i = 1; i > 0; i ++) {
            try {
                hbUyelikService.uyelikAc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
