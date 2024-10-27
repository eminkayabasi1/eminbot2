package com.app.fku.teknosa.fonksiyon.impl;

import com.app.fku.teknosa.entity.TknWorkerIstatistik;
import com.app.fku.teknosa.fonksiyon.service.TknWorkerIstatistikService;
import com.app.fku.teknosa.model.TknWorkerIstatistikModel;
import com.app.fku.teknosa.repository.TknWorkerIstatistikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TknWorkerIstatistikServiceImpl implements TknWorkerIstatistikService {

    @Autowired
    TknWorkerIstatistikRepository tknWorkerIstatistikRepository;

    @Override
    public List<TknWorkerIstatistikModel> son25IstatistigiGetir() {
        List<TknWorkerIstatistik> tknWorkerIstatistikList = tknWorkerIstatistikRepository.findTop25ByOrderByYilDescAyDescGunDescSaatDescHostname();
        List<TknWorkerIstatistikModel> tknWorkerIstatistikModelList = new ArrayList<TknWorkerIstatistikModel>();

        for(TknWorkerIstatistik tknWorkerIstatistik: tknWorkerIstatistikList) {
            TknWorkerIstatistikModel tknWorkerIstatistikModel = new TknWorkerIstatistikModel();
            Date tarih = new Date(tknWorkerIstatistik.getYil()-1900, tknWorkerIstatistik.getAy(), tknWorkerIstatistik.getGun(), tknWorkerIstatistik.getSaat(), 0);
            tknWorkerIstatistikModel.setTarih(tarih);
            tknWorkerIstatistikModel.setHostname(tknWorkerIstatistik.getHostname());
            tknWorkerIstatistikModel.setSayac(tknWorkerIstatistik.getSayac());

            tknWorkerIstatistikModelList.add(tknWorkerIstatistikModel);
        }

        return tknWorkerIstatistikModelList;
    }
}
