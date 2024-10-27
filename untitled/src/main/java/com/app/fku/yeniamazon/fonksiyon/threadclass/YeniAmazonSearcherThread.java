package com.app.fku.yeniamazon.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.genel.sabitler.Sabitler;
import com.app.fku.yeniamazon.entity.YeniAmazonKategori;
import com.app.fku.yeniamazon.fonksiyon.service.YeniAmazonEkranOkumaService;
import com.app.fku.yeniamazon.repository.YeniAmazonKategoriRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class YeniAmazonSearcherThread implements Runnable {

    public static YeniAmazonEkranOkumaService yeniAmazonEkranOkumaService;
    public static YeniAmazonKategoriRepository yeniAmazonKategoriRepository;
    public static Integer threadSirasi;
    public static Long kategoriId;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        YeniAmazonKategori yeniAmazonKategori = yeniAmazonKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = yeniAmazonEkranOkumaService.kategoriIleUrunBul(yeniAmazonKategori, 0, 1, 1, urunHashMap, tmpUrunHashMap, j==1);
                    logService.yeniAmzLogYaz("Yeni Amazon Tur Bitti " + tmpUrunHashMap.size() + " - " + yeniAmazonKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.yeniAmzLogYaz("Yeni Amazon Searcher Thread Hata Aldım.");
            }
        }
    }
}
