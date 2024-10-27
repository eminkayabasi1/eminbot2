package com.app.fku.teknosa.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.teknosa.fonksiyon.service.TknIndirimService;
import com.app.fku.teknosa.model.TknIndirimModel;
import com.app.fku.teknosa.model.TknIndirimSorguModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/teknosa/indirim")
public class TknIndirimController {

    @Autowired
    TknIndirimService tknIndirimService;

    @GetMapping("/indirimList")
    public SonucModel indirimList(TknIndirimSorguModel tknIndirimSorguModel) {

        List<TknIndirimModel> indirimList = tknIndirimService.sonIndirimleriGetir(tknIndirimSorguModel);

        SonucModel sonucModel = new SonucModel();
        sonucModel.setData(indirimList);
        return sonucModel;
    }
}