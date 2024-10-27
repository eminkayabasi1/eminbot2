package com.app.fku.trendyol.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "TY_KAHRAMAN")
public class TrendyolKahraman extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TY_KAHRAMAN")
    @SequenceGenerator(name="SEQ_TY_KAHRAMAN", sequenceName = "SEQ_TY_KAHRAMAN", allocationSize = 1)
    Long id;

    @Column(name="TY_NO")
    String tyNo;

    @Column(name="URUN_ADI")
    String urunAdi;

    @Column(name = "TELEGRAM_CHAT_ID")
    String telegramChatId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTyNo() {
        return tyNo;
    }

    public void setTyNo(String tyNo) {
        this.tyNo = tyNo;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(String telegramChatId) {
        this.telegramChatId = telegramChatId;
    }
}
