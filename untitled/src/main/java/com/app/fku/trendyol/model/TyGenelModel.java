package com.app.fku.trendyol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TyGenelModel {
    private TyResultModel result;

    public TyResultModel getResult() {
        return result;
    }

    public void setResult(TyResultModel result) {
        this.result = result;
    }
}