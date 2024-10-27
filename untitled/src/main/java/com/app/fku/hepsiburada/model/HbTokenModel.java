package com.app.fku.hepsiburada.model;

public class HbTokenModel {
    private String clientId;
    private String tenantId;
    private String bearerTokent;
    private Integer urunSayisi;

    public HbTokenModel(String clientId, String tenantId, String bearerTokent) {
        this.clientId = clientId;
        this.tenantId = tenantId;
        this.bearerTokent = bearerTokent;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getBearerTokent() {
        return bearerTokent;
    }

    public void setBearerTokent(String bearerTokent) {
        this.bearerTokent = bearerTokent;
    }

    public Integer getUrunSayisi() {
        return urunSayisi;
    }

    public void setUrunSayisi(Integer urunSayisi) {
        this.urunSayisi = urunSayisi;
    }
}