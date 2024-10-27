package com.app.fku.n11.model;

public class N11UrunModel {
    private String url;
    private String urunId;
    private String urunAdi;
    private String satici;
    private Long fiyat;
    private String kategoriAdi;
    private Double indirimOrani;

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

    public String getSatici() {
        return satici;
    }

    public void setSatici(String satici) {
        this.satici = satici;
    }

    public Long getFiyat() {
        return fiyat;
    }

    public void setFiyat(Long fiyat) {
        this.fiyat = fiyat;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public Double getIndirimOrani() {
        return indirimOrani;
    }

    public void setIndirimOrani(Double indirimOrani) {
        this.indirimOrani = indirimOrani;
    }
}
