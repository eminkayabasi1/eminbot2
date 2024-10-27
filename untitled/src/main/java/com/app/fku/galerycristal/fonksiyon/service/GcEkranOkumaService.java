package com.app.fku.galerycristal.fonksiyon.service;

import com.app.fku.galerycristal.entity.GcKategori;
import com.app.fku.genel.model.GenelUrunModel;

import java.util.HashMap;

public interface GcEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(GcKategori gcKategori, int hataCount, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
