package com.app.fku.karaca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KaracaUrunModel {
    private String product_id;
    private String name;
    private String href;
    private String unFormattedPrice;
    private Integer fiyat;
    private List<String> badgeList;
    private Double indirimOrani;
    private Boolean birlikteAlKazanMi;
    private Long telegramChatId;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getUnFormattedPrice() {
        return unFormattedPrice;
    }

    public void setUnFormattedPrice(String unFormattedPrice) {
        this.unFormattedPrice = unFormattedPrice;
    }

    public Integer getFiyat() {
        return fiyat;
    }

    public void setFiyat(Integer fiyat) {
        this.fiyat = fiyat;
    }

    public List<String> getBadgeList() {
        return badgeList;
    }

    public void setBadgeList(List<String> badgeList) {
        this.badgeList = badgeList;
    }

    public Double getIndirimOrani() {
        return indirimOrani;
    }

    public void setIndirimOrani(Double indirimOrani) {
        this.indirimOrani = indirimOrani;
    }

    public Boolean getBirlikteAlKazanMi() {
        return birlikteAlKazanMi;
    }

    public void setBirlikteAlKazanMi(Boolean birlikteAlKazanMi) {
        this.birlikteAlKazanMi = birlikteAlKazanMi;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(Long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }
}