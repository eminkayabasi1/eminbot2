package com.app.fku.karaca.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.karaca.entity.KrcKategori;

import java.util.HashMap;

public interface KrcEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(KrcKategori krcKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
