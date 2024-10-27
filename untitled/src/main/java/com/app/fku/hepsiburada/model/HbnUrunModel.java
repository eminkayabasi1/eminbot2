package com.app.fku.hepsiburada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HbnUrunModel {
    private String sku;
    private String brand;
    private String catalogName;
    private Integer finalPriceOnSale;
    private String name;
    private String url;
    private String merchantName;
    private String listingId;
    private String eskiSatici;
    private String yeniSatici;
    private Double indirimOrani;
    private Long telegramChatId;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Integer getFinalPriceOnSale() {
        return finalPriceOnSale;
    }

    public void setFinalPriceOnSale(Integer finalPriceOnSale) {
        this.finalPriceOnSale = finalPriceOnSale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getEskiSatici() {
        return eskiSatici;
    }

    public void setEskiSatici(String eskiSatici) {
        this.eskiSatici = eskiSatici;
    }

    public String getYeniSatici() {
        return yeniSatici;
    }

    public void setYeniSatici(String yeniSatici) {
        this.yeniSatici = yeniSatici;
    }

    public Double getIndirimOrani() {
        return indirimOrani;
    }

    public void setIndirimOrani(Double indirimOrani) {
        this.indirimOrani = indirimOrani;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(Long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }
}