package com.app.fku.stoktakip.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.stoktakip.model.HesapModel;
import com.app.fku.stoktakip.service.HesapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/stoktakip/hesap")
public class HesapController {

    @Autowired
    HesapService hesapService;

    @GetMapping("/getHesapListByMagazaId")
    public List<HesapModel> getHesapListByMagazaId(@RequestParam Long magazaId) throws Exception {
        return hesapService.getHesapListByMagazaId(magazaId);
    }

    @PostMapping("/saveHesap")
    public SonucModel saveHesap(@RequestBody HesapModel hesapModel) throws Exception {
        return new SonucModel("İşlem başarılı.", true, hesapService.saveHesap(hesapModel));
    }

    @DeleteMapping("/deleteHesap")
    public SonucModel deleteHesap(@RequestParam Long id) throws Exception {
        hesapService.deleteHesap(id);
        return new SonucModel("İşlem başarılı.", true, null);
    }
}