package com.app.fku.trendyol.fonksiyon.threadclass;

import com.app.fku.trendyol.entity.TrendyolUyelik;
import com.app.fku.trendyol.entity.TrendyolUyelikEski;
import com.app.fku.trendyol.fonksiyon.utils.TrendyolUtilsInterface;
import com.app.fku.trendyol.repository.TrendyolUyelikEskiRepository;
import com.app.fku.trendyol.repository.TrendyolUyelikRepository;

import java.util.List;

public class TrendyolKuponEskiThread implements Runnable {
    public static TrendyolUyelikEskiRepository trendyolUyelikEskiRepository;
    public static TrendyolUtilsInterface trendyolUtilsInterface;

    public void run() {
        List<TrendyolUyelikEski> trendyolUyelikEskiList = trendyolUyelikEskiRepository.findAllByOrderBySonKontrolTarihi();
        for (TrendyolUyelikEski trendyolUyelikEski: trendyolUyelikEskiList) {
            try {
                trendyolUtilsInterface.kuponKontrolEtme(trendyolUyelikEski);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
