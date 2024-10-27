package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.hepsiburada.entity.HbGerideKalanlar;
import com.app.fku.hepsiburada.entity.HbWorkerIstatistik;
import com.app.fku.hepsiburada.fonksiyon.impl.HbWorkerIstatistikService;
import com.app.fku.hepsiburada.model.HbWorkerIstatistikModel;
import com.app.fku.hepsiburada.repository.HbGerideKalanlarRepository;
import com.app.fku.hepsiburada.repository.HbWorkerIstatistikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HbWorkerIstatistikServiceImpl implements HbWorkerIstatistikService {

    @Autowired
    HbWorkerIstatistikRepository hbWorkerIstatistikRepository;

    @Autowired
    HbGerideKalanlarRepository hbGerideKalanlarRepository;

    @Override
    public List<HbWorkerIstatistikModel> son25IstatistigiGetir() {
        List<HbWorkerIstatistik> hbWorkerIstatistikList = hbWorkerIstatistikRepository.findTop25ByOrderByYilDescAyDescGunDescSaatDescHostname();
        List<HbWorkerIstatistikModel> hbWorkerIstatistikModelList = new ArrayList<HbWorkerIstatistikModel>();

        for(HbWorkerIstatistik hbWorkerIstatistik: hbWorkerIstatistikList) {
            HbWorkerIstatistikModel hbWorkerIstatistikModel = new HbWorkerIstatistikModel();
            Date tarih = new Date(hbWorkerIstatistik.getYil()-1900, hbWorkerIstatistik.getAy(), hbWorkerIstatistik.getGun(), hbWorkerIstatistik.getSaat(), 0);
            hbWorkerIstatistikModel.setTarih(tarih);
            hbWorkerIstatistikModel.setHostname(hbWorkerIstatistik.getHostname());
            hbWorkerIstatistikModel.setSayac(hbWorkerIstatistik.getSayac());

            hbWorkerIstatistikModelList.add(hbWorkerIstatistikModel);
        }

        return hbWorkerIstatistikModelList;
    }

    @Override
    public List<HbGerideKalanlar> gerideKalanlarList() {
        List<HbGerideKalanlar> hbGerideKalanlarList = hbGerideKalanlarRepository.findAllByOrderByTarihDesc();
        return hbGerideKalanlarList;
    }
}
