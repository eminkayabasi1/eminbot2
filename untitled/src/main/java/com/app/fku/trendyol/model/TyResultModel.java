package com.app.fku.trendyol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TyResultModel {
    private List<TyUrunModel> products;
    private Integer totalCount;

    public List<TyUrunModel> getProducts() {
        return products;
    }

    public void setProducts(List<TyUrunModel> products) {
        this.products = products;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}