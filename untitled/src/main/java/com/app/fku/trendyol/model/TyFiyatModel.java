package com.app.fku.trendyol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TyFiyatModel {
    private Long sellingPrice;

    public Long getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Long sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}