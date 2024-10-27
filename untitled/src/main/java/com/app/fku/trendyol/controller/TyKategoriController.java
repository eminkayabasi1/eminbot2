package com.app.fku.trendyol.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.trendyol.fonksiyon.service.TyKategoriService;
import com.app.fku.trendyol.model.TrendyolKategoriModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/ty/kategori")
public class TyKategoriController {

    @Autowired
    TyKategoriService tyKategoriService;

    @GetMapping("/getTyKategoriList")
    public List<TrendyolKategoriModel> getTyKategoriList() {
        return tyKategoriService.getTyKategoriList();
    }

    @PostMapping("/updateIndirimYuzdesi")
    public SonucModel updateIndirimYuzdesi(@RequestBody TrendyolKategoriModel trendyolKategoriModel) {
        SonucModel sonucModel = tyKategoriService.updateIndirimYuzdesi(trendyolKategoriModel);
        return sonucModel;
    }
}