package com.app.fku.yeniamazon.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "YENI_AMAZON_TELEGRAM_CONF")
public class YeniAmazonTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_YENI_AMAZON_TELEGRAM_CONF")
    @SequenceGenerator(name="SEQ_YENI_AMAZON_TELEGRAM_CONF", sequenceName = "SEQ_YENI_AMAZON_TELEGRAM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    YeniAmazonKategori yeniAmazonKategori;

    @Column(name = "TELEGRAM_ID")
    String telegramId;

    @Column(name = "ACIKLAMA")
    String aciklama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YeniAmazonKategori getVatanKategori() {
        return yeniAmazonKategori;
    }

    public void setVatanKategori(YeniAmazonKategori yeniAmazonKategori) {
        this.yeniAmazonKategori = yeniAmazonKategori;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
