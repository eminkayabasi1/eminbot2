package com.app.fku.teknosa.fonksiyon.service;

import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.teknosa.entity.TknKategori;
import com.app.fku.teknosa.entity.TknUrun;

import java.net.UnknownHostException;
import java.util.HashMap;

public interface TknEkranOkumaService {

    HashMap<String, GenelUrunModel> kategoriIleUrunBul(TknKategori tknKategori, int hataCount, int pageNumber, int maxPageNumber, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi, HashMap<String, String> havaleHashMap) throws InterruptedException;

    HashMap<String, GenelUrunModel> sayfadanOku(TknUrun tknUrun, HashMap<String, GenelUrunModel> hashMap, boolean ilkMi) throws InterruptedException;
}
