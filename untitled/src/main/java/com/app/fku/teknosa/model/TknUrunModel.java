package com.app.fku.teknosa.model;

import java.util.Date;

public class TknUrunModel {
    Long id;
    String tknNo;
    String model;
    TknKategoriModel tknKategoriModel;
    Date kayitTarihi;
    Date guncellemeTarihi;
    boolean kontrolEdilsinMi;
    Double beklenenFiyat;
    Double minFiyat;
    Double guncelFiyat;
    String urunUrl;
    Long fiyatId;
    Double yeniBeklenenFiyat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTknNo() {
        return tknNo;
    }

    public void setTknNo(String tknNo) {
        this.tknNo = tknNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public TknKategoriModel getTknKategoriModel() {
        return tknKategoriModel;
    }

    public void setTknKategoriModel(TknKategoriModel tknKategoriModel) {
        this.tknKategoriModel = tknKategoriModel;
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

    public boolean isKontrolEdilsinMi() {
        return kontrolEdilsinMi;
    }

    public void setKontrolEdilsinMi(boolean kontrolEdilsinMi) {
        this.kontrolEdilsinMi = kontrolEdilsinMi;
    }

    public Double getBeklenenFiyat() {
        return beklenenFiyat;
    }

    public void setBeklenenFiyat(Double beklenenFiyat) {
        this.beklenenFiyat = beklenenFiyat;
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

    public String getUrunUrl() {
        return urunUrl;
    }

    public void setUrunUrl(String urunUrl) {
        this.urunUrl = urunUrl;
    }

    public Long getFiyatId() {
        return fiyatId;
    }

    public void setFiyatId(Long fiyatId) {
        this.fiyatId = fiyatId;
    }

    public Double getYeniBeklenenFiyat() {
        return yeniBeklenenFiyat;
    }

    public void setYeniBeklenenFiyat(Double yeniBeklenenFiyat) {
        this.yeniBeklenenFiyat = yeniBeklenenFiyat;
    }
}
