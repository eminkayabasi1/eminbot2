package com.app.fku.trendyol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TyPlusFiyatModel {
    private Long tyPlusPrice;

    public Long getTyPlusPrice() {
        return tyPlusPrice;
    }

    public void setTyPlusPrice(Long tyPlusPrice) {
        this.tyPlusPrice = tyPlusPrice;
    }
}