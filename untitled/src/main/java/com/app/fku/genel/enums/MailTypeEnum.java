package com.app.fku.genel.enums;

import java.util.Objects;

public enum MailTypeEnum {

    FROM(0, "FROM"),
    TO(1, "TO");

    private final Integer id;
    private final String value;

    MailTypeEnum(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public static MailTypeEnum findValue(Integer id) {
        for (MailTypeEnum threadEnum: MailTypeEnum.values()) {
            if (Objects.equals(threadEnum.getId(), id)) {
                return threadEnum;
            }
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
