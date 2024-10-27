package com.app.fku.trendyol.model;

public class TrendyolKategoriModel {
    Long id;
    String kategoriAdi;
    String kategoriKisaAd;
    Double indirimYuzdesi;
    String sayfaAdresi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public String getKategoriKisaAd() {
        return kategoriKisaAd;
    }

    public void setKategoriKisaAd(String kategoriKisaAd) {
        this.kategoriKisaAd = kategoriKisaAd;
    }

    public Double getIndirimYuzdesi() {
        return indirimYuzdesi;
    }

    public void setIndirimYuzdesi(Double indirimYuzdesi) {
        this.indirimYuzdesi = indirimYuzdesi;
    }

    public String getSayfaAdresi() {
        return sayfaAdresi;
    }

    public void setSayfaAdresi(String sayfaAdresi) {
        this.sayfaAdresi = sayfaAdresi;
    }
}
