package com.app.fku.amazonx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonResponseBuyingOptionsModel {
    Object merchant;
    Object promotionsUnified;
    Object price;

    public Object getMerchant() {
        return merchant;
    }

    public void setMerchant(Object merchant) {
        this.merchant = merchant;
    }

    public Object getPromotionsUnified() {
        return promotionsUnified;
    }

    public void setPromotionsUnified(Object promotionsUnified) {
        this.promotionsUnified = promotionsUnified;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }
}