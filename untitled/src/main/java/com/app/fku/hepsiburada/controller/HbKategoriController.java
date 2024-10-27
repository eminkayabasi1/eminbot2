package com.app.fku.hepsiburada.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.hepsiburada.fonksiyon.impl.HbKategoriService;
import com.app.fku.hepsiburada.model.HbKategoriModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/hb/kategori")
public class HbKategoriController {

    @Autowired
    HbKategoriService hbKategoriService;

    @GetMapping("/getHbKategoriList")
    public List<HbKategoriModel> getHbKategoriList() {
        return hbKategoriService.getHbKategoriList();
    }

    @PostMapping("/updateIndirimYuzdesi")
    public SonucModel updateIndirimYuzdesi(@RequestBody HbKategoriModel hbKategoriModel) {
        SonucModel sonucModel = hbKategoriService.updateIndirimYuzdesi(hbKategoriModel);
        return sonucModel;
    }
}