package com.app.fku.hepsiburada.fonksiyon.threadclass;

import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.fonksiyon.impl.HbWorkerService;
import com.app.fku.hepsiburada.repository.HbKategoriRepository;
import com.app.fku.hepsiburada.repository.HbUrunRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HbWorkerThread implements Runnable {

    public static Integer workerThreadCount;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static HbKategoriRepository hbKategoriRepository;
    public static HbUrunRepository hbUrunRepository;
    public static HbWorkerService hbWorkerService;

    public void run() {
        Integer localWorkerThreadCount = workerThreadCount;
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;

        boolean kategoriSiniriVarMi = false;
        HbKategori hbKategori = null;
        if (localKategoriId != null) {
            hbKategori = hbKategoriRepository.findById(localKategoriId).orElse(null);
            if (hbKategori == null) {
                kategoriSiniriVarMi = false;
            } {
                kategoriSiniriVarMi = true;
            }
        } else {
            kategoriSiniriVarMi = false;
        }

        for (int i = 1; i > 0; i ++) {
            try {
                List<String> hbNoList = new ArrayList<String>();
                if (kategoriSiniriVarMi) {
                    hbNoList = hbUrunRepository.urunThreadSorgusuKategorili2(localKategoriId, localWorkerThreadCount, localThreadSirasi);
                } else {
                    hbNoList = hbUrunRepository.urunThreadSorgusuKategorisiz2(localWorkerThreadCount, localThreadSirasi);
                }
                System.out.println("Hb Worker Thread Başladı: threadSirasi : " + localThreadSirasi + " - List Size : " + hbNoList.size());

                if (hbNoList != null && hbNoList.size() > 0) {
                    for (String hbNo: hbNoList) {
                        hbWorkerService.sayfadanFiyatBul(hbNo);
                        Random rand = new Random();
                        int sleepRand = rand.nextInt(3);
                        TimeUnit.SECONDS.sleep(sleepRand);
                    }
                }
            } catch (Exception e) {
                System.out.println("Hb Thread içerisinde hata aldım.");
            }
        }
    }
}
