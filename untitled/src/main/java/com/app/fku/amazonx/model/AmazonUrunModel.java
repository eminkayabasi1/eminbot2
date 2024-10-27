package com.app.fku.amazonx.model;

public class AmazonUrunModel {
    private String asin;
    private String urunAdi;
    private Long fiyat;
    private String kategoriAdi;
    private Double indirimOrani;

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
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