package com.app.fku.teknosa.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "TKN_TELEGRAM_CONF")
public class TknTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TKN_TLGRM_CONF")
    @SequenceGenerator(name="SEQ_TKN_TLGRM_CONF", sequenceName = "SEQ_TKN_TLGRM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    TknKategori tknKategori;

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

    public TknKategori getTknKategori() {
        return tknKategori;
    }

    public void setTknKategori(TknKategori tknKategori) {
        this.tknKategori = tknKategori;
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
