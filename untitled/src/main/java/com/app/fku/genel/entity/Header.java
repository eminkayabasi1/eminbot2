package com.app.fku.genel.entity;

import javax.persistence.*;

@Entity
@Table(name = "GNL_HEADERS")
public class Header extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GNL_HEADERS")
    @SequenceGenerator(name="SEQ_GNL_HEADERS", sequenceName = "SEQ_GNL_HEADERS", allocationSize = 1)
    Long id;

    @Column(name = "HEADER")
    String header;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
