package com.app.fku.genel.model;

import java.util.Date;

public class GenelUrunModel {
    Double fiyat;
    Double minFiyat;
    Date minFiyatTarihi;
    String model;
    Date sonMesajTarihi;
    String urunId;
    Long eskiFiyat;

    public Double getFiyat() {
        return fiyat;
    }

    public void setFiyat(Double fiyat) {
        this.fiyat = fiyat;
    }

    public Double getMinFiyat() {
        return minFiyat;
    }

    public void setMinFiyat(Double minFiyat) {
        this.minFiyat = minFiyat;
    }

    public Date getMinFiyatTarihi() {
        return minFiyatTarihi;
    }

    public void setMinFiyatTarihi(Date minFiyatTarihi) {
        this.minFiyatTarihi = minFiyatTarihi;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getSonMesajTarihi() {
        return sonMesajTarihi;
    }

    public void setSonMesajTarihi(Date sonMesajTarihi) {
        this.sonMesajTarihi = sonMesajTarihi;
    }

    public String getUrunId() {
        return urunId;
    }

    public void setUrunId(String urunId) {
        this.urunId = urunId;
    }

    public Long getEskiFiyat() {
        return eskiFiyat;
    }

    public void setEskiFiyat(Long eskiFiyat) {
        this.eskiFiyat = eskiFiyat;
    }
}
