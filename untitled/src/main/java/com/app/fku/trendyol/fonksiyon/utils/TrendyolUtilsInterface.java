package com.app.fku.trendyol.fonksiyon.utils;

import com.app.fku.trendyol.entity.TrendyolKupon;
import com.app.fku.trendyol.entity.TrendyolUyelik;
import com.app.fku.trendyol.entity.TrendyolUyelikEski;

import java.util.List;

public interface TrendyolUtilsInterface {
    List<TrendyolKupon> kuponKontrolEtme(TrendyolUyelik trendyolUyelik) throws InterruptedException;

    List<TrendyolKupon> kuponKontrolEtme(TrendyolUyelikEski trendyolUyelikEski) throws InterruptedException;

    void hesapAcma() throws InterruptedException;

    boolean uyeligeSepetEkle(TrendyolUyelik trendyolUyelik) throws InterruptedException;
}
