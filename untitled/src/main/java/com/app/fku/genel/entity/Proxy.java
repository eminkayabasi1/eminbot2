package com.app.fku.genel.entity;

import javax.persistence.*;

@Entity
@Table(name = "GNL_PROXY")
public class Proxy extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GNL_PROXY")
    @SequenceGenerator(name="SEQ_GNL_PROXY", sequenceName = "SEQ_GNL_PROXY", allocationSize = 1)
    Long id;

    @Column(name = "PROXY")
    String proxy;

    @Column(name = "PORT")
    Integer port;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
