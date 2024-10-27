package com.app.fku.a101.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "A101_TELEGRAM_CONF")
public class A101TelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_A101_TELEGRAM_CONF")
    @SequenceGenerator(name="SEQ_A101_TELEGRAM_CONF", sequenceName = "SEQ_A101_TELEGRAM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    A101Kategori a101Kategori;

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

    public A101Kategori getA101Kategori() {
        return a101Kategori;
    }

    public void setA101Kategori(A101Kategori a101Kategori) {
        this.a101Kategori = a101Kategori;
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
