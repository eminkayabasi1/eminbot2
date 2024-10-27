package com.app.fku.bim.fonksiyon.service;

import com.app.fku.bim.entity.BimKategori;

import java.io.IOException;
import java.util.HashMap;

public interface BimUrunToplaFonksiyon {
    HashMap<String, Long> menudenUrunBul(BimKategori bimKategori, HashMap<String, Long> urunHashMap, boolean ilkMi, HashMap<String, Long> yasakliUrunHashMap) throws IOException, InterruptedException;
}
