package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.fonksiyon.impl.HbKategoriService;
import com.app.fku.hepsiburada.model.HbKategoriModel;
import com.app.fku.hepsiburada.repository.HbKategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HbKategoriServiceImpl implements HbKategoriService {

    @Autowired
    HbKategoriRepository hbKategoriRepository;

    @Override
    public List<HbKategoriModel> getHbKategoriList() {
        List<HbKategori> hbKategoriList = hbKategoriRepository.findAllByOrderById();

        List<HbKategoriModel> hbKategoriModelList = new ArrayList<>();
        for (HbKategori hbKategori: hbKategoriList) {
            HbKategoriModel hbKategoriModel = new HbKategoriModel();
            hbKategoriModel.setId(hbKategori.getId());
            hbKategoriModel.setKategoriAdi(hbKategori.getKategoriAdi());
            //hbKategoriModel.setIndirimYuzdesi(hbKategori.getIndirimYuzdesi());
            hbKategoriModel.setSayfaAdresi(hbKategori.getSayfaAdresi());
            hbKategoriModelList.add(hbKategoriModel);
        }
        return hbKategoriModelList;
    }

    @Override
    public SonucModel updateIndirimYuzdesi(HbKategoriModel hbKategoriModel) {
        SonucModel sonucModel = new SonucModel();
        HbKategori hbKategori = hbKategoriRepository.findById(hbKategoriModel.getId()).orElse(null);
        if (hbKategori != null) {
            //hbKategori.setIndirimYuzdesi(hbKategoriModel.getIndirimYuzdesi());
            hbKategoriRepository.save(hbKategori);
        }

        sonucModel.setData(hbKategoriModel);
        sonucModel.setSonucDurum(true);
        sonucModel.setSonucMsj("İşlem Başarılı.");
        return sonucModel;
    }
}
