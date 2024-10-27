package com.app.fku.trendyol.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.trendyol.entity.TrendyolKahraman;
import com.app.fku.trendyol.entity.TrendyolKategori;
import com.app.fku.trendyol.entity.TrendyolUrun;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

public interface TrendyolUrunToplaFonksiyon {
    HashMap<String, GenelUrunModel> menudenUrunBul(TrendyolKategori tyKategori, int pageNumber, int maxSayfa, int hataCount, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, HashMap<String, String> yasakliUrunHashMap, Boolean ilkMi, HashMap<String, List<TrendyolKahraman>> kahramanHashMap) throws IOException, InterruptedException;

    HashMap<String, String> yildizKontrol(TrendyolKategori tyKategori, HashMap<String, String> urunHashMap, HashMap<String, String> yeniUrunHashMap, int pageNumber, int maxSayfa) throws Exception;
}
