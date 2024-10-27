package com.app.fku.karaca.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.karaca.entity.KrcKategori;
import com.app.fku.karaca.fonksiyon.service.KrcEkranOkumaService;
import com.app.fku.karaca.repository.KrcKategoriRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class KrcSearcherThread implements Runnable {

    public static KrcEkranOkumaService krcEkranOkumaService;
    public static KrcKategoriRepository krcKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        KrcKategori krcKategori = krcKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = krcEkranOkumaService.kategoriIleUrunBul(krcKategori, 0, 1, 1, urunHashMap, tmpUrunHashMap, j==1);
                    logService.krcLogYaz("KRC Tur Bitti " + tmpUrunHashMap.size() + " - " + krcKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.krcLogYaz("KRC Searcher Thread Hata Aldım.");
            }
        }
    }
}
