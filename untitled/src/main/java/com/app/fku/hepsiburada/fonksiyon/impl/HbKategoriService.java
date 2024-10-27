package com.app.fku.hepsiburada.fonksiyon.impl;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.hepsiburada.model.HbKategoriModel;
import com.app.fku.hepsiburada.model.HbUrunModel;

import java.util.List;

public interface HbKategoriService {

    List<HbKategoriModel> getHbKategoriList();
    SonucModel updateIndirimYuzdesi(HbKategoriModel hbKategoriModel);
}
