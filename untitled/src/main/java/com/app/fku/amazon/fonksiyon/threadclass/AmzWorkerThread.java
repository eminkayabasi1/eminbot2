package com.app.fku.amazon.fonksiyon.threadclass;

import com.app.fku.amazon.fonksiyon.service.AmzEkranOkumaService;
import com.app.fku.amazon.fonksiyon.service.AmzWorkerService;
import com.app.fku.amazon.repository.AmzKategoriRepository;
import com.app.fku.amazon.repository.AmzUrunRepository;

import java.io.IOException;
import java.util.List;

public class AmzWorkerThread implements Runnable {

    public static Integer workerThreadCount;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static AmzKategoriRepository amzKategoriRepository;
    public static AmzUrunRepository amzUrunRepository;
    public static AmzEkranOkumaService amzEkranOkumaService;
    public static AmzWorkerService amzWorkerService;

    public void run() {
        Integer localWorkerThreadCount = workerThreadCount;
        Integer localThreadSirasi = threadSirasi;
        System.out.println("Amz Thread Ayağa Kalktı");

        for (int i = 1; i > 0; i++) {
            List<String> asinNoList = amzUrunRepository.urunThreadSorgusuKategorisiz2(localWorkerThreadCount, localThreadSirasi);
            for (String asinNo: asinNoList) {
                try {
//                    amzWorkerService.ekrandanFiyatOku(asinNo, 0);
                    amzWorkerService.ekrandanFiyatOkuHtmlUnit(asinNo, 0);
                } catch (InterruptedException e) {

                } catch (IOException e) {
                }
            }
        }
    }
}
