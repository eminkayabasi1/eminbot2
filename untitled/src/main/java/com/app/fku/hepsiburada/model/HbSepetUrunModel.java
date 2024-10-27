package com.app.fku.hepsiburada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HbSepetUrunModel {
    private HbSepetUrunIsimModel product;
    private HbSepetUrunFiyatModel price;

    public HbSepetUrunIsimModel getProduct() {
        return product;
    }

    public void setProduct(HbSepetUrunIsimModel product) {
        this.product = product;
    }

    public HbSepetUrunFiyatModel getPrice() {
        return price;
    }

    public void setPrice(HbSepetUrunFiyatModel price) {
        this.price = price;
    }
}