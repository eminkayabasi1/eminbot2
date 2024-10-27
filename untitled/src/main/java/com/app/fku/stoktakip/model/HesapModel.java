package com.app.fku.stoktakip.model;

public class HesapModel {
    Long id;
    MagazaModel magazaModel;
    String email;
    String sifre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MagazaModel getMagazaModel() {
        return magazaModel;
    }

    public void setMagazaModel(MagazaModel magazaModel) {
        this.magazaModel = magazaModel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}
