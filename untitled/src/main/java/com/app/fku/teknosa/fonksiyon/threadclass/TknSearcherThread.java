package com.app.fku.teknosa.fonksiyon.threadclass;

import com.app.fku.a101.entity.A101Kategori;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.teknosa.entity.TknKategori;
import com.app.fku.teknosa.fonksiyon.service.TknEkranOkumaService;
import com.app.fku.teknosa.repository.TknKategoriRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TknSearcherThread implements Runnable {

    public static TknEkranOkumaService tknEkranOkumaService;
    public static TknKategoriRepository tknKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        HashMap<String, String> havaleHashMap = havaleHashMapDoldur();
        TknKategori tknKategori = tknKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = tknEkranOkumaService.kategoriIleUrunBul(tknKategori, 0, 1, 1, urunHashMap, tmpUrunHashMap, j==1, havaleHashMap);
                    logService.teknosaLogYaz("Teknosa Tur Bitti " + tmpUrunHashMap.size() + " - " + tknKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.teknosaLogYaz("TKN Searcher Thread Hata Aldım.");
            }
        }
    }

    public HashMap<String, String> havaleHashMapDoldur() {
        HashMap<String, String> havaleHashMap = new HashMap<>();
        havaleHashMap.put("115042222", "115042222"); //DYSON V15
        havaleHashMap.put("115042221", "115042221"); //DYSON V12
        return havaleHashMap;
    }
}
