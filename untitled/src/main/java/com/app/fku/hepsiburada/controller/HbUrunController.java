package com.app.fku.hepsiburada.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.hepsiburada.fonksiyon.impl.HbUrunService;
import com.app.fku.hepsiburada.model.HbKategoriModel;
import com.app.fku.hepsiburada.model.HbUrunModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/hb/urun")
public class HbUrunController {

    @Autowired
    HbUrunService hbUrunService;

    @GetMapping("/getSon50UrunList")
    public List<HbUrunModel> getSon50UrunList() {
        return hbUrunService.getSon50UrunList();
    }

    @GetMapping("/getUrunListByKategori")
    public List<HbUrunModel> getUrunListByKategori(@RequestParam("kategoriId") Long kategoriId) {
        return hbUrunService.getUrunListByKategori(kategoriId);
    }

    @PostMapping("/urunDevreDisiBirak")
    public SonucModel urunDevreDisiBirak(@RequestParam Long urunId) {
        return hbUrunService.urunDevreDisiBirak(urunId);
    }
}