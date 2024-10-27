package com.app.fku.trendyol.fonksiyon.threadclass;

import com.app.fku.trendyol.entity.TrendyolUyelik;
import com.app.fku.trendyol.fonksiyon.utils.TrendyolUtilsInterface;
import com.app.fku.trendyol.repository.TrendyolUyelikRepository;

import java.util.List;

public class TrendyolHesapAcThread implements Runnable {
    public static TrendyolUtilsInterface trendyolUtilsInterface;

    public void run() {
        for (int i = 1; i < 1500; i++) {
            try {
                trendyolUtilsInterface.hesapAcma();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
