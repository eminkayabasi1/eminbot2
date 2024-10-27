package com.app.fku.amazon.fonksiyon.service;

import com.app.fku.amazon.entity.AmzKategori;
import com.app.fku.amazon.entity.AmzUrun;
import com.app.fku.hepsiburada.entity.HbSepetEkleme;

import java.net.UnknownHostException;

public interface AmzEkranOkumaService {

    void kategoriIleUrunBul(AmzKategori amzKategori, int hataCount, int pageNumber, int maxPageNumber) throws InterruptedException;

    void ekrandanFiyatOku(String asinNo);
}
