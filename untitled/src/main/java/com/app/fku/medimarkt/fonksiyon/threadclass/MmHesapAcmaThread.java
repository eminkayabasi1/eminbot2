package com.app.fku.medimarkt.fonksiyon.threadclass;

import com.app.fku.medimarkt.fonksiyon.service.MmEkranOkumaService;

public class MmHesapAcmaThread implements Runnable {

    public static MmEkranOkumaService mmEkranOkumaService;

    public void run() {
        for (int j = 1; j > 0; j++) {
            try {
                mmEkranOkumaService.hesapAcma();
            } catch (Exception e) {
                System.out.println("Ymk Hesap Aç Thread Hata Aldım.");
            }
        }
    }
}
