package com.app.fku.hepsiburada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HbSepetOtomatikEkleGenelModel {
    private List<HbSepetOtomatikEkleModel> urunList;
    private Long urunSayisi;

    public List<HbSepetOtomatikEkleModel> getUrunList() {
        return urunList;
    }

    public void setUrunList(List<HbSepetOtomatikEkleModel> urunList) {
        this.urunList = urunList;
    }

    public Long getUrunSayisi() {
        return urunSayisi;
    }

    public void setUrunSayisi(Long urunSayisi) {
        this.urunSayisi = urunSayisi;
    }
}