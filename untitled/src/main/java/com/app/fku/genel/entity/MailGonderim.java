package com.app.fku.genel.entity;

import com.app.fku.amazon.entity.AmzUrun;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.hepsiburada.entity.HbUrun;
import com.app.fku.teknosa.entity.TknUrun;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GNL_MAIL_GONDERIM")
public class MailGonderim extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HB_CONF_1")
    @SequenceGenerator(name="SEQ_HB_CONF_1", sequenceName = "SEQ_HB_CONF_1", allocationSize = 1)
    Long id;

    @Column(name = "MAIL_TO")
    String mailTo;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "HOSTNAME")
    private String hostname;

    @Column(name = "STATUS")
    private EvetHayirEnum status;

    @Column(name = "GONDERIM_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gonderimTarihi;

    @Column(name = "KAYIT_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kayitTarihi;

    @Column(name = "GUNCELLEME_TARIHI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date guncellemeTarihi;

    @JoinColumn(name = "AMZ_URUN_ID")
    @ManyToOne
    AmzUrun amzUrun;

    @JoinColumn(name = "HB_URUN_ID")
    @ManyToOne
    HbUrun hbUrun;

    @JoinColumn(name = "TKN_URUN_ID")
    @ManyToOne
    TknUrun tknUrun;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public EvetHayirEnum getStatus() {
        return status;
    }

    public void setStatus(EvetHayirEnum status) {
        this.status = status;
    }

    public Date getGonderimTarihi() {
        return gonderimTarihi;
    }

    public void setGonderimTarihi(Date gonderimTarihi) {
        this.gonderimTarihi = gonderimTarihi;
    }

    public Date getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(Date kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public Date getGuncellemeTarihi() {
        return guncellemeTarihi;
    }

    public void setGuncellemeTarihi(Date guncellemeTarihi) {
        this.guncellemeTarihi = guncellemeTarihi;
    }

    public AmzUrun getAmzUrun() {
        return amzUrun;
    }

    public void setAmzUrun(AmzUrun amzUrun) {
        this.amzUrun = amzUrun;
    }

    public HbUrun getHbUrun() {
        return hbUrun;
    }

    public void setHbUrun(HbUrun hbUrun) {
        this.hbUrun = hbUrun;
    }

    public TknUrun getTknUrun() {
        return tknUrun;
    }

    public void setTknUrun(TknUrun tknUrun) {
        this.tknUrun = tknUrun;
    }
}
