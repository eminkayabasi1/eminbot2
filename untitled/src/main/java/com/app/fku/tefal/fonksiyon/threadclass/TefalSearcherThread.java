package com.app.fku.tefal.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.tefal.entity.TefalKategori;
import com.app.fku.tefal.fonksiyon.service.TefalEkranOkumaService;
import com.app.fku.tefal.repository.TefalKategoriRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TefalSearcherThread implements Runnable {

    public static TefalEkranOkumaService tefalEkranOkumaService;
    public static TefalKategoriRepository tefalKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        TefalKategori tefalKategori = tefalKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = tefalEkranOkumaService.kategoriIleUrunBul(tefalKategori, 0, 1, 1, urunHashMap, tmpUrunHashMap, j==1);
                    logService.tefalLogYaz("TEFAL Tur Bitti " + tmpUrunHashMap.size() + " - " + tefalKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.tefalLogYaz("TEFAL Searcher Thread Hata Aldım.");
            }
        }
    }
}
