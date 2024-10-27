package com.app.fku.genel.enums;

import java.util.Objects;

public enum EvetHayirEnum {

    HAYIR(0, "HAYIR"),
    EVET(1, "EVET");

    private final Integer id;
    private final String value;

    EvetHayirEnum(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public static EvetHayirEnum findValue(Integer id) {
        for (EvetHayirEnum threadEnum: EvetHayirEnum.values()) {
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
