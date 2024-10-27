package com.app.fku.hepsiburada.fonksiyon.threadclass;

import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.fonksiyon.impl.HbFiyatToplaFonksiyon;
import com.app.fku.hepsiburada.fonksiyon.impl.HbSearcherService;
import com.app.fku.hepsiburada.repository.HbKategoriRepository;
import com.app.fku.hepsiburada.repository.HbUrunRepository;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class HbSearcherThread implements Runnable {

    public static Integer threadSirasi;
    public static Long kategoriId;
    public static HbKategoriRepository hbKategoriRepository;
    public static HbUrunRepository hbUrunRepository;
    public static HbSearcherService hbSearcherService;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;

        boolean kategoriSiniriVarMi = false;
        HbKategori hbKategori = null;
        if (localKategoriId != null) {
            hbKategori = hbKategoriRepository.findById(localKategoriId).orElse(null);
            if (hbKategori == null) {
                kategoriSiniriVarMi = false;
            } {
                kategoriSiniriVarMi = true;
            }
        } else {
            kategoriSiniriVarMi = false;
        }

        for (int j = 1; j > 0; j++) {
            try {
                if (kategoriSiniriVarMi) {
                    for (int i = 1; i > 0; i++) {
                        System.out.println(" HB Searcher Thread : Thread Sirasi =" + localThreadSirasi + ", Kaçıncı Tur =" + i);
                        hbSearcherService.menudenUrunBul(hbKategori, 1L, 10L, 0);
                        hbSearcherService.menudenUrunBul(hbKategori, 1L, 10L, 1);
                        hbSearcherService.menudenUrunBul(hbKategori, 1L, 10L, 2);
                        hbSearcherService.menudenUrunBul(hbKategori, 1L, 10L, 3);
                        hbSearcherService.menudenUrunBul(hbKategori, 1L, 10L, 4);
                        hbSearcherService.menudenUrunBul(hbKategori, 1L, 10L, 5);
                        hbSearcherService.menudenUrunBul(hbKategori, 1L, 10L, 6);
                        hbSearcherService.menudenUrunBul(hbKategori, 1L, 10L, 7);
                    }
                } else {
                    for (int i = 1; i > 0; i++) {
                        System.out.println("HB Searcher Thread : Thread Sirasi =" + localThreadSirasi + ", Kaçıncı Tur =" + i);
                        List<HbKategori> hbKategoriList = hbKategoriRepository.findAllByOrderById();
                        for (HbKategori kategorim: hbKategoriList) {
                            if (ObjectUtils.isEmpty(kategorim.getSayfaAdresi())) {
                                continue;
                            }
                            hbSearcherService.menudenUrunBul(kategorim, 1L, 10L, 0);
                            hbSearcherService.menudenUrunBul(kategorim, 1L, 10L, 1);
                            hbSearcherService.menudenUrunBul(kategorim, 1L, 10L, 2);
                            hbSearcherService.menudenUrunBul(kategorim, 1L, 10L, 3);
                            hbSearcherService.menudenUrunBul(kategorim, 1L, 10L, 4);
                            hbSearcherService.menudenUrunBul(kategorim, 1L, 10L, 5);
                            hbSearcherService.menudenUrunBul(kategorim, 1L, 10L, 6);
                            hbSearcherService.menudenUrunBul(kategorim, 1L, 10L, 7);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Searcher Thread hata aldım.");
            }
        }
    }
}
