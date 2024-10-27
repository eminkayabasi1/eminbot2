package com.app.fku.teknosa.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.teknosa.fonksiyon.service.TknUrunService;
import com.app.fku.teknosa.model.TknUrunModel;
import com.app.fku.teknosa.model.TknUrunTopluModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/teknosa/urun")
public class TknUrunController {

    @Autowired
    TknUrunService tknUrunService;

    @GetMapping("/yeniUrunList")
    public SonucModel yeniUrunList() {
        List<TknUrunModel> tknUrunModelList = tknUrunService.son25UrunGetir();
        SonucModel sonucModel = new SonucModel();
        sonucModel.setData(tknUrunModelList);
        return sonucModel;
    }

    @GetMapping("/urunBeklenenFiyatEksilt")
    public SonucModel urunBeklenenFiyatEksilt(TknUrunModel tknUrunModel) {
        String sonuc = tknUrunService.urunBeklenenFiyatEksilt(tknUrunModel.getFiyatId(), tknUrunModel.getYeniBeklenenFiyat());
        SonucModel sonucModel = new SonucModel();
        sonucModel.setSonucMsj(sonuc);
        return sonucModel;
    }

    @GetMapping("/urunBeklenenFiyatGuncelle")
    public SonucModel urunBeklenenFiyatGuncelle(TknUrunModel tknUrunModel) {
        String sonuc = tknUrunService.urunBeklenenFiyatGuncelle(tknUrunModel.getFiyatId(), tknUrunModel.getYeniBeklenenFiyat());
        SonucModel sonucModel = new SonucModel();
        sonucModel.setSonucMsj(sonuc);
        return sonucModel;
    }

    @GetMapping("/urunuEngelle")
    public  SonucModel urunuEngelle(TknUrunModel tknUrunModel) {
        String mesaj = tknUrunService.urunuEngelle(tknUrunModel.getFiyatId());
        SonucModel sonucModel = new SonucModel();
        sonucModel.setSonucMsj(mesaj);
        return sonucModel;
    }

    @GetMapping("/topluUrunBeklenenFiyatEksilt")
    public SonucModel topluUrunBeklenenFiyatEksilt(TknUrunTopluModel tknUrunTopluModel) {
        String sonuc = tknUrunService.topluUrunBeklenenFiyatEksilt(tknUrunTopluModel);
        SonucModel sonucModel = new SonucModel();
        sonucModel.setSonucMsj(sonuc);
        return sonucModel;
    }

    @GetMapping("/topluUrunBeklenenFiyatCarp")
    public SonucModel topluUrunBeklenenFiyatCarp(TknUrunTopluModel tknUrunTopluModel) {
        String sonuc = tknUrunService.topluUrunBeklenenFiyatCarp(tknUrunTopluModel);
        SonucModel sonucModel = new SonucModel();
        sonucModel.setSonucMsj(sonuc);
        return sonucModel;
    }
}