package com.app.fku.migrossanal.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.migrossanal.entity.MsKategori;

import java.util.HashMap;

public interface MsEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(MsKategori msKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
