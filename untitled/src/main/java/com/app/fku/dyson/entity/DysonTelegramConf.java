package com.app.fku.dyson.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "DYSON_TELEGRAM_CONF")
public class DysonTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DYSON_TELEGRAM_CONF")
    @SequenceGenerator(name="SEQ_DYSON_TELEGRAM_CONF", sequenceName = "SEQ_DYSON_TELEGRAM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    DysonKategori dysonKategori;

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

    public DysonKategori getDysonKategori() {
        return dysonKategori;
    }

    public void setDysonKategori(DysonKategori dysonKategori) {
        this.dysonKategori = dysonKategori;
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
