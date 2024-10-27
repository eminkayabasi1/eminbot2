package com.app.fku.hepsiburada.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.hepsiburada.fonksiyon.impl.HbSepetService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbUrunService;
import com.app.fku.hepsiburada.model.HbUrunModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hb/sepet")
public class HbSepetController {

    @Autowired
    HbSepetService hbSepetService;

    @GetMapping("/sepet-alim-ekle")
    public Boolean sepetAlimEkle(
            @RequestParam String eposta,
            @RequestParam String hbNo,
            @RequestParam Integer adet
    ) {
        return hbSepetService.sepetAlimEkle(eposta, hbNo, adet);
    }

    @GetMapping("/sepet-alim-ekle-toplu")
    public Boolean sepetAlimEkleToplu(
            @RequestParam String hbNo,
            @RequestParam Integer adet
    ) {
        return hbSepetService.sepetAlimEkleToplu(hbNo, adet);
    }
}