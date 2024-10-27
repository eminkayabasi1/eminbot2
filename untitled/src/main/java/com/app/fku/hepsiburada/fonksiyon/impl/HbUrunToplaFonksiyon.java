package com.app.fku.hepsiburada.fonksiyon.impl;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.hepsiburada.entity.HbKahraman;
import com.app.fku.hepsiburada.entity.HbKategori;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface HbUrunToplaFonksiyon {
    HashMap<String, GenelUrunModel> menudenUrunBul(HbKategori hbKategori, Integer page, Integer maxPage, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi, HashMap<String, String> yasakliUrunHashMap, HashMap<String, List<HbKahraman>> kahramanHashMap) throws IOException, InterruptedException;
}
