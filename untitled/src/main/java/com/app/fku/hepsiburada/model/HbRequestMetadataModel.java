package com.app.fku.hepsiburada.model;

public class HbRequestMetadataModel {
    private String sku;
    private String listingId;

    public HbRequestMetadataModel(String sku, String listingId) {
        this.sku = sku;
        this.listingId = listingId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }
}