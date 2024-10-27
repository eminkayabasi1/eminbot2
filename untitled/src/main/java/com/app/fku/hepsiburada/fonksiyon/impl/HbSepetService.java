package com.app.fku.hepsiburada.fonksiyon.impl;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.hepsiburada.model.HbUrunModel;

import java.util.List;

public interface HbSepetService {

    Boolean sepetAlimEkle(String eposta, String hbNo, Integer adet);
    Boolean sepetAlimEkleToplu(String hbNo, Integer adet);
}
