package com.app.fku.hepsiburada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HbSepetGenel3Model {
    List<HbSepetUrunModel> basketItems;

    public List<HbSepetUrunModel> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<HbSepetUrunModel> basketItems) {
        this.basketItems = basketItems;
    }
}