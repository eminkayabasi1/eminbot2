package com.app.fku.trendyol.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.trendyol.entity.TrendyolKahraman;
import com.app.fku.trendyol.entity.TrendyolKategori;
import com.app.fku.trendyol.entity.TrendyolUrun;
import com.app.fku.trendyol.fonksiyon.service.TrendyolUrunToplaFonksiyon;
import com.app.fku.trendyol.repository.TrendyolKahramanRepository;
import com.app.fku.trendyol.repository.TrendyolKategoriRepository;
import com.app.fku.trendyol.repository.TrendyolUrunRepository;
import com.app.fku.trendyol.repository.TyIstatistikRepository;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TrendyolSearcherThread implements Runnable {

    public static Integer threadSirasi;
    public static Long kategoriId;
    public static TrendyolKategoriRepository trendyolKategoriRepository;
    public static TrendyolKahramanRepository trendyolKahramanRepository;
    //public static TrendyolUrunRepository trendyolUrunRepository;
    public static TrendyolUrunToplaFonksiyon trendyolUrunToplaFonksiyon;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;

        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        HashMap<String, String> yasakliUrunHashMap = new HashMap<>();
        yasakliUrunDoldur(yasakliUrunHashMap);

        TrendyolKategori tyKategori = trendyolKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                HashMap<String, List<TrendyolKahraman>> kahramanHashMap = kahramanHashMapDoldur();
                for (int i = 0; i < 20; i++) {
                    urunHashMap = trendyolUrunToplaFonksiyon.menudenUrunBul(tyKategori, 1, 2, 0, urunHashMap, tmpUrunHashMap, yasakliUrunHashMap, j<5, kahramanHashMap);
                    logService.trendyolLogYaz("TY Searcher " + tyKategori.getKategoriAdi() + " Thread Bitti - " + urunHashMap.size() + ": " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                    Random rand = new Random();
                    int sleepRand = rand.nextInt(6);
                    TimeUnit.SECONDS.sleep(sleepRand);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.trendyolLogYaz("Ty Searcher Thread hata aldım: " + sdf.format(new Date()) + " - " + tyKategori.getKategoriAdi());
            }
        }
    }

    private void yasakliUrunDoldur(HashMap<String, String> yasakliUrunHashMap) {
        yasakliUrunHashMap.put("184544045", "184544045");
        yasakliUrunHashMap.put("376203873", "376203873");
        yasakliUrunHashMap.put("235502849", "235502849");
        yasakliUrunHashMap.put("375424569", "375424569");
        yasakliUrunHashMap.put("154842325", "154842325");
        yasakliUrunHashMap.put("152718658", "152718658");
    }

    private HashMap<String, List<TrendyolKahraman>> kahramanHashMapDoldur() {
        HashMap<String, List<TrendyolKahraman>> kahramanHashMap = new HashMap<>();
        List<TrendyolKahraman> trendyolKahramanList = trendyolKahramanRepository.findAll();
        for (TrendyolKahraman trendyolKahraman: trendyolKahramanList) {
            List<TrendyolKahraman> mevcutList = kahramanHashMap.get(trendyolKahraman.getTyNo());
            if (mevcutList != null && mevcutList.size() > 0) {
                mevcutList.add(trendyolKahraman);
                kahramanHashMap.put(trendyolKahraman.getTyNo(), mevcutList);
            } else {
                List<TrendyolKahraman> yeniList = new ArrayList<>();
                yeniList.add(trendyolKahraman);
                kahramanHashMap.put(trendyolKahraman.getTyNo(), yeniList);
            }
        }
        return kahramanHashMap;
    }
}
