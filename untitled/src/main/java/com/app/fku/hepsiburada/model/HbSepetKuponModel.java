package com.app.fku.hepsiburada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HbSepetKuponModel {
    private Long targetGroupId;
    private Long campaignId;
    private Boolean isApplied;

    public Long getTargetGroupId() {
        return targetGroupId;
    }

    public void setTargetGroupId(Long targetGroupId) {
        this.targetGroupId = targetGroupId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Boolean getApplied() {
        return isApplied;
    }

    @JsonProperty("isApplied")
    public void setApplied(Boolean applied) {
        isApplied = applied;
    }
}