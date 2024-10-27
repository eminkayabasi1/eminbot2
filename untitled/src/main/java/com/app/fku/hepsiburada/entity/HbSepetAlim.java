package com.app.fku.hepsiburada.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.genel.enums.ThreadTypeEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "HB_SEPET_ALIM")
public class HbSepetAlim extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HB_SEPET_ALIM")
    @SequenceGenerator(name="SEQ_HB_SEPET_ALIM", sequenceName = "SEQ_HB_SEPET_ALIM", allocationSize = 1)
    Long id;

    @Column(name = "EPOSTA")
    String eposta;

    @Column(name = "HB_NO")
    String hbNo;

    @Column(name = "LINK")
    String link;

    @Column(name = "ADET")
    Integer adet;

    @Column(name = "DURUM")
    Integer durum;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kayitTarihi;

    @Column(name = "SIPARIS_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date siparisTarihi;

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

    public String getHbNo() {
        return hbNo;
    }

    public void setHbNo(String hbNo) {
        this.hbNo = hbNo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getAdet() {
        return adet;
    }

    public void setAdet(Integer adet) {
        this.adet = adet;
    }

    public Integer getDurum() {
        return durum;
    }

    public void setDurum(Integer durum) {
        this.durum = durum;
    }

    public Date getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(Date kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public Date getSiparisTarihi() {
        return siparisTarihi;
    }

    public void setSiparisTarihi(Date siparisTarihi) {
        this.siparisTarihi = siparisTarihi;
    }
}
