package com.app.fku.amazon.fonksiyon.threadclass;

import com.app.fku.amazon.entity.AmzKategori;
import com.app.fku.amazon.fonksiyon.service.AmzEkranOkumaService;
import com.app.fku.amazon.repository.AmzKategoriRepository;

import java.util.List;

public class AmzSearcherThread implements Runnable {

    public static AmzEkranOkumaService amzEkranOkumaService;
    public static AmzKategoriRepository amzKategoriRepository;
    public static Integer threadSirasi;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        for (int j = 1; j > 0; j++) {
            try {
                System.out.println("AMZ Searcher Thread : Thread Sirasi =" + localThreadSirasi + ", Kaçıncı Tur =" + j);
                List<AmzKategori> amzKategoriList = amzKategoriRepository.findAllByOrderById();
                for (AmzKategori amzKategori: amzKategoriList) {
                    if (amzKategori.getId() == 3) {
                        continue;
                    }
                    amzEkranOkumaService.kategoriIleUrunBul(amzKategori, 0, 1, 0);
                }
            } catch (Exception e) {
                System.out.println("AMZ Searcher Thread Hata Aldım.");
            }
        }
    }
}
