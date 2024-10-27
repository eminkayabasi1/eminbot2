package com.app.fku.hepsiburada.fonksiyon.threadclass;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.hepsiburada.entity.HbKahraman;
import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.fonksiyon.impl.HbUrunToplaFonksiyon;
import com.app.fku.hepsiburada.repository.HbKahramanRepository;
import com.app.fku.hepsiburada.repository.HbKategoriRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HbNewSearcherThread implements Runnable {

    public static Integer threadSirasi;
    public static Long kategoriId;
    public static HbKategoriRepository hbKategoriRepository;
    public static HbKahramanRepository hbKahramanRepository;
    public static HbUrunToplaFonksiyon hbUrunToplaFonksiyon;
    public static LogService logService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;

        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        HashMap<String, String> yasakliUrunHashMap = new HashMap<>();
        yasakliUrunDoldur(yasakliUrunHashMap);

        HbKategori hbKategori = hbKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                HashMap<String, GenelUrunModel> tmpUrunHashMap = new HashMap<>();
                HashMap<String, List<HbKahraman>> kahramanHashMap = kahramanHashMapDoldur();
                for (int i = 0; i < 20; i++) {
                    tmpUrunHashMap = hbUrunToplaFonksiyon.menudenUrunBul(hbKategori, 1, null, urunHashMap, tmpUrunHashMap, j<10, yasakliUrunHashMap, kahramanHashMap);
                    logService.hepsiBuradaLogYaz("HB New Searcher Tur Bitti " + tmpUrunHashMap.size() + " - " + hbKategori.getKategoriAdi() + " : " + sdf.format(new Date()));
                    urunHashMap.putAll(tmpUrunHashMap);
                }
                urunHashMap = tmpUrunHashMap;
            } catch (Exception e) {
                logService.hepsiBuradaLogYaz("HB New Searcher Thread hata aldım: " + sdf.format(new Date()));
            }
        }
    }

    private void yasakliUrunDoldur(HashMap<String, String> yasakliUrunHashMap) {
        /**
        yasakliUrunHashMap.put("Jbl Tune 230NC Kulakiçi Tws Kulaklık, Krem", "Jbl Tune 230NC Kulakiçi Tws Kulaklık, Krem");
        yasakliUrunHashMap.put("Hytech HY-XBK589 Kırmızı Tf Kartlı Mıknatıslı Bluetooth Spor Kulak Içi Kulaklık Kırmızı", "Hytech HY-XBK589 Kırmızı Tf Kartlı Mıknatıslı Bluetooth Spor Kulak Içi Kulaklık Kırmızı");
        yasakliUrunHashMap.put("Jbl Tune 230NC Kulakiçi Tws Kulaklık, Beyaz", "Jbl Tune 230NC Kulakiçi Tws Kulaklık, Beyaz");
        yasakliUrunHashMap.put("Belkin Soundform True Kablosuz Kulak Içi Kulaklık", "Belkin Soundform True Kablosuz Kulak Içi Kulaklık");
        yasakliUrunHashMap.put("Ugreen Hitune T2 Bluetooth 5.0 Kablosuz Tws Kulaklık Beyaz", "Ugreen Hitune T2 Bluetooth 5.0 Kablosuz Tws Kulaklık Beyaz");
        yasakliUrunHashMap.put("Nespresso Essenza Mini D30 Red Kahve Makinesi, Kırmızı", "Nespresso Essenza Mini D30 Red Kahve Makinesi, Kırmızı");
        yasakliUrunHashMap.put("TCL L7 32 GB (TCL Türkiye Garantili)", "TCL L7 32 GB (TCL Türkiye Garantili)");
        yasakliUrunHashMap.put("Karaca Çaysever Konuşan Çay Makinesi Chrome", "Karaca Çaysever Konuşan Çay Makinesi Chrome");
         */
        yasakliUrunHashMap.put("Schafer Woody Peynir Sunum Seti", "Schafer Woody Peynir Sunum Seti");
        yasakliUrunHashMap.put("Wmf Kevgir Profi Plus", "Wmf Kevgir Profi Plus");
    }

    private HashMap<String, List<HbKahraman>> kahramanHashMapDoldur() {
        HashMap<String, List<HbKahraman>> kahramanHashMap = new HashMap<>();
        List<HbKahraman> hbKahramanList = hbKahramanRepository.findAll();
        for (HbKahraman hbKahraman: hbKahramanList) {
            List<HbKahraman> mevcutList = kahramanHashMap.get(hbKahraman.getHbNo());
            if (mevcutList != null && mevcutList.size() > 0) {
                mevcutList.add(hbKahraman);
                kahramanHashMap.put(hbKahraman.getHbNo(), mevcutList);
            } else {
                List<HbKahraman> yeniList = new ArrayList<>();
                yeniList.add(hbKahraman);
                kahramanHashMap.put(hbKahraman.getHbNo(), yeniList);
            }
        }
        return kahramanHashMap;
    }
}
