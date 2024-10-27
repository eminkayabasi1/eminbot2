package com.app.fku.hepsiburada.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.genel.enums.ThreadTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "HB_TELEGRAM_CONF")
public class HbTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HB_TLGRM_CONF")
    @SequenceGenerator(name="SEQ_HB_TLGRM_CONF", sequenceName = "SEQ_HB_TLGRM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    HbKategori hbKategori;

    @Column(name = "TELEGRAM_ID")
    String telegramId;

    @Column(name = "TELEGRAM_TOKEN")
    String telegramToken;

    @Column(name = "ACIKLAMA")
    String aciklama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HbKategori getHbKategori() {
        return hbKategori;
    }

    public void setHbKategori(HbKategori hbKategori) {
        this.hbKategori = hbKategori;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public String getTelegramToken() {
        return telegramToken;
    }

    public void setTelegramToken(String telegramToken) {
        this.telegramToken = telegramToken;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
