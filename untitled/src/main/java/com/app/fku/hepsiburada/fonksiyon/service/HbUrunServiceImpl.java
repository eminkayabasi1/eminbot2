package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.hepsiburada.entity.HbUrun;
import com.app.fku.hepsiburada.fonksiyon.impl.HbUrunService;
import com.app.fku.hepsiburada.model.HbUrunModel;
import com.app.fku.hepsiburada.repository.HbKategoriRepository;
import com.app.fku.hepsiburada.repository.HbUrunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HbUrunServiceImpl implements HbUrunService {

    @Autowired
    HbUrunRepository hbUrunRepository;

    @Autowired
    HbKategoriRepository hbKategoriRepository;

    @Override
    public List<HbUrunModel> getSon50UrunList() {
        List<HbUrun> hbUrunList = hbUrunRepository.getSon50UrunList();

        List<HbUrunModel> hbUrunModelList = new ArrayList<>();
        for (HbUrun hbUrun: hbUrunList) {
            HbUrunModel hbUrunModel = new HbUrunModel();
            hbUrunModel.setModel(hbUrun.getModel());
            hbUrunModel.setGuncelFiyat(hbUrun.getGuncelFiyat());
            hbUrunModelList.add(hbUrunModel);
        }
        return hbUrunModelList;
    }

    @Override
    public List<HbUrunModel> getUrunListByKategori(Long kategoriId) {
        List<HbUrun> hbUrunList = hbUrunRepository.getUrunListByKategori(kategoriId);

        List<HbUrunModel> hbUrunModelList = new ArrayList<>();
        for (HbUrun hbUrun: hbUrunList) {
            HbUrunModel hbUrunModel = new HbUrunModel();
            hbUrunModel.setId(hbUrun.getId());
            hbUrunModel.setHbNo(hbUrun.getHbNo());
            hbUrunModel.setModel(hbUrun.getModel());
            hbUrunModel.setGuncelFiyat(hbUrun.getGuncelFiyat());
            hbUrunModel.setMinFiyat(hbUrun.getMinFiyat());
            hbUrunModel.setMinFiyatTarihi(hbUrun.getMinFiyatTarihi());
            hbUrunModelList.add(hbUrunModel);
        }
        return hbUrunModelList;
    }

    @Override
    public SonucModel urunDevreDisiBirak(Long urunId) {
        HbUrun hbUrun = hbUrunRepository.findById(urunId).orElse(null);
        hbUrun.setKontrolEdilsinMi(false);
        hbUrunRepository.save(hbUrun);

        SonucModel sonucModel = new SonucModel();
        sonucModel.setData(true);
        sonucModel.setSonucDurum(true);
        sonucModel.setSonucMsj("İşlem Başarılı.");
        return sonucModel;
    }
}
