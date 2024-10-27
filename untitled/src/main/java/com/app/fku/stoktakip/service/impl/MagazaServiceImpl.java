package com.app.fku.stoktakip.service.impl;

import com.app.fku.stoktakip.entity.Magaza;
import com.app.fku.stoktakip.model.MagazaModel;
import com.app.fku.stoktakip.repository.MagazaRepository;
import com.app.fku.stoktakip.service.MagazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MagazaServiceImpl implements MagazaService {

    @Autowired
    MagazaRepository magazaRepository;

    @Override
    public List<MagazaModel> getMagazaList() {
        List<Magaza> magazaList = magazaRepository.findAll();
        List<MagazaModel> magazaModelList = new ArrayList<>();
        for (Magaza magaza: magazaList) {
            MagazaModel magazaModel = new MagazaModel();
            magazaModel.setId(magaza.getId());
            magazaModel.setMagazaAdi(magaza.getMagazaAdi());
            magazaModel.setMagazaKisaAdi(magaza.getMagazaKisaAdi());
            magazaModelList.add(magazaModel);
        }
        return magazaModelList;
    }

    @Override
    public MagazaModel saveMagaza(MagazaModel magazaModel) throws Exception {
        Magaza magaza = magazaRepository.findByMagazaAdi(magazaModel.getMagazaAdi());
        if (magaza != null) {
            throw new Exception(magaza.getMagazaAdi() + " isimli mağaza önceden kayıt edilmiştir.");
        }
        magaza = new Magaza();
        magaza.setMagazaAdi(magazaModel.getMagazaAdi());
        magaza.setMagazaKisaAdi(magazaModel.getMagazaKisaAdi());
        magaza = magazaRepository.save(magaza);
        magazaModel.setId(magaza.getId());
        return magazaModel;
    }

    @Override
    public void deleteMagaza(Long id) throws Exception {
        Magaza magaza = magazaRepository.findById(id).orElse(null);
        if (magaza == null) {
            throw new Exception("Mağaza Bulunamadı.");
        }
        magazaRepository.delete(magaza);
    }
}
