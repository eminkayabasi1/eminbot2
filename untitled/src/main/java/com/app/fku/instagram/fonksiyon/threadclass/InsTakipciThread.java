package com.app.fku.instagram.fonksiyon.threadclass;

import com.app.fku.instagram.fonksiyon.service.InsEkranOkumaService;

public class InsTakipciThread implements Runnable {

    public static InsEkranOkumaService insEkranOkumaService;
    public static Integer threadSirasi;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        for (int j = 1; j > 0; j++) {
            try {
                System.out.println("Ins Takipçi Thread : Thread Sirasi =" + localThreadSirasi + ", Kaçıncı Tur =" + j);
                insEkranOkumaService.takipciKas();
            } catch (Exception e) {
                System.out.println("TKN Searcher Thread Hata Aldım.");
            }
        }
    }
}
