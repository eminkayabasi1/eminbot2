package com.app.fku.hepsiburada.model;

import com.app.fku.genel.enums.EvetHayirEnum;

import java.util.Date;

public class HbSepetModel {
    Long hbSepetEklemeId;
    String modelAdi;
    String renk;
    Date sepeteEklenmeTarihi;
    String eklendigiHesap;
    EvetHayirEnum sepeteEklendiMi;

    public Long getHbSepetEklemeId() {
        return hbSepetEklemeId;
    }

    public void setHbSepetEklemeId(Long hbSepetEklemeId) {
        this.hbSepetEklemeId = hbSepetEklemeId;
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

    public Date getSepeteEklenmeTarihi() {
        return sepeteEklenmeTarihi;
    }

    public void setSepeteEklenmeTarihi(Date sepeteEklenmeTarihi) {
        this.sepeteEklenmeTarihi = sepeteEklenmeTarihi;
    }

    public String getEklendigiHesap() {
        return eklendigiHesap;
    }

    public void setEklendigiHesap(String eklendigiHesap) {
        this.eklendigiHesap = eklendigiHesap;
    }

    public EvetHayirEnum getSepeteEklendiMi() {
        return sepeteEklendiMi;
    }

    public void setSepeteEklendiMi(EvetHayirEnum sepeteEklendiMi) {
        this.sepeteEklendiMi = sepeteEklendiMi;
    }
}
