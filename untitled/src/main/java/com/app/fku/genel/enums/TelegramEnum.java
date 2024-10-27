package com.app.fku.genel.enums;

import java.util.Objects;

public enum TelegramEnum {

    AMAZON("AMAZON", "AMAZON"),
    HEPSI_BURADA("HEPSI_BURADA", "HEPSI_BURADA"),//320807781
    TEKNOSA("TEKNOSA", "TEKNOSA");

    private final String id;
    private final String value;

    TelegramEnum(String id, String value) {
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

    public static TelegramEnum findValue(String id) {
        for (TelegramEnum threadTypeEnum: TelegramEnum.values()) {
            if (Objects.equals(threadTypeEnum.getId(), id)) {
                return threadTypeEnum;
            }
        }
        return null;
    }

}