package com.app.fku.amazonx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonResponse2Model {
    @JsonProperty("ASINList")
    private List<String> ASINList;
    private List<AmazonResponseProductModel> products;

    public List<String> getASINList() {
        return ASINList;
    }

    public void setASINList(List<String> ASINList) {
        this.ASINList = ASINList;
    }

    public List<AmazonResponseProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<AmazonResponseProductModel> products) {
        this.products = products;
    }
}