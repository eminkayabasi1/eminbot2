package com.app.fku.hepsiburada.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "HB_FIYAT")
public class HbFiyat extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HB_FIYAT_1")
    @SequenceGenerator(name="SEQ_HB_FIYAT_1", sequenceName = "SEQ_HB_FIYAT_1", allocationSize = 1)
    Long id;

    @JoinColumn(name = "URUN_ID")
    @ManyToOne
    HbUrun hbUrun;

    @Column(name = "FIYAT")
    Double fiyat;

    @Column(name = "ESKI_BEKLENEN_FIYAT")
    Double eskiBeklenenFiyat;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kayitTarihi;

    @Column(name = "GUNCELLEME_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date guncellemeTarihi;

    @Column(name = "HOSTNAME")
    private String hostname;

    @JoinColumn(name = "HB_SEPET_EKLEME_ID")
    @ManyToOne
    HbSepetEkleme hbSepetEkleme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HbUrun getHbUrun() {
        return hbUrun;
    }

    public void setHbUrun(HbUrun hbUrun) {
        this.hbUrun = hbUrun;
    }

    public Double getFiyat() {
        return fiyat;
    }

    public void setFiyat(Double fiyat) {
        this.fiyat = fiyat;
    }

    public Double getEskiBeklenenFiyat() {
        return eskiBeklenenFiyat;
    }

    public void setEskiBeklenenFiyat(Double eskiBeklenenFiyat) {
        this.eskiBeklenenFiyat = eskiBeklenenFiyat;
    }

    public Date getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(Date kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public Date getGuncellemeTarihi() {
        return guncellemeTarihi;
    }

    public void setGuncellemeTarihi(Date guncellemeTarihi) {
        this.guncellemeTarihi = guncellemeTarihi;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public HbSepetEkleme getHbSepetEkleme() {
        return hbSepetEkleme;
    }

    public void setHbSepetEkleme(HbSepetEkleme hbSepetEkleme) {
        this.hbSepetEkleme = hbSepetEkleme;
    }
}
