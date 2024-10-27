package com.app.fku.trendyol.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRENDYOL_KUPON")
public class TrendyolKupon extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRENDYOL_KUPON")
    @SequenceGenerator(name="SEQ_TRENDYOL_KUPON", sequenceName = "SEQ_TRENDYOL_KUPON", allocationSize = 1)
    Long id;

    @Column(name = "EPOSTA")
    String eposta;

    @Column(name = "SIFRE")
    String sifre;

    @Column(name = "KUPON_ADI")
    String kuponAdi;

    @Column(name = "MIN_TUTAR")
    Double minTutar;

    @Column(name = "INDIRIM")
    Double indirim;

    @Column(name = "BASLANGIC_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date baslangicTarihi;

    @Column(name = "BITIS_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bitisTarihi;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kayitTarihi;

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

    public String getKuponAdi() {
        return kuponAdi;
    }

    public void setKuponAdi(String kuponAdi) {
        this.kuponAdi = kuponAdi;
    }

    public Double getMinTutar() {
        return minTutar;
    }

    public void setMinTutar(Double minTutar) {
        this.minTutar = minTutar;
    }

    public Double getIndirim() {
        return indirim;
    }

    public void setIndirim(Double indirim) {
        this.indirim = indirim;
    }

    public Date getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(Date baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public Date getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(Date bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public Date getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(Date kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }
}
