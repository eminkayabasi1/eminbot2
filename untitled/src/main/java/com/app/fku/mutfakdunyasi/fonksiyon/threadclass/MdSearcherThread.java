package com.app.fku.mutfakdunyasi.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.mutfakdunyasi.entity.MdKategori;
import com.app.fku.mutfakdunyasi.fonksiyon.service.MdEkranOkumaService;
import com.app.fku.mutfakdunyasi.repository.MdKategoriRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MdSearcherThread implements Runnable {

    public static MdEkranOkumaService mdEkranOkumaService;
    public static MdKategoriRepository mdKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        MdKategori mdKategori = mdKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = mdEkranOkumaService.kategoriIleUrunBul(mdKategori, 0, 1, 1, urunHashMap, tmpUrunHashMap, j==1);
                    logService.mdLogYaz("MD Tur Bitti " + tmpUrunHashMap.size() + " - " + mdKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.mdLogYaz("MD Searcher Thread Hata Aldım.");
            }
        }
    }
}
