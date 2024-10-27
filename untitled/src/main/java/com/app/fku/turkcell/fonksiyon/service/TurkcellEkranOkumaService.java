package com.app.fku.turkcell.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.turkcell.entity.TurkcellKategori;

import java.util.HashMap;

public interface TurkcellEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(TurkcellKategori turkcellKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
