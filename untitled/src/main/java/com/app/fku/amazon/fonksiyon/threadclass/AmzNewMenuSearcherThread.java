package com.app.fku.amazon.fonksiyon.threadclass;

import com.app.fku.amazon.entity.AmzKategori;
import com.app.fku.amazon.fonksiyon.service.AmzUrunToplaFonksiyon;
import com.app.fku.amazon.repository.AmzKategoriRepository;
import com.app.fku.genel.model.GenelUrunModel;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AmzNewMenuSearcherThread implements Runnable {

    public static Integer threadSirasi;
    public static Long kategoriId;
    public static AmzKategoriRepository amzKategoriRepository;
    public static AmzUrunToplaFonksiyon amzUrunToplaFonksiyon;

    public void run() {
        Integer localThreadSirasi = threadSirasi;
        Long localKategoriId = kategoriId;

        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        HashMap<String, String> yasakliUrunHashMap = new HashMap<>();
        HashMap<String, Date> mesajHashMap = new HashMap<>();
        yasakliUrunDoldur(yasakliUrunHashMap);

        AmzKategori amzKategori = amzKategoriRepository.findById(localKategoriId).orElse(null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int j = 1; j > 0; j++) {
            try {
                if (j % 2 == 0) {
                    amzUrunToplaFonksiyon.fakeCagri();
                }
                urunHashMap = amzUrunToplaFonksiyon.listedenUrunBul(amzKategori, 1, null, urunHashMap, j < 5, yasakliUrunHashMap, mesajHashMap);
                System.out.println("AMZ New Menu Searcher Tur Bitti " + urunHashMap.size() + " - " + amzKategori.getKategoriAdi() + " - j " + j);
                /**Random rand = new Random();
                int sleepRand = rand.nextInt(2);
                TimeUnit.MINUTES.sleep(sleepRand + 1);*/
            } catch (Exception e) {
                System.out.println("AMZ New Menu Searcher Thread hata aldım: " + amzKategori.getKategoriAdi() + " - " + sdf.format(new Date()));
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                System.out.println(sw.toString());
            }
        }
    }

    private void yasakliUrunDoldur(HashMap<String, String> yasakliUrunHashMap) {
        //yasakliUrunHashMap.put("Jbl Tune 230NC Kulakiçi Tws Kulaklık, Krem", "Jbl Tune 230NC Kulakiçi Tws Kulaklık, Krem");
    }
}
