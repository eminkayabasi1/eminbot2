package com.app.fku.hepsiburada.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.genel.enums.EvetHayirEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "HB_SEPET_EKLEME")
public class HbSepetEkleme extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HB_SEPET_EKLEME_1")
    @SequenceGenerator(name="SEQ_HB_SEPET_EKLEME_1", sequenceName = "SEQ_HB_SEPET_EKLEME_1", allocationSize = 1)
    Long id;

    @JoinColumn(name = "URUN_ID")
    @ManyToOne
    HbUrun hbUrun;

    @JoinColumn(name = "HEPSI_BURADA_UYELIK_ID")
    @ManyToOne
    HbUyelik hbUyelik;

    @Column(name = "ADET")
    Long adet;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    Date kayitTarihi;

    @Column(name = "SEPETE_EKLENME_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    Date sepeteEklenmeTarihi;

    @Column(name = "EKLENDI_MI")
    EvetHayirEnum eklendiMi;

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

    public HbUyelik getHbUyelik() {
        return hbUyelik;
    }

    public void setHbUyelik(HbUyelik hbUyelik) {
        this.hbUyelik = hbUyelik;
    }

    public Long getAdet() {
        return adet;
    }

    public void setAdet(Long adet) {
        this.adet = adet;
    }

    public Date getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(Date kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public Date getSepeteEklenmeTarihi() {
        return sepeteEklenmeTarihi;
    }

    public void setSepeteEklenmeTarihi(Date sepeteEklenmeTarihi) {
        this.sepeteEklenmeTarihi = sepeteEklenmeTarihi;
    }

    public EvetHayirEnum getEklendiMi() {
        return eklendiMi;
    }

    public void setEklendiMi(EvetHayirEnum eklendiMi) {
        this.eklendiMi = eklendiMi;
    }
}
