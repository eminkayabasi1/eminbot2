package com.app.fku.genel.model;

public class LinkModel {
    private String url;
    private Double indirimOrani;
    private String kategoriAdi;
    private Long telegramChatId;

    public LinkModel(String url, Double indirimOrani) {
        this.url = url;
        this.indirimOrani = indirimOrani;
    }

    public LinkModel(String url, Double indirimOrani, String kategoriAdi) {
        this.url = url;
        this.indirimOrani = indirimOrani;
        this.kategoriAdi = kategoriAdi;
    }

    public LinkModel(String url, Double indirimOrani, Long telegramChatId) {
        this.url = url;
        this.indirimOrani = indirimOrani;
        this.telegramChatId = telegramChatId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getIndirimOrani() {
        return indirimOrani;
    }

    public void setIndirimOrani(Double indirimOrani) {
        this.indirimOrani = indirimOrani;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(Long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }
}