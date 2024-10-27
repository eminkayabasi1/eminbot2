package com.app.fku.itopya.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.itopya.entity.ItopyaKategori;

import java.io.IOException;
import java.util.HashMap;

public interface ItopyaUrunToplaFonksiyon {
    HashMap<String, GenelUrunModel> menudenUrunBul(ItopyaKategori itopyaKategori, Integer page, Integer maxPage, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws IOException, InterruptedException;
}
