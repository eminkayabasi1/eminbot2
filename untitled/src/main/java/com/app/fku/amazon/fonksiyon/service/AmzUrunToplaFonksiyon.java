package com.app.fku.amazon.fonksiyon.service;

import com.app.fku.amazon.entity.AmzKategori;
import com.app.fku.genel.model.GenelUrunModel;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public interface AmzUrunToplaFonksiyon {
    HashMap<String, GenelUrunModel> menudenUrunBul(AmzKategori amzKategori, Integer page, Integer maxPage, HashMap<String, GenelUrunModel> urunHashMap, boolean ilkMi, HashMap<String, String> yasakliUrunHashMap, HashMap<String, Date> mesajHashMap) throws IOException, InterruptedException;
    HashMap<String, GenelUrunModel> listedenUrunBul(AmzKategori amzKategori, Integer page, Integer maxPage, HashMap<String, GenelUrunModel> urunHashMap, boolean ilkMi, HashMap<String, String> yasakliUrunHashMap, HashMap<String, Date> mesajHashMap) throws IOException, InterruptedException;
    void fakeCagri() throws Exception;
}
