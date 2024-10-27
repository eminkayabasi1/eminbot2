package com.app.fku.medimarkt.fonksiyon.service;

import org.jsoup.nodes.Document;

public interface MmGenelService {

    Document urleGit(String url) throws Exception;

    String headerGetir();
}
