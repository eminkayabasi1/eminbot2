package com.app.fku.amazonx.model;

public class AmazonRequestSubModel {
    private String obfuscatedMarketplaceId;
    private String obfuscatedMerchantId;
    private String language;
    private String currency;
    private String slateToken;
    private AmazonRequestSub2Model queryParameterMap;

    public String getObfuscatedMarketplaceId() {
        return obfuscatedMarketplaceId;
    }

    public void setObfuscatedMarketplaceId(String obfuscatedMarketplaceId) {
        this.obfuscatedMarketplaceId = obfuscatedMarketplaceId;
    }

    public String getObfuscatedMerchantId() {
        return obfuscatedMerchantId;
    }

    public void setObfuscatedMerchantId(String obfuscatedMerchantId) {
        this.obfuscatedMerchantId = obfuscatedMerchantId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSlateToken() {
        return slateToken;
    }

    public void setSlateToken(String slateToken) {
        this.slateToken = slateToken;
    }

    public AmazonRequestSub2Model getQueryParameterMap() {
        return queryParameterMap;
    }

    public void setQueryParameterMap(AmazonRequestSub2Model queryParameterMap) {
        this.queryParameterMap = queryParameterMap;
    }
}