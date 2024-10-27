package com.app.fku.amazon.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AMZ_FIYAT")
public class AmzFiyat extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AMZ_FIYAT")
    @SequenceGenerator(name="SEQ_AMZ_FIYAT", sequenceName = "SEQ_AMZ_FIYAT", allocationSize = 1)
    Long id;

    @JoinColumn(name = "URUN_ID")
    @ManyToOne
    AmzUrun amzUrun;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AmzUrun getAmzUrun() {
        return amzUrun;
    }

    public void setAmzUrun(AmzUrun amzUrun) {
        this.amzUrun = amzUrun;
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
}
