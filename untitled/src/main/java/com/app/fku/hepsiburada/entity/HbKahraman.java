package com.app.fku.hepsiburada.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "HB_KAHRAMAN")
public class HbKahraman extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HB_KAHRAMAN")
    @SequenceGenerator(name="SEQ_HB_KAHRAMAN", sequenceName = "SEQ_HB_KAHRAMAN", allocationSize = 1)
    Long id;

    @Column(name="HB_NO")
    String hbNo;

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

    public String getHbNo() {
        return hbNo;
    }

    public void setHbNo(String hbNo) {
        this.hbNo = hbNo;
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
