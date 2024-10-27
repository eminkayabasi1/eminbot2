package com.app.fku.turkcell.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "TURKCELL_TELEGRAM_CONF")
public class TurkcellTelegramConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TURKCELL_TELEGRAM_CONF")
    @SequenceGenerator(name="SEQ_TURKCELL_TELEGRAM_CONF", sequenceName = "SEQ_TURKCELL_TELEGRAM_CONF", allocationSize = 1)
    Long id;

    @JoinColumn(name = "KATEGORI_ID")
    @ManyToOne
    TurkcellKategori turkcellKategori;

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

    public TurkcellKategori getTurkcellKategori() {
        return turkcellKategori;
    }

    public void setTurkcellKategori(TurkcellKategori turkcellKategori) {
        this.turkcellKategori = turkcellKategori;
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
