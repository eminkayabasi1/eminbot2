package com.app.fku.medimarkt.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.medimarkt.entity.MmKategori;

import java.util.HashMap;

public interface MmEkranOkumaService {

    void hesapAcma() throws InterruptedException;

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(MmKategori mmKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
