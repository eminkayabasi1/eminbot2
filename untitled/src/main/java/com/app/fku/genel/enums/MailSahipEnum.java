package com.app.fku.genel.enums;

import java.util.Objects;

public enum MailSahipEnum {

    EMIN("EMIN", "EMIN"),
    SULEYMAN("SULEYMAN", "SULEYMAN"),
    KERIM("KERIM", "KERIM");

    private final String id;
    private final String value;

    MailSahipEnum(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }
    public String getValue() {
        return value;
    }

    public String enumValue() {
        return this.id;
    }

    public static MailSahipEnum findValue(String id) {
        for (MailSahipEnum threadTypeEnum: MailSahipEnum.values()) {
            if (Objects.equals(threadTypeEnum.getId(), id)) {
                return threadTypeEnum;
            }
        }
        return null;
    }

}