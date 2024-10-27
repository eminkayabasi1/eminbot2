package com.app.fku.karaca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KaracaGenelModel {
    private List<KaracaUrunModel> products;
    private Integer total;

    public List<KaracaUrunModel> getProducts() {
        return products;
    }

    public void setProducts(List<KaracaUrunModel> products) {
        this.products = products;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}