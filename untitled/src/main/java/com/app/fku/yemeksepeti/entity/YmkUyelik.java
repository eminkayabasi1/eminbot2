package com.app.fku.yemeksepeti.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.genel.enums.MailSahipEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "YMK_UYELIK")
public class YmkUyelik extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_YMK_UYELIK")
    @SequenceGenerator(name="SEQ_YMK_UYELIK", sequenceName = "SEQ_YMK_UYELIK", allocationSize = 1)
    Long id;

    @Column(name = "EPOSTA")
    String eposta;

    @Column(name = "SIFRE")
    String sifre;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    Date kayitTarihi;

    @Column(name = "SAHIP")
    @Enumerated(EnumType.STRING)
    private MailSahipEnum mailSahipEnum;

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

    public MailSahipEnum getMailSahipEnum() {
        return mailSahipEnum;
    }

    public void setMailSahipEnum(MailSahipEnum mailSahipEnum) {
        this.mailSahipEnum = mailSahipEnum;
    }
}
