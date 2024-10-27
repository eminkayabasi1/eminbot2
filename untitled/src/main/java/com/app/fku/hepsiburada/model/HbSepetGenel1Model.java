package com.app.fku.hepsiburada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HbSepetGenel1Model {
    private HbSepetGenel2Model result;

    public HbSepetGenel2Model getResult() {
        return result;
    }

    public void setResult(HbSepetGenel2Model result) {
        this.result = result;
    }
}