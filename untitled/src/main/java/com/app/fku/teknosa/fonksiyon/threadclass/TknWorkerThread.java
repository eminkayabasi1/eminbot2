package com.app.fku.teknosa.fonksiyon.threadclass;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.teknosa.entity.TknKategori;
import com.app.fku.teknosa.entity.TknUrun;
import com.app.fku.teknosa.fonksiyon.service.TknEkranOkumaService;
import com.app.fku.teknosa.repository.TknKategoriRepository;
import com.app.fku.teknosa.repository.TknUrunRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TknWorkerThread implements Runnable {

    public static TknEkranOkumaService tknEkranOkumaService;
    public static TknUrunRepository tknUrunRepository;
    public static Integer workerThreadCount;
    public static Integer threadSirasi;

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Integer localWorkerThreadCount = workerThreadCount;
        Integer localThreadSirasi = threadSirasi;
        HashMap<String, GenelUrunModel> urunHashMap = new HashMap<>();
        for (int j = 1; j > 0; j++) {
            try {
                List<TknUrun> tknUrunList = tknUrunRepository.urunThreadSorgusuKategorisiz(localWorkerThreadCount, localThreadSirasi);
                for (TknUrun tknUrun: tknUrunList) {
                    urunHashMap = tknEkranOkumaService.sayfadanOku(tknUrun, urunHashMap, j==1);
                }
                System.out.println("Teknosa Tur Bitti " + urunHashMap.size() + " - " + tknUrunList.size() + " - " + sdf.format(new Date()));
            } catch (Exception e) {
                System.out.println("TKN Worker Thread Hata Aldım.");
            }
        }
    }
}
