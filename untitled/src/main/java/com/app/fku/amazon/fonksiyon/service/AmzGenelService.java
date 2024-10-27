package com.app.fku.amazon.fonksiyon.service;

import com.app.fku.amazon.entity.AmzFiyat;
import com.app.fku.amazon.entity.AmzUrun;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;

public interface AmzGenelService {

    Document urleGit(String url) throws Exception;

    void mailGonderimi (AmzUrun amzUrun, AmzFiyat amzFiyat) throws IOException, InterruptedException;
}
