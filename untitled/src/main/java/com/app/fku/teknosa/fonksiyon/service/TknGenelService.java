package com.app.fku.teknosa.fonksiyon.service;

import com.app.fku.teknosa.entity.TknFiyat;
import com.app.fku.teknosa.entity.TknUrun;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;

public interface TknGenelService {

    Document urleGit(String url) throws Exception;

    void mailGonderimi (TknUrun tknUrun, TknFiyat tknFiyat) throws IOException, InterruptedException;
}
