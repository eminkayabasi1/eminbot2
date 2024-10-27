package com.app.fku.amazonx.model;

public class YeniAmazonLinkModel {
    private String obfuscatedMarketplaceId;
    private String obfuscatedMerchantId;
    private Long node;
    private String keywords;
    private String slateToken;

    public YeniAmazonLinkModel(String obfuscatedMarketplaceId, String obfuscatedMerchantId, Long node, String keywords, String slateToken) {
        this.obfuscatedMarketplaceId = obfuscatedMarketplaceId;
        this.obfuscatedMerchantId = obfuscatedMerchantId;
        this.node = node;
        this.keywords = keywords;
        this.slateToken = slateToken;
    }

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

    public Long getNode() {
        return node;
    }

    public void setNode(Long node) {
        this.node = node;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSlateToken() {
        return slateToken;
    }

    public void setSlateToken(String slateToken) {
        this.slateToken = slateToken;
    }
}