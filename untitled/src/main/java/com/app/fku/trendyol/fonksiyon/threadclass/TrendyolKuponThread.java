package com.app.fku.trendyol.fonksiyon.threadclass;

import com.app.fku.trendyol.entity.TrendyolUyelik;
import com.app.fku.trendyol.repository.TrendyolUyelikRepository;
import com.app.fku.trendyol.fonksiyon.utils.TrendyolUtilsInterface;

import java.util.List;

public class TrendyolKuponThread implements Runnable {
    public static TrendyolUyelikRepository trendyolUyelikRepository;
    public static TrendyolUtilsInterface trendyolUtilsInterface;

    public void run() {
        List<TrendyolUyelik> trendyolUyelikList = trendyolUyelikRepository.findAllByOrderBySonKontrolTarihi();
        for (TrendyolUyelik trendyolUyelik: trendyolUyelikList) {
            try {
                trendyolUtilsInterface.kuponKontrolEtme(trendyolUyelik);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
