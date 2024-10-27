package com.app.fku.vatan.model;

public class VatanUrunModel {
    private String url;
    private String urunId;
    private String urunAdi;
    private Long fiyat;
    private Boolean geceyeOzelMi;
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

    public Long getFiyat() {
        return fiyat;
    }

    public void setFiyat(Long fiyat) {
        this.fiyat = fiyat;
    }

    public Boolean getGeceyeOzelMi() {
        return geceyeOzelMi;
    }

    public void setGeceyeOzelMi(Boolean geceyeOzelMi) {
        this.geceyeOzelMi = geceyeOzelMi;
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
