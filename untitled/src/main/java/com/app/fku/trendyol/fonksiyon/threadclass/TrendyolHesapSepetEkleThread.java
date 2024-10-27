package com.app.fku.trendyol.fonksiyon.threadclass;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.trendyol.entity.TrendyolUyelik;
import com.app.fku.trendyol.fonksiyon.utils.TrendyolUtilsInterface;
import com.app.fku.trendyol.repository.TrendyolUyelikRepository;

import java.util.List;

public class TrendyolHesapSepetEkleThread implements Runnable {
    public static TrendyolUtilsInterface trendyolUtilsInterface;
    public static TrendyolUyelikRepository trendyolUyelikRepository;

    public void run() {
        for (int i = 1; i < 1500; i++) {
            try {
                List<TrendyolUyelik> sepeteEklenecekUyelikList = trendyolUyelikRepository.findBySepeteEklendiMi(EvetHayirEnum.HAYIR);

                for (TrendyolUyelik uyelik: sepeteEklenecekUyelikList) {
                    if (trendyolUtilsInterface.uyeligeSepetEkle(uyelik)) {
                        uyelik.setSepeteEklendiMi(EvetHayirEnum.EVET);
                        trendyolUyelikRepository.save(uyelik);
                        trendyolUyelikRepository.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
