package com.app.fku.stoktakip.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.stoktakip.model.MagazaModel;
import com.app.fku.stoktakip.service.MagazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/stoktakip/magaza")
public class MagazaController {

    @Autowired
    MagazaService magazaService;

    @GetMapping("/getMagazaList")
    public List<MagazaModel> getMagazaList() {
        return magazaService.getMagazaList();
    }

    @PostMapping("/saveMagaza")
    public SonucModel saveMagaza(@RequestBody MagazaModel magazaModel) throws Exception {
        return new SonucModel("İşlem başarılı.", true, magazaService.saveMagaza(magazaModel));
    }

    @DeleteMapping("/deleteMagaza")
    public SonucModel deleteMagaza(@RequestParam Long id) throws Exception {
        magazaService.deleteMagaza(id);
        return new SonucModel("İşlem başarılı.", true, null);
    }
}