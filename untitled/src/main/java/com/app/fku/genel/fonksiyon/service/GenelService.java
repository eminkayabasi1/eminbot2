package com.app.fku.genel.fonksiyon.service;

import com.app.fku.genel.enums.TelegramEnum;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;

public interface GenelService {

    String getHostName() throws UnknownHostException;

    Document urleGit(String url) throws Exception;

    String yeniMailAdiUret();

    String yeniAdUret();

    String yeniSoyadUret();

    void telegramMesajGonder(String mesaj, String chatId, String urunId, String token) throws IOException, InterruptedException;

    void telegramBombaGonder(String mesaj, String urunId) throws IOException, InterruptedException;

    void telegramFiyatHatasiGonder(String mesaj, String urunId) throws IOException, InterruptedException;

    void telegramDysonGonder(String mesaj, String urunId) throws IOException, InterruptedException;

    void telegramFritozGonder(String mesaj, String urunId) throws IOException, InterruptedException;

    void telegramFisslerGonder(String mesaj, String urunId) throws IOException, InterruptedException;

    void a101TelegramHDDGonder(String mesaj, String urunId) throws IOException, InterruptedException;

    void telegramPlayStationGonder(String mesaj, String urunId) throws IOException, InterruptedException;

    void telegramIphone14Gonder(String mesaj, String urunId) throws IOException, InterruptedException;

    void telegramKahramanGonder(String mesaj, String chatId, String urunId) throws IOException, InterruptedException;

    void telegramHbSepetGonder(String mesaj, String urunId) throws IOException, InterruptedException;
}
