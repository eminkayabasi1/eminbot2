package com.app.fku.galerycristal.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "GC_TELEGRAM_CONF")
public class GcTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GC_TELEGRAM_CONF")
    @SequenceGenerator(name="SEQ_GC_TELEGRAM_CONF", sequenceName = "SEQ_GC_TELEGRAM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    GcKategori gcKategori;

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

    public GcKategori getGcKategori() {
        return gcKategori;
    }

    public void setGcKategori(GcKategori gcKategori) {
        this.gcKategori = gcKategori;
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
