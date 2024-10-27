package com.app.fku.teknosa.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.teknosa.fonksiyon.service.TknKategoriService;
import com.app.fku.teknosa.model.TknKategoriModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/teknosa/kategori")
public class TknKategoriController {

    @Autowired
    TknKategoriService tknKategoriService;

    @GetMapping("/kategoriList")
    public SonucModel kategoriList() {

        List<TknKategoriModel> tknKategoriModelList = tknKategoriService.getKategoriList();

        SonucModel sonucModel = new SonucModel();
        sonucModel.setData(tknKategoriModelList);
        return sonucModel;
    }


}