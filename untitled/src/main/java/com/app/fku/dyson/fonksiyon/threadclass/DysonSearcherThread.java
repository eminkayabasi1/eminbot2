package com.app.fku.dyson.fonksiyon.threadclass;

import com.app.fku.dyson.entity.DysonKategori;
import com.app.fku.dyson.fonksiyon.service.DysonEkranOkumaService;
import com.app.fku.dyson.repository.DysonKategoriRepository;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DysonSearcherThread implements Runnable {

    public static DysonEkranOkumaService dysonEkranOkumaService;
    public static DysonKategoriRepository dysonKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        DysonKategori dysonKategori = dysonKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = dysonEkranOkumaService.kategoriIleUrunBul(dysonKategori, 0, 1, 1, urunHashMap, tmpUrunHashMap, j==1);
                    logService.dysonLogYaz("DYSON Tur Bitti " + tmpUrunHashMap.size() + " - " + dysonKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.dysonLogYaz("DYSON Searcher Thread Hata Aldım.");
            }
        }
    }
}
