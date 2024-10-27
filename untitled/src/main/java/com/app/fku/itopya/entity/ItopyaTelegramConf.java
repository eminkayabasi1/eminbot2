package com.app.fku.itopya.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "ITOPYA_TELEGRAM_CONF")
public class ItopyaTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITOPYA_TELEGRAM_CONF")
    @SequenceGenerator(name="SEQ_ITOPYA_TELEGRAM_CONF", sequenceName = "SEQ_ITOPYA_TELEGRAM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    ItopyaKategori itopyaKategori;

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

    public ItopyaKategori getItopyaKategori() {
        return itopyaKategori;
    }

    public void setItopyaKategori(ItopyaKategori itopyaKategori) {
        this.itopyaKategori = itopyaKategori;
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
