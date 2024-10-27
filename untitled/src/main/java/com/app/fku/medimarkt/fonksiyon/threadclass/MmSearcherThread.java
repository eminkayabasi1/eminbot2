package com.app.fku.medimarkt.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.medimarkt.entity.MmKategori;
import com.app.fku.medimarkt.fonksiyon.service.MmEkranOkumaService;
import com.app.fku.medimarkt.repository.MmKategoriRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MmSearcherThread implements Runnable {

    public static MmEkranOkumaService mmEkranOkumaService;
    public static MmKategoriRepository mmKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        MmKategori mmKategori = mmKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = mmEkranOkumaService.kategoriIleUrunBul(mmKategori, 0, 1, 1, urunHashMap, tmpUrunHashMap, j < 10);
                    logService.mmLogYaz("MediaMarkt Tur Bitti " + tmpUrunHashMap.size() + " - " + mmKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.mmLogYaz("MediaMarkt Searcher Thread Hata Aldım.");
            }
        }
    }
}
