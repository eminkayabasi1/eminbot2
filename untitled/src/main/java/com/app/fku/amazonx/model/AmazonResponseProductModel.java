package com.app.fku.amazonx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonResponseProductModel {
    private String asin;
    private List<AmazonResponseBuyingOptionsModel> buyingOptions;
    private AmazonResponseTitleModel title;

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public List<AmazonResponseBuyingOptionsModel> getBuyingOptions() {
        return buyingOptions;
    }

    public void setBuyingOptions(List<AmazonResponseBuyingOptionsModel> buyingOptions) {
        this.buyingOptions = buyingOptions;
    }

    public AmazonResponseTitleModel getTitle() {
        return title;
    }

    public void setTitle(AmazonResponseTitleModel title) {
        this.title = title;
    }
}