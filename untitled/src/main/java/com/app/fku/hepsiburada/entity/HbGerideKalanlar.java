package com.app.fku.hepsiburada.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "HB_GERIDE_KALANLAR")
public class HbGerideKalanlar {

    @Id
    @Column(name = "TARIH")
    Date tarih;

    @Column(name = "SAYAC")
    private Long sayac;

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public Long getSayac() {
        return sayac;
    }

    public void setSayac(Long sayac) {
        this.sayac = sayac;
    }
}
