package com.app.fku.teknosa.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.teknosa.fonksiyon.service.TknWorkerIstatistikService;
import com.app.fku.teknosa.model.TknWorkerIstatistikModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/teknosa/workeristatistik")
public class TknWorkerIstatistikController {

    @Autowired
    TknWorkerIstatistikService tknWorkerIstatistikService;

    @GetMapping("/workerIstatistikList")
    public SonucModel workerIstatistikList() {
        List<TknWorkerIstatistikModel> tknWorkerIstatistikModelList = tknWorkerIstatistikService.son25IstatistigiGetir();
        SonucModel sonucModel = new SonucModel();
        sonucModel.setData(tknWorkerIstatistikModelList);
        return sonucModel;
    }
}