package com.app.fku.hepsiburada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HbSepetGenel2Model {
    HbSepetGenel3Model basket;

    public HbSepetGenel3Model getBasket() {
        return basket;
    }

    public void setBasket(HbSepetGenel3Model basket) {
        this.basket = basket;
    }
}