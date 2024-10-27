package com.app.fku.amazon.fonksiyon.service;

import java.io.IOException;

public interface AmzWorkerService {

    void ekrandanFiyatOku(String asinNo, int hataCount) throws InterruptedException;
    void ekrandanFiyatOkuHtmlUnit(String asinNo, int hataCount) throws InterruptedException, IOException;
}
