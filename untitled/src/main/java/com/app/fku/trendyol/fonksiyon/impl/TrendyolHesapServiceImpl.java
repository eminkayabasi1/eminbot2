package com.app.fku.trendyol.fonksiyon.impl;

import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.trendyol.repository.TrendyolKuponRepository;
import com.app.fku.trendyol.repository.TrendyolUyelikRepository;
import com.app.fku.trendyol.fonksiyon.service.TrendyolHesapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrendyolHesapServiceImpl implements TrendyolHesapService {

    @Autowired
    HbGenelService genelService;

    @Autowired
    TrendyolUyelikRepository trendyolUyelikRepository;

    @Autowired
    TrendyolKuponRepository trendyolKuponRepository;

    public void yeniHesapAcma() throws InterruptedException {

    }
}
