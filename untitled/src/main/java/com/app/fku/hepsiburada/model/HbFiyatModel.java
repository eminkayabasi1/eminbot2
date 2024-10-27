package com.app.fku.hepsiburada.model;

public class HbFiyatModel {
    Long id;
    HbUrunModel hbUrunModel;
    Long fiyat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HbUrunModel getHbUrunModel() {
        return hbUrunModel;
    }

    public void setHbUrunModel(HbUrunModel hbUrunModel) {
        this.hbUrunModel = hbUrunModel;
    }

    public Long getFiyat() {
        return fiyat;
    }

    public void setFiyat(Long fiyat) {
        this.fiyat = fiyat;
    }
}
