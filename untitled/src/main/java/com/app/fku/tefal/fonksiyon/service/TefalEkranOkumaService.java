package com.app.fku.tefal.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.tefal.entity.TefalKategori;

import java.util.HashMap;

public interface TefalEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(TefalKategori tefalKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
