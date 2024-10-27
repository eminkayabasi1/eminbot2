package com.app.fku.vatan.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.vatan.entity.VatanKategori;
import com.app.fku.vatan.fonksiyon.service.VatanEkranOkumaService;
import com.app.fku.vatan.repository.VatanKategoriRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class VatanSearcherThread implements Runnable {

    public static VatanEkranOkumaService vatanEkranOkumaService;
    public static VatanKategoriRepository vatanKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        VatanKategori vatanKategori = vatanKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = vatanEkranOkumaService.kategoriIleUrunBul(vatanKategori, 0, 1, 1, urunHashMap, tmpUrunHashMap, j==1);
                    logService.vatanLogYaz("VATAN Tur Bitti " + tmpUrunHashMap.size() + " - " + vatanKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.vatanLogYaz("VATAN Searcher Thread Hata Aldım.");
            }
        }
    }
}
