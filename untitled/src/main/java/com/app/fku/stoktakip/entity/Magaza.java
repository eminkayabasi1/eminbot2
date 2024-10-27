package com.app.fku.stoktakip.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.genel.enums.ThreadTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "STU_MAGAZA")
public class Magaza extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STU_MAGAZA")
    @SequenceGenerator(name="SEQ_STU_MAGAZA", sequenceName = "SEQ_STU_MAGAZA", allocationSize = 1)
    Long id;

    @Column(name = "MAGAZA_ADI")
    String magazaAdi;

    @Column(name = "MAGAZA_KISA_ADI")
    String magazaKisaAdi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMagazaAdi() {
        return magazaAdi;
    }

    public void setMagazaAdi(String magazaAdi) {
        this.magazaAdi = magazaAdi;
    }

    public String getMagazaKisaAdi() {
        return magazaKisaAdi;
    }

    public void setMagazaKisaAdi(String magazaKisaAdi) {
        this.magazaKisaAdi = magazaKisaAdi;
    }
}
