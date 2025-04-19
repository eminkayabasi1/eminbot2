package com.app.fku.hepsiburada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HbSepetGenel3Model {
    List<HbSepetUrunModel> basketItems;
    List<HbSepetKuponModel> eligibleCouponList;
    private HbSepetUrunFiyatModel totalPrice;

    public List<HbSepetUrunModel> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<HbSepetUrunModel> basketItems) {
        this.basketItems = basketItems;
    }

    public List<HbSepetKuponModel> getEligibleCouponList() {
        return eligibleCouponList;
    }

    public void setEligibleCouponList(List<HbSepetKuponModel> eligibleCouponList) {
        this.eligibleCouponList = eligibleCouponList;
    }

    public HbSepetUrunFiyatModel getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(HbSepetUrunFiyatModel totalPrice) {
        this.totalPrice = totalPrice;
    }
}