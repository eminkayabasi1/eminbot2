package com.app.fku.yeniamazon.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.yeniamazon.entity.YeniAmazonKategori;

import java.util.HashMap;

public interface YeniAmazonEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(YeniAmazonKategori yeniAmazonKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi) throws InterruptedException;
}
