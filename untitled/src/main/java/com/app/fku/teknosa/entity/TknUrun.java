package com.app.fku.teknosa.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TKN_URUN")
public class TknUrun extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TKN_URUN")
    @SequenceGenerator(name="SEQ_TKN_URUN", sequenceName = "SEQ_TKN_URUN", allocationSize = 1)
    Long id;

    @Column(name="TKN_NO")
    String tknNo;

    @Column(name="MODEL")
    String model;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    TknKategori tknKategori;

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

    @Column(name = "BEKLENEN_FIYAT")
    Double beklenenFiyat;

    @Column(name = "MIN_FIYAT")
    Double minFiyat;

    @Column(name = "GUNCEL_FIYAT")
    Double guncelFiyat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTknNo() {
        return tknNo;
    }

    public void setTknNo(String tknNo) {
        this.tknNo = tknNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public TknKategori getTknKategori() {
        return tknKategori;
    }

    public void setTknKategori(TknKategori tknKategori) {
        this.tknKategori = tknKategori;
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

    public Double getBeklenenFiyat() {
        return beklenenFiyat;
    }

    public void setBeklenenFiyat(Double beklenenFiyat) {
        this.beklenenFiyat = beklenenFiyat;
    }

    public Double getMinFiyat() {
        return minFiyat;
    }

    public void setMinFiyat(Double minFiyat) {
        this.minFiyat = minFiyat;
    }

    public Double getGuncelFiyat() {
        return guncelFiyat;
    }

    public void setGuncelFiyat(Double guncelFiyat) {
        this.guncelFiyat = guncelFiyat;
    }
}
