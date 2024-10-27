package com.app.fku.dyson.fonksiyon.service;

import com.app.fku.dyson.entity.DysonKategori;
import com.app.fku.genel.model.GenelUrunModel;

import java.util.HashMap;

public interface DysonEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(DysonKategori dysonKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
