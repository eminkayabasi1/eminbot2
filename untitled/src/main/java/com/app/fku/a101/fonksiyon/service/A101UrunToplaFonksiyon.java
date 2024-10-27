package com.app.fku.a101.fonksiyon.service;

import com.app.fku.a101.entity.A101Kategori;

import java.io.IOException;
import java.util.HashMap;

public interface A101UrunToplaFonksiyon {
    HashMap<String, Long> menudenUrunBul(A101Kategori a101Kategori, Integer page, Integer maxPage, HashMap<String, Long> urunHashMap, HashMap<String, Long> yeniUrunHashMap, boolean ilkMi) throws IOException, InterruptedException;
}
