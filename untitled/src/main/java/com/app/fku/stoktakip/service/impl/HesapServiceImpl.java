package com.app.fku.stoktakip.service.impl;

import com.app.fku.stoktakip.entity.Hesap;
import com.app.fku.stoktakip.entity.Magaza;
import com.app.fku.stoktakip.model.HesapModel;
import com.app.fku.stoktakip.model.MagazaModel;
import com.app.fku.stoktakip.repository.HesapRepository;
import com.app.fku.stoktakip.repository.MagazaRepository;
import com.app.fku.stoktakip.service.HesapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HesapServiceImpl implements HesapService {

    @Autowired
    HesapRepository hesapRepository;

    @Autowired
    MagazaRepository magazaRepository;

    @Override
    public List<HesapModel> getHesapListByMagazaId(Long magazaId) throws Exception {
        Magaza magaza = magazaRepository.findById(magazaId).orElse(null);
        if (magaza == null) {
            throw new Exception("Geçersiz magazaId");
        }
        List<Hesap> hesapList = hesapRepository.findByMagaza(magaza);
        List<HesapModel> hesapModelList = new ArrayList<>();
        for (Hesap hesap: hesapList) {
            HesapModel hesapModel = new HesapModel();
            hesapModel.setId(hesap.getId());
            hesapModel.setEmail(hesap.getEmail());
            hesapModel.setSifre(hesap.getSifre());
            MagazaModel magazaModel = new MagazaModel();
            magazaModel.setId(magaza.getId());
            magazaModel.setMagazaAdi(magaza.getMagazaAdi());
            magazaModel.setMagazaKisaAdi(magaza.getMagazaKisaAdi());
            hesapModel.setMagazaModel(magazaModel);
            hesapModelList.add(hesapModel);
        }
        return hesapModelList;
    }

    @Override
    public HesapModel saveHesap(HesapModel hesapModel) throws Exception {
        Magaza magaza = magazaRepository.findById(hesapModel.getMagazaModel().getId()).orElse(null);
        if (magaza == null) {
            throw new Exception("Geçersiz magazaId");
        }
        Hesap hesap = hesapRepository.findByMagazaAndEmail(magaza, hesapModel.getEmail());
        if (hesap != null) {
            throw new Exception(magaza.getMagazaAdi() + " mağazasına " + hesap.getEmail() + " hesabı daha önceden kaydedilmiştir.");
        }

        hesap = new Hesap();
        hesap.setEmail(hesapModel.getEmail());
        hesap.setSifre(hesapModel.getSifre());
        hesap.setMagaza(magaza);
        hesap = hesapRepository.save(hesap);
        hesapModel.setId(hesap.getId());
        return hesapModel;
    }

    @Override
    public void deleteHesap(Long id) throws Exception {
        Hesap hesap = hesapRepository.findById(id).orElse(null);
        if (hesap == null) {
            throw new Exception("Hesap Bulunamadı.");
        }
        hesapRepository.delete(hesap);
    }
}
