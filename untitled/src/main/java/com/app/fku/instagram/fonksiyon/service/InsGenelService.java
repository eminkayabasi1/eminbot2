package com.app.fku.instagram.fonksiyon.service;

import org.jsoup.nodes.Document;

public interface InsGenelService {

    Document urleGit(String url) throws Exception;

    String headerGetir();
}
