package com.app.fku.teknosa.model;

import java.util.Date;

public class TknWorkerIstatistikModel {
    Long id;
    Date tarih;
    String hostname;
    Long sayac;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getSayac() {
        return sayac;
    }

    public void setSayac(Long sayac) {
        this.sayac = sayac;
    }
}
