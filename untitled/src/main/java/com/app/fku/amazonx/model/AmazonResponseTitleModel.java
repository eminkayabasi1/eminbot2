package com.app.fku.amazonx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonResponseTitleModel {
    private String displayString;

    public String getDisplayString() {
        return displayString;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
}