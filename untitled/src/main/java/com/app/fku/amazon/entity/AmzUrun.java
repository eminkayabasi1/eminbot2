package com.app.fku.amazon.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.hepsiburada.entity.HbKategori;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AMZ_URUN_YENI")
public class AmzUrun extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AMZ_URUN_YENI")
    @SequenceGenerator(name="SEQ_AMZ_URUN_YENI", sequenceName = "SEQ_AMZ_URUN_YENI", allocationSize = 1)
    Long id;

    @Column(name="ASIN_NO")
    String asinNo;

    @Column(name="MODEL")
    String model;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    AmzKategori amzKategori;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kayitTarihi;

    @Column(name = "GUNCELLEME_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date guncellemeTarihi;

    @Column(name = "SON_KONTROL_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sonKontrolTarihi;

    @Column(name = "KONTROL_EDILSIN_MI")
    boolean kontrolEdilsinMi;

    @Column(name = "MIN_FIYAT")
    Double minFiyat;

    @Column(name = "MIN_FIYAT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date minFiyatTarihi;

    @Column(name = "GUNCEL_FIYAT")
    Double guncelFiyat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsinNo() {
        return asinNo;
    }

    public void setAsinNo(String asinNo) {
        this.asinNo = asinNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public AmzKategori getAmzKategori() {
        return amzKategori;
    }

    public void setAmzKategori(AmzKategori amzKategori) {
        this.amzKategori = amzKategori;
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

    public Date getSonKontrolTarihi() {
        return sonKontrolTarihi;
    }

    public void setSonKontrolTarihi(Date sonKontrolTarihi) {
        this.sonKontrolTarihi = sonKontrolTarihi;
    }

    public boolean isKontrolEdilsinMi() {
        return kontrolEdilsinMi;
    }

    public void setKontrolEdilsinMi(boolean kontrolEdilsinMi) {
        this.kontrolEdilsinMi = kontrolEdilsinMi;
    }

    public Double getMinFiyat() {
        return minFiyat;
    }

    public void setMinFiyat(Double minFiyat) {
        this.minFiyat = minFiyat;
    }

    public Date getMinFiyatTarihi() {
        return minFiyatTarihi;
    }

    public void setMinFiyatTarihi(Date minFiyatTarihi) {
        this.minFiyatTarihi = minFiyatTarihi;
    }

    public Double getGuncelFiyat() {
        return guncelFiyat;
    }

    public void setGuncelFiyat(Double guncelFiyat) {
        this.guncelFiyat = guncelFiyat;
    }
}
