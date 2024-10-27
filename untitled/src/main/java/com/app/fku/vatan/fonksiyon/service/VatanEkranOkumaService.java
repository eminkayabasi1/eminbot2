package com.app.fku.vatan.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.vatan.entity.VatanKategori;

import java.util.HashMap;

public interface VatanEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(VatanKategori vatanKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
