package com.app.fku.yeniamazon.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.genel.enums.EvetHayirEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "YENI_AMAZON_KATEGORI")
public class YeniAmazonKategori extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_YENI_AMAZON_KATEGORI")
    @SequenceGenerator(name="SEQ_YENI_AMAZON_KATEGORI", sequenceName = "SEQ_YENI_AMAZON_KATEGORI", allocationSize = 1)
    Long id;

    @Column(name = "SAYFA_ADRESI")
    String sayfaAdresi;

    @Column(name = "SAYFA_ADRESI_2")
    String sayfaAdresi2;

    @Column(name = "SAYFA_ADRESI_3")
    String sayfaAdresi3;

    @Column(name = "KATEGORI_ADI")
    String kategoriAdi;

    @Column(name = "KATEGORI_KISA_ADI")
    String kategoriKisaAd;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kayitTarihi;

    @Column(name = "GUNCELLEME_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date guncellemeTarihi;

    @Column(name = "INDIRIM_YUZDESI")
    private Double indirimYuzdesi;

    @Column(name = "MIN_INDIRIM")
    private Double minIndirim;

    @Column(name = "MAX_INDIRIM")
    private Double maxIndirim;

    @Column(name = "FILTRELI_MI")
    EvetHayirEnum filtreliMi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSayfaAdresi() {
        return sayfaAdresi;
    }

    public void setSayfaAdresi(String sayfaAdresi) {
        this.sayfaAdresi = sayfaAdresi;
    }

    public String getSayfaAdresi2() {
        return sayfaAdresi2;
    }

    public void setSayfaAdresi2(String sayfaAdresi2) {
        this.sayfaAdresi2 = sayfaAdresi2;
    }

    public String getSayfaAdresi3() {
        return sayfaAdresi3;
    }

    public void setSayfaAdresi3(String sayfaAdresi3) {
        this.sayfaAdresi3 = sayfaAdresi3;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }

    public String getKategoriKisaAd() {
        return kategoriKisaAd;
    }

    public void setKategoriKisaAd(String kategoriKisaAd) {
        this.kategoriKisaAd = kategoriKisaAd;
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

    public Double getIndirimYuzdesi() {
        return indirimYuzdesi;
    }

    public void setIndirimYuzdesi(Double indirimYuzdesi) {
        this.indirimYuzdesi = indirimYuzdesi;
    }

    public Double getMinIndirim() {
        return minIndirim;
    }

    public void setMinIndirim(Double minIndirim) {
        this.minIndirim = minIndirim;
    }

    public Double getMaxIndirim() {
        return maxIndirim;
    }

    public void setMaxIndirim(Double maxIndirim) {
        this.maxIndirim = maxIndirim;
    }

    public EvetHayirEnum getFiltreliMi() {
        return filtreliMi;
    }

    public void setFiltreliMi(EvetHayirEnum filtreliMi) {
        this.filtreliMi = filtreliMi;
    }
}
