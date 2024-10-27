package com.app.fku.hepsiburada.model;

import com.app.fku.genel.enums.EvetHayirEnum;

public class HbIndirimSorguModel {
    HbKategoriModel hbKategoriModel;
    Long kayitSayisi;
    EvetHayirEnum yeniKayitOlsunMu;
    EvetHayirEnum guncelIndirimlerMi;

    public HbKategoriModel getHbKategoriModel() {
        return hbKategoriModel;
    }

    public void setHbKategoriModel(HbKategoriModel hbKategoriModel) {
        this.hbKategoriModel = hbKategoriModel;
    }

    public Long getKayitSayisi() {
        return kayitSayisi;
    }

    public void setKayitSayisi(Long kayitSayisi) {
        this.kayitSayisi = kayitSayisi;
    }

    public EvetHayirEnum getYeniKayitOlsunMu() {
        return yeniKayitOlsunMu;
    }

    public void setYeniKayitOlsunMu(EvetHayirEnum yeniKayitOlsunMu) {
        this.yeniKayitOlsunMu = yeniKayitOlsunMu;
    }

    public EvetHayirEnum getGuncelIndirimlerMi() {
        return guncelIndirimlerMi;
    }

    public void setGuncelIndirimlerMi(EvetHayirEnum guncelIndirimlerMi) {
        this.guncelIndirimlerMi = guncelIndirimlerMi;
    }
}
