package com.app.fku.hepsiburada.fonksiyon.impl;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.hepsiburada.model.HbUrunModel;

import java.util.List;

public interface HbUrunService {

    List<HbUrunModel> getSon50UrunList();

    List<HbUrunModel> getUrunListByKategori(Long kategoriId);

    SonucModel urunDevreDisiBirak(Long urunId);
}
