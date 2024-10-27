package com.app.fku.teknosa.model;

import java.util.Date;

public class TknKategoriModel {
    Long id;
    String sayfaAdresi;
    String kategoriAdi;
    String kategoriKisaAd;
    Date kayitTarihi;
    Date guncellemeTarihi;
    Double indirimYuzdesi;
    Double minIndirim;
    Double maxIndirim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSayfaAdresi() {
        return sayfaAdresi;
    }

    public void setSayfaAdresi(String sayfaAdresi) {
        this.sayfaAdresi = sayfaAdresi;
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

    public Date getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(Date kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public Date getGuncellemeTarihi() {
        return guncellemeTarihi;
    }

    public void setGuncellemeTarihi(Date guncellemeTarihi) {
        this.guncellemeTarihi = guncellemeTarihi;
    }

    public Double getIndirimYuzdesi() {
        return indirimYuzdesi;
    }

    public void setIndirimYuzdesi(Double indirimYuzdesi) {
        this.indirimYuzdesi = indirimYuzdesi;
    }

    public Double getMinIndirim() {
        return minIndirim;
    }

    public void setMinIndirim(Double minIndirim) {
        this.minIndirim = minIndirim;
    }

    public Double getMaxIndirim() {
        return maxIndirim;
    }

    public void setMaxIndirim(Double maxIndirim) {
        this.maxIndirim = maxIndirim;
    }
}
