package com.app.fku.hepsiburada.fonksiyon.impl;

import com.app.fku.hepsiburada.entity.HbGerideKalanlar;
import com.app.fku.hepsiburada.model.HbWorkerIstatistikModel;

import java.util.List;

public interface HbWorkerIstatistikService {

    List<HbWorkerIstatistikModel> son25IstatistigiGetir();

    List<HbGerideKalanlar> gerideKalanlarList();
}
