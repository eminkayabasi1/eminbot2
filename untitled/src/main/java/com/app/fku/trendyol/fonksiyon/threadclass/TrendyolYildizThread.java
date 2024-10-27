package com.app.fku.trendyol.fonksiyon.threadclass;

import com.app.fku.trendyol.entity.TrendyolKategori;
import com.app.fku.trendyol.entity.TrendyolUrun;
import com.app.fku.trendyol.fonksiyon.service.TrendyolUrunToplaFonksiyon;
import com.app.fku.trendyol.repository.TrendyolKategoriRepository;
import com.app.fku.trendyol.repository.TrendyolUrunRepository;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TrendyolYildizThread implements Runnable {

    public static Long kategoriId;
    public static TrendyolKategoriRepository trendyolKategoriRepository;
    public static TrendyolUrunToplaFonksiyon trendyolUrunToplaFonksiyon;

    public void run() {
        Long localKategoriId = kategoriId;

        TrendyolKategori tyKategori = trendyolKategoriRepository.findById(localKategoriId).orElse(null);
        HashMap<String, String> urunHashMap = new HashMap<>();
        for (int j = 1; j > 0; j++) {
            try {
                //System.out.println("Yıldızlı " + tyKategori.getKategoriAdi() + " Ürün Sayısı: " + urunHashMap.size() + "\n");
                HashMap<String, String> yeniUrunHashMap = new HashMap<>();
                urunHashMap = trendyolUrunToplaFonksiyon.yildizKontrol(tyKategori, urunHashMap, yeniUrunHashMap, 1, 1);
            } catch (Exception e) {

            }
        }
    }
}
