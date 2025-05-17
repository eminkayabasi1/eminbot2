package com.app.fku.tesla;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeslaResultModel {
    private Integer total_matches_found;

    public Integer getTotal_matches_found() {
        return total_matches_found;
    }

    public void setTotal_matches_found(Integer total_matches_found) {
        this.total_matches_found = total_matches_found;
    }
}