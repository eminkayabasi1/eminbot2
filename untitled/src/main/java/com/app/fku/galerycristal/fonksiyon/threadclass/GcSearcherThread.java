package com.app.fku.galerycristal.fonksiyon.threadclass;

import com.app.fku.galerycristal.entity.GcKategori;
import com.app.fku.galerycristal.fonksiyon.service.GcEkranOkumaService;
import com.app.fku.galerycristal.repository.GcKategoriRepository;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GcSearcherThread implements Runnable {

    public static GcEkranOkumaService gcEkranOkumaService;
    public static GcKategoriRepository gcKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        GcKategori gcKategori = gcKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = gcEkranOkumaService.kategoriIleUrunBul(gcKategori, 0, urunHashMap, tmpUrunHashMap, j==1);
                    logService.gcLogYaz("GC Tur Bitti " + tmpUrunHashMap.size() + " - " + gcKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.gcLogYaz("GC Searcher Thread Hata Aldım.");
            }
        }
    }
}
