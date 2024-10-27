package com.app.fku.yemeksepeti.fonksiyon.threadclass;

import com.app.fku.yemeksepeti.fonksiyon.service.YmkEkranOkumaService;

public class YmkHesapAcmaThread implements Runnable {

    public static YmkEkranOkumaService ymkEkranOkumaService;

    public void run() {
        for (int j = 1; j > 0; j++) {
            try {
                ymkEkranOkumaService.hesapAcma();
            } catch (Exception e) {
                System.out.println("Ymk Hesap Aç Thread Hata Aldım.");
            }
        }
    }
}
