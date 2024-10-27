package com.app.fku.vatan.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "VATAN_TELEGRAM_CONF")
public class VatanTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VATAN_TLGRM_CONF")
    @SequenceGenerator(name="SEQ_VATAN_TLGRM_CONF", sequenceName = "SEQ_VATAN_TLGRM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    VatanKategori vatanKategori;

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

    public VatanKategori getVatanKategori() {
        return vatanKategori;
    }

    public void setVatanKategori(VatanKategori vatanKategori) {
        this.vatanKategori = vatanKategori;
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
