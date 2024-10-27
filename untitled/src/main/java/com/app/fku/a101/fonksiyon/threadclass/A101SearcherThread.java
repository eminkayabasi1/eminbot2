package com.app.fku.a101.fonksiyon.threadclass;

import com.app.fku.a101.entity.A101Kategori;
import com.app.fku.a101.fonksiyon.service.A101UrunToplaFonksiyon;
import com.app.fku.a101.repository.A101KategoriRepository;
import com.app.fku.genel.fonksiyon.service.LogService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class A101SearcherThread implements Runnable {

    public static Integer threadSirasi;
    public static Long kategoriId;
    public static A101KategoriRepository a101KategoriRepository;
    public static A101UrunToplaFonksiyon a101UrunToplaFonksiyon;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;

        HashMap<String, Long> urunHashMap = new HashMap<>();

        A101Kategori a101Kategori = a101KategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, Long> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = a101UrunToplaFonksiyon.menudenUrunBul(a101Kategori, 1, null, urunHashMap, tmpUrunHashMap, j==1);
                    logService.a101LogYaz("A101 Tur Bitti " + tmpUrunHashMap.size() + " - " + a101Kategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.a101LogYaz("A101 Searcher Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
