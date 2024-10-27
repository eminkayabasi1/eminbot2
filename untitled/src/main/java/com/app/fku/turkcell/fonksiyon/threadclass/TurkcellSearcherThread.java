package com.app.fku.turkcell.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.turkcell.entity.TurkcellKategori;
import com.app.fku.turkcell.fonksiyon.service.TurkcellEkranOkumaService;
import com.app.fku.turkcell.repository.TurkcellKategoriRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TurkcellSearcherThread implements Runnable {

    public static TurkcellEkranOkumaService turkcellEkranOkumaService;
    public static TurkcellKategoriRepository turkcellKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        TurkcellKategori turkcellKategori = turkcellKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = turkcellEkranOkumaService.kategoriIleUrunBul(turkcellKategori, 0, 1, 1, urunHashMap, tmpUrunHashMap, j==1);
                    logService.turkcellLogYaz("TURKCELL Tur Bitti " + tmpUrunHashMap.size() + " - " + turkcellKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.turkcellLogYaz("TURKCELL Searcher Thread Hata Aldım.");
            }
        }
    }
}
