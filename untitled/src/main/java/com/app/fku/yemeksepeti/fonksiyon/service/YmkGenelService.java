package com.app.fku.yemeksepeti.fonksiyon.service;

import org.jsoup.nodes.Document;

public interface YmkGenelService {

    Document urleGit(String url) throws Exception;

    String headerGetir();
}
