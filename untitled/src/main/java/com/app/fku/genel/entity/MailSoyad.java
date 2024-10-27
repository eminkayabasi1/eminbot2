package com.app.fku.genel.entity;

import javax.persistence.*;

@Entity
@Table(name = "GNL_MAIL_SOYAD")
public class MailSoyad extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAIL_SOYAD_1")
    @SequenceGenerator(name="SEQ_MAIL_SOYAD_1", sequenceName = "SEQ_MAIL_SOYAD_1", allocationSize = 1)
    Long id;

    @Column(name = "SOYISIM")
    String soyIsim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoyIsim() {
        return soyIsim;
    }

    public void setSoyIsim(String soyIsim) {
        this.soyIsim = soyIsim;
    }
}
