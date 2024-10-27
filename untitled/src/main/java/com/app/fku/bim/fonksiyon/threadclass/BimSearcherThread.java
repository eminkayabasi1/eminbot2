package com.app.fku.bim.fonksiyon.threadclass;

import com.app.fku.bim.entity.BimKategori;
import com.app.fku.bim.fonksiyon.service.BimUrunToplaFonksiyon;
import com.app.fku.bim.repository.BimKategoriRepository;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BimSearcherThread implements Runnable {

    public static Integer threadSirasi;
    public static Long kategoriId;
    public static BimKategoriRepository bimKategoriRepository;
    public static BimUrunToplaFonksiyon bimUrunToplaFonksiyon;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;

        HashMap<String, Long> urunHashMap = new HashMap<>();
        HashMap<String, Long> tmpUrunHashMap = new HashMap<>();

        HashMap<String, Long> yasakliUrunHashMap = yasakliUrunHashMapDoldur();

        BimKategori bimKategori = bimKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                if (urunHashMap.size() == 0) {
                    urunHashMap = tmpUrunHashMap;
                }
                urunHashMap = bimUrunToplaFonksiyon.menudenUrunBul(bimKategori, urunHashMap, j==1, yasakliUrunHashMap);
                if (urunHashMap.size() > 0) {
                    tmpUrunHashMap = urunHashMap;
                }
                System.out.println("Bim Tur Bitti " + urunHashMap.size() + " - " + bimKategori.getKategoriAdi());
                Random rand = new Random();
                int sleepRand = rand.nextInt(20);
                TimeUnit.SECONDS.sleep(sleepRand);
            } catch (Exception e) {
                System.out.println("Bim Searcher Thread hata aldım: " + sdf.format(new Date()));
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                System.out.println(sw.toString());
            }
        }
    }

    public HashMap<String, Long> yasakliUrunHashMapDoldur() {
        HashMap<String, Long> yasakliUrunHashMap = new HashMap<>();
        yasakliUrunHashMap.put("/aktuel-urunler/50-127-ekran-4k-smart-uhd-tv/aktuel.aspx", 1L);
        yasakliUrunHashMap.put("/aktuel-urunler/jc1l001m0035-kadin-kol-saati/aktuel.aspx", 1L);
        return yasakliUrunHashMap;
    }
}
