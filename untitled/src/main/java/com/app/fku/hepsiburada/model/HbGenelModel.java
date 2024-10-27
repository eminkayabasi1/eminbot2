package com.app.fku.hepsiburada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HbGenelModel {
    private String name;
    private String id;
    private Integer itemCount;
    private Integer productBoxRatio;
    private Integer totalItemCount;
    private List<HbnUrunModel> items;
    private String experiment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getProductBoxRatio() {
        return productBoxRatio;
    }

    public void setProductBoxRatio(Integer productBoxRatio) {
        this.productBoxRatio = productBoxRatio;
    }

    public Integer getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(Integer totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public List<HbnUrunModel> getItems() {
        return items;
    }

    public void setItems(List<HbnUrunModel> items) {
        this.items = items;
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }
}