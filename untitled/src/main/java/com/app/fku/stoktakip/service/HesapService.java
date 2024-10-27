package com.app.fku.stoktakip.service;

import com.app.fku.stoktakip.model.HesapModel;

import java.util.List;

public interface HesapService {

    List<HesapModel> getHesapListByMagazaId(Long magazaId) throws Exception;
    HesapModel saveHesap(HesapModel hesapModel) throws Exception;
    void deleteHesap(Long id) throws Exception;

}
