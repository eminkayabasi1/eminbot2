package com.app.fku.trendyol.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRENDYOL_UYELIK_ESKI")
public class TrendyolUyelikEski extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRENDYOL_UYELIK_ESKI")
    @SequenceGenerator(name="SEQ_TRENDYOL_UYELIK_ESKI", sequenceName = "SEQ_TRENDYOL_UYELIK_ESKI", allocationSize = 1)
    Long id;

    @Column(name = "EPOSTA")
    String eposta;

    @Column(name = "SIFRE")
    String sifre;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kayitTarihi;

    @Column(name = "SON_KONTROL_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sonKontrolTarihi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public Date getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(Date kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public Date getSonKontrolTarihi() {
        return sonKontrolTarihi;
    }

    public void setSonKontrolTarihi(Date sonKontrolTarihi) {
        this.sonKontrolTarihi = sonKontrolTarihi;
    }
}
