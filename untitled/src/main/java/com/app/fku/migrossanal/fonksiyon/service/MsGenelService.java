package com.app.fku.migrossanal.fonksiyon.service;

import org.jsoup.nodes.Document;

public interface MsGenelService {

    Document urleGit(String url) throws Exception;

    String headerGetir();
}
