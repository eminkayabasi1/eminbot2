package com.app.fku.stoktakip.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "STU_HESAP")
public class Hesap extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STU_HESAP")
    @SequenceGenerator(name="SEQ_STU_HESAP", sequenceName = "SEQ_STU_HESAP", allocationSize = 1)
    Long id;

    @JoinColumn(name = "MAGAZA_ID")
    @ManyToOne
    Magaza magaza;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "SIFRE")
    String sifre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Magaza getMagaza() {
        return magaza;
    }

    public void setMagaza(Magaza magaza) {
        this.magaza = magaza;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}
