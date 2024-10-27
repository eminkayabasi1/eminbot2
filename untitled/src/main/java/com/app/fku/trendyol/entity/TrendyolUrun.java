package com.app.fku.trendyol.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TY_URUN")
public class TrendyolUrun extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TY_URUN")
    @SequenceGenerator(name="SEQ_TY_URUN", sequenceName = "SEQ_TY_URUN", allocationSize = 1)
    Long id;

    @Column(name="TY_NO")
    String tyNo;

    @Column(name="MARKA")
    String marka;

    @Column(name="MODEL")
    String model;

    @Column(name = "RENK")
    String renk;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    TrendyolKategori tyKategori;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kayitTarihi;

    @Column(name = "GUNCELLEME_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date guncellemeTarihi;

    @Column(name = "KONTROL_EDILSIN_MI")
    boolean kontrolEdilsinMi;

    @Column(name = "SON_FIYAT_KONTROL_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sonFiyatKontrolTarihi;

    @Column(name = "GUNCEL_FIYAT")
    Double guncelFiyat;

    @Column(name = "MIN_FIYAT")
    Double minFiyat;

    @Column(name = "MIN_FIYAT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date minFiyatTarihi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTyNo() {
        return tyNo;
    }

    public void setTyNo(String tyNo) {
        this.tyNo = tyNo;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public TrendyolKategori getTyKategori() {
        return tyKategori;
    }

    public void setTyKategori(TrendyolKategori tyKategori) {
        this.tyKategori = tyKategori;
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

    public boolean isKontrolEdilsinMi() {
        return kontrolEdilsinMi;
    }

    public void setKontrolEdilsinMi(boolean kontrolEdilsinMi) {
        this.kontrolEdilsinMi = kontrolEdilsinMi;
    }

    public Date getSonFiyatKontrolTarihi() {
        return sonFiyatKontrolTarihi;
    }

    public void setSonFiyatKontrolTarihi(Date sonFiyatKontrolTarihi) {
        this.sonFiyatKontrolTarihi = sonFiyatKontrolTarihi;
    }

    public Double getGuncelFiyat() {
        return guncelFiyat;
    }

    public void setGuncelFiyat(Double guncelFiyat) {
        this.guncelFiyat = guncelFiyat;
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
}
