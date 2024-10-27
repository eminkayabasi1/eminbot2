package com.app.fku.tefal.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "TEFAL_TELEGRAM_CONF")
public class TefalTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TEFAL_TLGRM_CONF")
    @SequenceGenerator(name="SEQ_TEFAL_TLGRM_CONF", sequenceName = "SEQ_TEFAL_TLGRM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    TefalKategori tefalKategori;

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

    public TefalKategori getTefalKategori() {
        return tefalKategori;
    }

    public void setTefalKategori(TefalKategori tefalKategori) {
        this.tefalKategori = tefalKategori;
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
