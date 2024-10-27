package com.app.fku.trendyol.fonksiyon.impl;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.trendyol.entity.TrendyolKategori;
import com.app.fku.trendyol.fonksiyon.service.TyKategoriService;
import com.app.fku.trendyol.model.TrendyolKategoriModel;
import com.app.fku.trendyol.repository.TrendyolKategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TyKategoriServiceImpl implements TyKategoriService {

    @Autowired
    TrendyolKategoriRepository trendyolKategoriRepository;

    @Override
    public List<TrendyolKategoriModel> getTyKategoriList() {
        List<TrendyolKategori> trendyolKategoriList = trendyolKategoriRepository.findAllByOrderById();

        List<TrendyolKategoriModel> trendyolKategoriModelList = new ArrayList<>();
        for (TrendyolKategori trendyolKategori: trendyolKategoriList) {
            TrendyolKategoriModel trendyolKategoriModel = new TrendyolKategoriModel();
            trendyolKategoriModel.setId(trendyolKategori.getId());
            trendyolKategoriModel.setKategoriAdi(trendyolKategori.getKategoriAdi());
            trendyolKategoriModel.setIndirimYuzdesi(trendyolKategori.getIndirimYuzdesi());
            trendyolKategoriModel.setSayfaAdresi(trendyolKategori.getSayfaAdresi());
            trendyolKategoriModelList.add(trendyolKategoriModel);
        }
        return trendyolKategoriModelList;
    }

    @Override
    public SonucModel updateIndirimYuzdesi(TrendyolKategoriModel trendyolKategoriModel) {
        SonucModel sonucModel = new SonucModel();
        TrendyolKategori trendyolKategori = trendyolKategoriRepository.findById(trendyolKategoriModel.getId()).orElse(null);
        if (trendyolKategori != null) {
            trendyolKategori.setIndirimYuzdesi(trendyolKategoriModel.getIndirimYuzdesi());
            trendyolKategoriRepository.save(trendyolKategori);
        }

        sonucModel.setData(trendyolKategori);
        sonucModel.setSonucDurum(true);
        sonucModel.setSonucMsj("İşlem Başarılı.");
        return sonucModel;
    }
}
