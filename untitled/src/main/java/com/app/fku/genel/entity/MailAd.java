package com.app.fku.genel.entity;

import javax.persistence.*;

@Entity
@Table(name = "GNL_MAIL_AD")
public class MailAd extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAIL_AD_1")
    @SequenceGenerator(name="SEQ_MAIL_AD_1", sequenceName = "SEQ_MAIL_AD_1", allocationSize = 1)
    Long id;

    @Column(name = "ISIM")
    String isim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }
}
