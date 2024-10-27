package com.app.fku.migrossanal.fonksiyon.threadclass;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.migrossanal.entity.MsKategori;
import com.app.fku.migrossanal.fonksiyon.service.MsEkranOkumaService;
import com.app.fku.migrossanal.repository.MsKategoriRepository;

import java.util.HashMap;

public class MsSearcherThread implements Runnable {

    public static MsEkranOkumaService msEkranOkumaService;
    public static MsKategoriRepository msKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        MsKategori msKategori = msKategoriRepository.findById(localKategoriId).orElse(null);
        for (int j = 1; j > 0; j++) {
            try {
                urunHashMap = msEkranOkumaService.kategoriIleUrunBul(msKategori, 0, 1, 1, urunHashMap, null, j < 10);
                System.out.println("MigrosSanal Tur Bitti " + urunHashMap.size() + " - " + msKategori.getKategoriAdi());
            } catch (Exception e) {
                System.out.println("MigrosSanal Searcher Thread Hata Aldım.");
            }
        }
    }
}
