package com.app.fku.itopya.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.itopya.entity.ItopyaKategori;
import com.app.fku.itopya.fonksiyon.service.ItopyaUrunToplaFonksiyon;
import com.app.fku.itopya.repository.ItopyaKategoriRepository;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ItopyaSearcherThread implements Runnable {

    public static Integer threadSirasi;
    public static Long kategoriId;
    public static ItopyaKategoriRepository itopyaKategoriRepository;
    public static ItopyaUrunToplaFonksiyon itopyaUrunToplaFonksiyon;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;

        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();

        logService.itopyaLogYaz("Itopya Thread Başladı");
        ItopyaKategori itopyaKategori = itopyaKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = itopyaUrunToplaFonksiyon.menudenUrunBul(itopyaKategori, 1, null, urunHashMap, tmpUrunHashMap, j < 5);
                    logService.itopyaLogYaz("Itopya Tur Bitti " + tmpUrunHashMap.size() + " - " + itopyaKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.itopyaLogYaz("Itopya Searcher Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }
}
