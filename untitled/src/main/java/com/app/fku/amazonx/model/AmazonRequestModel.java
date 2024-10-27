package com.app.fku.amazonx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AmazonRequestModel {
    @JsonProperty("ASINList")
    private List<String> ASINList;
    private String endpoint;
    private AmazonRequestSubModel requestContext;
    private String sectionType;
    private AmazonRequestSub3Model search;

    public List<String> getASINList() {
        return ASINList;
    }

    public void setASINList(List<String> ASINList) {
        this.ASINList = ASINList;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public AmazonRequestSubModel getRequestContext() {
        return requestContext;
    }

    public void setRequestContext(AmazonRequestSubModel requestContext) {
        this.requestContext = requestContext;
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public AmazonRequestSub3Model getSearch() {
        return search;
    }

    public void setSearch(AmazonRequestSub3Model search) {
        this.search = search;
    }
}