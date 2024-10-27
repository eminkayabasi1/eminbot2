package com.app.fku.teknosa.fonksiyon.service;

import com.app.fku.teknosa.model.TknIndirimModel;
import com.app.fku.teknosa.model.TknIndirimSorguModel;

import java.util.List;

public interface TknIndirimService {

    List<TknIndirimModel> sonIndirimleriGetir(TknIndirimSorguModel tknIndirimSorguModel);
}
