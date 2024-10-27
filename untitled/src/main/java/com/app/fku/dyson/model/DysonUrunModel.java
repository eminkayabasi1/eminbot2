package com.app.fku.dyson.model;

public class DysonUrunModel {
    private String url;
    private String urunId;
    private String urunAdi;
    private Long fiyat;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrunId() {
        return urunId;
    }

    public void setUrunId(String urunId) {
        this.urunId = urunId;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public Long getFiyat() {
        return fiyat;
    }

    public void setFiyat(Long fiyat) {
        this.fiyat = fiyat;
    }
}