package com.app.fku.teknosa.model;

import com.app.fku.genel.enums.EvetHayirEnum;

public class TknIndirimSorguModel {
    TknKategoriModel tknKategoriModel;
    Long kayitSayisi;
    EvetHayirEnum yeniKayitOlsunMu;
    EvetHayirEnum guncelIndirimlerMi;

    public TknKategoriModel getTknKategoriModel() {
        return tknKategoriModel;
    }

    public void setTknKategoriModel(TknKategoriModel tknKategoriModel) {
        this.tknKategoriModel = tknKategoriModel;
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
