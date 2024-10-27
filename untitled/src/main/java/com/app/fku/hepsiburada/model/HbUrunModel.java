package com.app.fku.hepsiburada.model;

import java.util.Date;

public class HbUrunModel {
    Long id;
    String hbNo;
    String marka;
    String model;
    String hafiza;
    String renk;
    Double beklenenFiyat;
    Double minFiyat;
    Date minFiyatTarihi;
    Double guncelFiyat;
    Double yeniBeklenenFiyat;
    Long fiyatId;
    Date kayitTarihi;
    String urunUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHbNo() {
        return hbNo;
    }

    public void setHbNo(String hbNo) {
        this.hbNo = hbNo;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getHafiza() {
        return hafiza;
    }

    public void setHafiza(String hafiza) {
        this.hafiza = hafiza;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
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

    public Date getMinFiyatTarihi() {
        return minFiyatTarihi;
    }

    public void setMinFiyatTarihi(Date minFiyatTarihi) {
        this.minFiyatTarihi = minFiyatTarihi;
    }

    public Double getGuncelFiyat() {
        return guncelFiyat;
    }

    public void setGuncelFiyat(Double guncelFiyat) {
        this.guncelFiyat = guncelFiyat;
    }

    public Double getYeniBeklenenFiyat() {
        return yeniBeklenenFiyat;
    }

    public void setYeniBeklenenFiyat(Double yeniBeklenenFiyat) {
        this.yeniBeklenenFiyat = yeniBeklenenFiyat;
    }

    public Long getFiyatId() {
        return fiyatId;
    }

    public void setFiyatId(Long fiyatId) {
        this.fiyatId = fiyatId;
    }

    public Date getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(Date kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public String getUrunUrl() {
        return urunUrl;
    }

    public void setUrunUrl(String urunUrl) {
        this.urunUrl = urunUrl;
    }
}
