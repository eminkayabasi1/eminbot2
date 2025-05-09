package com.app.fku.teknosa.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "TKN_WORKER_ISTATISTIK")
public class TknWorkerIstatistik extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TKN_WORKER_ISTATISTIK")
    @SequenceGenerator(name="SEQ_TKN_WORKER_ISTATISTIK", sequenceName = "SEQ_TKN_WORKER_ISTATISTIK", allocationSize = 1)
    Long id;

    @Column(name = "HOST_NAME")
    String hostname;

    @Column(name = "YIL")
    Integer yil;

    @Column(name = "AY")
    Integer ay;

    @Column(name = "GUN")
    Integer gun;

    @Column(name = "SAAT")
    Integer saat;

    @Column(name = "SAYAC")
    Long sayac;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getYil() {
        return yil;
    }

    public void setYil(Integer yil) {
        this.yil = yil;
    }

    public Integer getAy() {
        return ay;
    }

    public void setAy(Integer ay) {
        this.ay = ay;
    }

    public Integer getGun() {
        return gun;
    }

    public void setGun(Integer gun) {
        this.gun = gun;
    }

    public Integer getSaat() {
        return saat;
    }

    public void setSaat(Integer saat) {
        this.saat = saat;
    }

    public Long getSayac() {
        return sayac;
    }

    public void setSayac(Long sayac) {
        this.sayac = sayac;
    }
}
