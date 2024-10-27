package com.app.fku.genel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

public enum ThreadTypeEnum {

    //GENEL
    GNL_MAIL_SENDER("GNL_MAIL_SENDER", "GNL_MAIL_SENDER"),
    //GENEL

    //HB
    HB_WORKER("HB_WORKER", "HB_WORKER"),
    HB_SEARCHER("HB_SEARCHER", "HB_SEARCHER"),
    HB_NEW_SEARCHER("HB_NEW_SEARCHER", "HB_NEW_SEARCHER"),
    HB_WORKER_GERI("HB_WORKER_GERI", "HB_WORKER_GERI"),
    HB_HESAP_ACMA("HB_HESAP_ACMA", "HB_HESAP_ACMA"),
    HB_SEPET_EKLE("HB_SEPET_EKLE", "HB_SEPET_EKLE"),
    HB_UYELIK_AC("HB_UYELIK_AC", "HB_UYELIK_AC"),
    HB_KUPON_KONTROL("HB_KUPON_KONTROL", "HB_KUPON_KONTROL"),
    //HB

    //A101
    A101_SEARCHER("A101_SEARCHER", "A101_SEARCHER"),
    //A101

    //ITOPYA
    ITOPYA_SEARCHER("ITOPYA_SEARCHER", "ITOPYA_SEARCHER"),
    //ITOPYA

    //BIM
    BIM_SEARCHER("BIM_SEARCHER", "BIM_SEARCHER"),
    //BIM

    //AMZ
    AMZ_SEARCHER("AMZ_SEARCHER", "AMZ_SEARCHER"),
    AMZ_WORKER("AMZ_WORKER", "AMZ_WORKER"),
    AMZ_NEW_SEARCHER("AMZ_NEW_SEARCHER", "AMZ_NEW_SEARCHER"),
    AMZ_NEW_MENU_SEARCHER("AMZ_NEW_MENU_SEARCHER", "AMZ_NEW_MENU_SEARCHER"),
    //AMZ

    //TEKNOSA
    TKN_SEARCHER("TKN_SEARCHER", "TKN_SEARCHER"),
    TKN_WORKER("TKN_WORKER", "TKN_WORKER"),
    //TEKNOSA

    //TEFAL
    TEFAL_SEARCHER("TEFAL_SEARCHER", "TEFAL_SEARCHER"),
    //TEFAL

    //TURKCELL
    TURKCELL_SEARCHER("TURKCELL_SEARCHER", "TURKCELL_SEARCHER"),
    //TURKCELL

    //GC
    GC_SEARCHER("GC_SEARCHER", "GC_SEARCHER"),
    //GC

    //KRC
    KRC_SEARCHER("KRC_SEARCHER", "KRC_SEARCHER"),
    //KRC

    //MUTFAK DÜNYASI
    MD_SEARCHER("MD_SEARCHER", "MD_SEARCHER"),
    //MUTFAK DÜNYASI

    //VATAN
    VATAN_SEARCHER("VATAN_SEARCHER", "VATAN_SEARCHER"),
    //VATAN

    //YENİ AMAZON
    YENI_AMZ_SEARCHER("YENI_AMZ_SEARCHER", "YENI_AMZ_SEARCHER"),
    //YENİ AMAZON

    //DYSON
    DYSON_SEARCHER("DYSON_SEARCHER", "DYSON_SEARCHER"),
    //DYSON

    //TRENDYOL
    TY_HESAP_ACMA("TY_HESAP_ACMA", "TY_HESAP_ACMA"),
    TY_HESAP_SEPET_EKLE("TY_HESAP_SEPET_EKLE", "TY_HESAP_SEPET_EKLE"),
    TY_KUPON_KONTROL("TY_KUPON_KONTROL", "TY_KUPON_KONTROL"),
    TY_SEARCHER("TY_SEARCHER", "TY_SEARCHER"),
    TY_SEARCHER_STAR("TY_SEARCHER_STAR", "TY_SEARCHER_STAR"),
    //TRENDYOL

    //YEMEKSEPETİ
    YMK_HESAP_ACMA("YMK_HESAP_ACMA", "YMK_HESAP_ACMA"),
    //YEMEKSEPETİ

    //MEDIAMARKT
    MM_HESAP_ACMA("MM_HESAP_ACMA", "MM_HESAP_ACMA"),
    MM_SEARCHER("MM_SEARCHER", "MM_SEARCHER"),
    //MEDIAMARKT

    //MIGROSSANAL
    MS_SEARCHER("MS_SEARCHER", "MS_SEARCHER"),
    //MIGROSSANAL

    //INSTAGRAM
    INS_TAKIPCI("INS_TAKIPCI", "INS_TAKIPCI");
    //INSTAGRAM

    private final String id;
    private final String value;

    ThreadTypeEnum(String id, String value) {
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

    public static ThreadTypeEnum findValue(String id) {
        for (ThreadTypeEnum threadTypeEnum: ThreadTypeEnum.values()) {
            if (Objects.equals(threadTypeEnum.getId(), id)) {
                return threadTypeEnum;
            }
        }
        return null;
    }

}