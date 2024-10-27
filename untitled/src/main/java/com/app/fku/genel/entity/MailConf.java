package com.app.fku.genel.entity;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.enums.MailTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "GNL_MAIL_CONF")
public class MailConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAIL_CONF_1")
    @SequenceGenerator(name="SEQ_MAIL_CONF_1", sequenceName = "SEQ_MAIL_CONF_1", allocationSize = 1)
    Long id;

    @Column(name = "MAIL_TYPE")
    MailTypeEnum mailType;

    @Column(name = "MAIL_ADI")
    private String mailAdi;

    @Column(name = "MAIL_PSW")
    private String mailPsw;

    @Column(name = "AKTIF")
    EvetHayirEnum aktif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MailTypeEnum getMailType() {
        return mailType;
    }

    public void setMailType(MailTypeEnum mailType) {
        this.mailType = mailType;
    }

    public String getMailAdi() {
        return mailAdi;
    }

    public void setMailAdi(String mailAdi) {
        this.mailAdi = mailAdi;
    }

    public String getMailPsw() {
        return mailPsw;
    }

    public void setMailPsw(String mailPsw) {
        this.mailPsw = mailPsw;
    }

    public EvetHayirEnum getAktif() {
        return aktif;
    }

    public void setAktif(EvetHayirEnum aktif) {
        this.aktif = aktif;
    }
}
