package com.app.fku.mutfakdunyasi.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.mutfakdunyasi.entity.MdKategori;

import java.util.HashMap;

public interface MdEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(MdKategori mdKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
