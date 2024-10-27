package com.app.fku.hepsiburada.fonksiyon.impl;

import com.app.fku.hepsiburada.entity.HbKategori;

public interface HbSearcherService {
    boolean menudenUrunBul(HbKategori hbKategori, Long pageNumber, Long maxPageNumber, int siralamaDeger) throws InterruptedException;
}
