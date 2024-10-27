package com.app.fku.stoktakip.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "STU_RENK")
public class Renk extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STU_RENK")
    @SequenceGenerator(name="SEQ_STU_RENK", sequenceName = "SEQ_STU_RENK", allocationSize = 1)
    Long id;

    @Column(name = "RENK")
    String renk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }
}
