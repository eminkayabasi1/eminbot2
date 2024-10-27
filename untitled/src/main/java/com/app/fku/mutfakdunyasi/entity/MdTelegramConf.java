package com.app.fku.mutfakdunyasi.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "MD_TELEGRAM_CONF")
public class MdTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MD_TELEGRAM_CONF")
    @SequenceGenerator(name="SEQ_MD_TELEGRAM_CONF", sequenceName = "SEQ_MD_TELEGRAM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    MdKategori mdKategori;

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

    public MdKategori getMdKategori() {
        return mdKategori;
    }

    public void setMdKategori(MdKategori mdKategori) {
        this.mdKategori = mdKategori;
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
