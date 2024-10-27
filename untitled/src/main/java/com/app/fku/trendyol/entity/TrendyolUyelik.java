package com.app.fku.trendyol.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.enums.MailSahipEnum;
import com.app.fku.genel.enums.ThreadTypeEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRENDYOL_UYELIK")
public class TrendyolUyelik extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRENDYOL_UYELIK")
    @SequenceGenerator(name="SEQ_TRENDYOL_UYELIK", sequenceName = "SEQ_TRENDYOL_UYELIK", allocationSize = 1)
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

    @Column(name = "SAHIP")
    @Enumerated(EnumType.STRING)
    private MailSahipEnum mailSahipEnum;

    @Column(name = "SEPETE_EKLENDI_MI")
    EvetHayirEnum sepeteEklendiMi;

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

    public MailSahipEnum getMailSahipEnum() {
        return mailSahipEnum;
    }

    public void setMailSahipEnum(MailSahipEnum mailSahipEnum) {
        this.mailSahipEnum = mailSahipEnum;
    }

    public EvetHayirEnum getSepeteEklendiMi() {
        return sepeteEklendiMi;
    }

    public void setSepeteEklendiMi(EvetHayirEnum sepeteEklendiMi) {
        this.sepeteEklendiMi = sepeteEklendiMi;
    }
}
