package com.app.fku.bim.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "BIM_TELEGRAM_CONF")
public class BimTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BIM_TELEGRAM_CONF")
    @SequenceGenerator(name="SEQ_BIM_TELEGRAM_CONF", sequenceName = "SEQ_BIM_TELEGRAM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    BimKategori bimKategori;

    @Column(name = "TELEGRAM_CHAT_ID")
    String telegramChatId;

    @Column(name = "ACIKLAMA")
    private String aciklama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BimKategori getBimKategori() {
        return bimKategori;
    }

    public void setBimKategori(BimKategori bimKategori) {
        this.bimKategori = bimKategori;
    }

    public String getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(String telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
