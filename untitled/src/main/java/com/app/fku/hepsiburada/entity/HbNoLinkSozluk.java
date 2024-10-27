package com.app.fku.hepsiburada.entity;

import com.app.fku.genel.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "HB_NO_LINK_SOZLUK")
public class HbNoLinkSozluk extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HB_NO_LINK_SOZLUK")
    @SequenceGenerator(name="SEQ_HB_NO_LINK_SOZLUK", sequenceName = "SEQ_HB_NO_LINK_SOZLUK", allocationSize = 1)
    Long id;

    @Column(name = "HB_NO")
    String hbNo;

    @Column(name = "LINK")
    String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHbNo() {
        return hbNo;
    }

    public void setHbNo(String hbNo) {
        this.hbNo = hbNo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
