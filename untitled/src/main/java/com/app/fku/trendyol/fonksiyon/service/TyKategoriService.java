package com.app.fku.trendyol.fonksiyon.service;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.trendyol.model.TrendyolKategoriModel;

import java.util.List;

public interface TyKategoriService {

    List<TrendyolKategoriModel> getTyKategoriList();
    SonucModel updateIndirimYuzdesi(TrendyolKategoriModel trendyolKategoriModel);
}
