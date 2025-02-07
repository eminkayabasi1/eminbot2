package com.app.fku.trendyol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TyUrunModel {
    private String id;
    private String imageAlt;
    private String categoryName;
    private String url;
    private TyFiyatModel price;
    private Double indirimOrani;
    private Long collectableCouponDiscount;
    private Boolean hasCollectableCoupon;
    private List<TyUrunPromotionModel> promotions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TyFiyatModel getPrice() {
        return price;
    }

    public void setPrice(TyFiyatModel price) {
        this.price = price;
    }

    public Double getIndirimOrani() {
        return indirimOrani;
    }

    public void setIndirimOrani(Double indirimOrani) {
        this.indirimOrani = indirimOrani;
    }

    public Long getCollectableCouponDiscount() {
        return collectableCouponDiscount;
    }

    public void setCollectableCouponDiscount(Long collectableCouponDiscount) {
        this.collectableCouponDiscount = collectableCouponDiscount;
    }

    public Boolean getHasCollectableCoupon() {
        return hasCollectableCoupon;
    }

    public void setHasCollectableCoupon(Boolean hasCollectableCoupon) {
        this.hasCollectableCoupon = hasCollectableCoupon;
    }

    public List<TyUrunPromotionModel> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<TyUrunPromotionModel> promotions) {
        this.promotions = promotions;
    }
}