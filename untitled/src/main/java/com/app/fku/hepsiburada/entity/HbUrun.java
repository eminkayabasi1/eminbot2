package com.app.fku.hepsiburada.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "HB_URUN")
public class HbUrun extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HB_URUN_1")
    @SequenceGenerator(name="SEQ_HB_URUN_1", sequenceName = "SEQ_HB_URUN_1", allocationSize = 1)
    Long id;

    @Column(name="HB_NO")
    String hbNo;

    @Column(name="MARKA")
    String marka;

    @Column(name="MODEL")
    String model;

    @Column(name="HAFIZA")
    String hafiza;

    @Column(name = "RENK")
    String renk;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    HbKategori hbKategori;

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

    @Column(name = "BEKLENEN_FIYAT")
    Double beklenenFiyat;

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

    public String getHbNo() {
        return hbNo;
    }

    public void setHbNo(String hbNo) {
        this.hbNo = hbNo;
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

    public String getHafiza() {
        return hafiza;
    }

    public void setHafiza(String hafiza) {
        this.hafiza = hafiza;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public HbKategori getHbKategori() {
        return hbKategori;
    }

    public void setHbKategori(HbKategori hbKategori) {
        this.hbKategori = hbKategori;
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

    public Date getMinFiyatTarihi() {
        return minFiyatTarihi;
    }

    public void setMinFiyatTarihi(Date minFiyatTarihi) {
        this.minFiyatTarihi = minFiyatTarihi;
    }
}
