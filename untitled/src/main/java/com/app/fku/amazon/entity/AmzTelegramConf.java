package com.app.fku.amazon.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.hepsiburada.entity.HbKategori;

import javax.persistence.*;

@Entity
@Table(name = "AMZ_TELEGRAM_CONF")
public class AmzTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AMZ_TLGRM_CONF")
    @SequenceGenerator(name="SEQ_AMZ_TLGRM_CONF", sequenceName = "SEQ_AMZ_TLGRM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    AmzKategori amzKategori;

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

    public AmzKategori getAmzKategori() {
        return amzKategori;
    }

    public void setAmzKategori(AmzKategori amzKategori) {
        this.amzKategori = amzKategori;
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
