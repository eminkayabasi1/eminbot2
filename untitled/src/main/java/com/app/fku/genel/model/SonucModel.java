package com.app.fku.genel.model;

import java.util.List;

public class SonucModel {
    String sonucMsj;
    boolean sonucDurum;
    Object data;

    public SonucModel() {
    }

    public SonucModel(String sonucMsj, boolean sonucDurum, Object data) {
        this.sonucMsj = sonucMsj;
        this.sonucDurum = sonucDurum;
        this.data = data;
    }

    public String getSonucMsj() {
        return sonucMsj;
    }

    public void setSonucMsj(String sonucMsj) {
        this.sonucMsj = sonucMsj;
    }

    public boolean isSonucDurum() {
        return sonucDurum;
    }

    public void setSonucDurum(boolean sonucDurum) {
        this.sonucDurum = sonucDurum;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
