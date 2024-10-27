package com.app.fku.yemeksepeti.entity;

import com.app.fku.genel.entity.BaseEntity;
import com.app.fku.genel.enums.ThreadTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "YMK_CONF")
public class YmkConf extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_YMK_CONF")
    @SequenceGenerator(name="SEQ_YMK_CONF", sequenceName = "SEQ_YMK_CONF", allocationSize = 1)
    Long id;

    @Column(name = "HOSTNAME")
    String hostname;

    @Column(name = "MANAGER_SERVER_IP")
    String managerServerIp;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ThreadTypeEnum threadTypeEnum;

    @Column(name = "THREAD_COUNT")
    private int threadCount;

    @Column(name = "CATEGORY")
    private Long category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getManagerServerIp() {
        return managerServerIp;
    }

    public void setManagerServerIp(String managerServerIp) {
        this.managerServerIp = managerServerIp;
    }

    public ThreadTypeEnum getThreadTypeEnum() {
        return threadTypeEnum;
    }

    public void setThreadTypeEnum(ThreadTypeEnum threadTypeEnum) {
        this.threadTypeEnum = threadTypeEnum;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
