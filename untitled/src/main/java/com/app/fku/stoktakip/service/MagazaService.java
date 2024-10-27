package com.app.fku.stoktakip.service;

import com.app.fku.stoktakip.model.MagazaModel;

import java.util.List;

public interface MagazaService {

    List<MagazaModel> getMagazaList();
    MagazaModel saveMagaza(MagazaModel magazaModel) throws Exception;
    void deleteMagaza(Long id) throws Exception;

}
