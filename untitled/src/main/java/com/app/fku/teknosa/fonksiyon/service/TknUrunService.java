package com.app.fku.teknosa.fonksiyon.service;

import com.app.fku.teknosa.model.TknUrunModel;
import com.app.fku.teknosa.model.TknUrunTopluModel;

import java.util.List;

public interface TknUrunService {

    List<TknUrunModel> son25UrunGetir();

    String urunBeklenenFiyatEksilt(Long fiyatId, Double eksilecekMiktar);

    String urunBeklenenFiyatGuncelle(Long fiyatId, Double yeniBeklenenFiyat);

    String urunuEngelle(Long fiyatId);

    String topluUrunBeklenenFiyatEksilt(TknUrunTopluModel tknUrunTopluModel);

    String topluUrunBeklenenFiyatCarp(TknUrunTopluModel tknUrunTopluModel);
}
