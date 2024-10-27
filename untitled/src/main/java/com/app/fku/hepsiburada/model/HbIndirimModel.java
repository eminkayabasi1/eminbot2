package com.app.fku.hepsiburada.model;

import java.util.Date;

public class HbIndirimModel {
    Long fiyatKayitId;
    String urunLink;
    String kategori;
    String modelAdi;
    String renk;
    Double minFiyat;
    Double guncelFiyat;
    Double beklenenFiyat;
    Double eskiBeklenenFiyat;
    String minFiyatStr;
    String guncelFiyatStr;
    String beklenenFiyatStr;
    String eskiBeklenenFiyatStr;
    Date guncelFiyatKayitTarihi;

    public Long getFiyatKayitId() {
        return fiyatKayitId;
    }

    public void setFiyatKayitId(Long fiyatKayitId) {
        this.fiyatKayitId = fiyatKayitId;
    }

    public String getUrunLink() {
        return urunLink;
    }

    public void setUrunLink(String urunLink) {
        this.urunLink = urunLink;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getModelAdi() {
        return modelAdi;
    }

    public void setModelAdi(String modelAdi) {
        this.modelAdi = modelAdi;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public Double getMinFiyat() {
        return minFiyat;
    }

    public void setMinFiyat(Double minFiyat) {
        this.minFiyat = minFiyat;
    }

    public Double getGuncelFiyat() {
        return guncelFiyat;
    }

    public void setGuncelFiyat(Double guncelFiyat) {
        this.guncelFiyat = guncelFiyat;
    }

    public Double getBeklenenFiyat() {
        return beklenenFiyat;
    }

    public void setBeklenenFiyat(Double beklenenFiyat) {
        this.beklenenFiyat = beklenenFiyat;
    }

    public Date getGuncelFiyatKayitTarihi() {
        return guncelFiyatKayitTarihi;
    }

    public void setGuncelFiyatKayitTarihi(Date guncelFiyatKayitTarihi) {
        this.guncelFiyatKayitTarihi = guncelFiyatKayitTarihi;
    }

    public Double getEskiBeklenenFiyat() {
        return eskiBeklenenFiyat;
    }

    public void setEskiBeklenenFiyat(Double eskiBeklenenFiyat) {
        this.eskiBeklenenFiyat = eskiBeklenenFiyat;
    }

    public String getMinFiyatStr() {
        return minFiyatStr;
    }

    public void setMinFiyatStr(String minFiyatStr) {
        this.minFiyatStr = minFiyatStr;
    }

    public String getGuncelFiyatStr() {
        return guncelFiyatStr;
    }

    public void setGuncelFiyatStr(String guncelFiyatStr) {
        this.guncelFiyatStr = guncelFiyatStr;
    }

    public String getBeklenenFiyatStr() {
        return beklenenFiyatStr;
    }

    public void setBeklenenFiyatStr(String beklenenFiyatStr) {
        this.beklenenFiyatStr = beklenenFiyatStr;
    }

    public String getEskiBeklenenFiyatStr() {
        return eskiBeklenenFiyatStr;
    }

    public void setEskiBeklenenFiyatStr(String eskiBeklenenFiyatStr) {
        this.eskiBeklenenFiyatStr = eskiBeklenenFiyatStr;
    }
}
