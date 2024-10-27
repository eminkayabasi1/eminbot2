package com.app.fku.hepsiburada.model;

public class HbUrunTopluModel {
    Long[] fiyatIdList;
    Double topluEksiltDeger;
    Double topluCarpDeger;

    public Long[] getFiyatIdList() {
        return fiyatIdList;
    }

    public void setFiyatIdList(Long[] fiyatIdList) {
        this.fiyatIdList = fiyatIdList;
    }

    public Double getTopluEksiltDeger() {
        return topluEksiltDeger;
    }

    public void setTopluEksiltDeger(Double topluEksiltDeger) {
        this.topluEksiltDeger = topluEksiltDeger;
    }

    public Double getTopluCarpDeger() {
        return topluCarpDeger;
    }

    public void setTopluCarpDeger(Double topluCarpDeger) {
        this.topluCarpDeger = topluCarpDeger;
    }
}
