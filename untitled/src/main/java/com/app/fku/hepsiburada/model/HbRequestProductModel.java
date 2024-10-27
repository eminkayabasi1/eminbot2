package com.app.fku.hepsiburada.model;

public class HbRequestProductModel {
    private String quantity;
    private HbRequestMetadataModel metadata;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public HbRequestMetadataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(HbRequestMetadataModel metadata) {
        this.metadata = metadata;
    }
}