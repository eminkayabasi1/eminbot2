package com.app.fku.trendyol.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "TRENDYOL_TELEGRAM_CONF")
public class TrendyolTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRENDYOL_CONF")
    @SequenceGenerator(name="SEQ_TRENDYOL_CONF", sequenceName = "SEQ_TRENDYOL_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    TrendyolKategori trendyolKategori;

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

    public TrendyolKategori getTrendyolKategori() {
        return trendyolKategori;
    }

    public void setTrendyolKategori(TrendyolKategori trendyolKategori) {
        this.trendyolKategori = trendyolKategori;
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
