package com.app.fku.stoktakip.controller;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.stoktakip.model.RenkModel;
import com.app.fku.stoktakip.service.RenkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/stoktakip/renk")
public class RenkController {

    @Autowired
    RenkService renkService;

    @GetMapping("/getRenkList")
    public List<RenkModel> getRenkList() {
        return renkService.getRenkList();
    }

    @PostMapping("/saveRenk")
    public SonucModel saveRenk(@RequestBody RenkModel renkModel) throws Exception {
        return new SonucModel("İşlem başarılı.", true, renkService.saveRenk(renkModel));
    }

    @DeleteMapping("/deleteRenk")
    public SonucModel deleteRenk(@RequestParam Long id) throws Exception {
        renkService.deleteRenk(id);
        return new SonucModel("İşlem başarılı.", true, null);
    }
}