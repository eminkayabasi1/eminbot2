package com.app.fku.teknosa.fonksiyon.impl;

import com.app.fku.teknosa.entity.TknKategori;
import com.app.fku.teknosa.fonksiyon.service.TknKategoriService;
import com.app.fku.teknosa.model.TknKategoriModel;
import com.app.fku.teknosa.repository.TknKategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TknKategoriServiceImpl implements TknKategoriService {


    @Autowired
    TknKategoriRepository tknKategoriRepository;

    @Override
    public List<TknKategoriModel> getKategoriList() {

       List<TknKategori> tknKategoriList = tknKategoriRepository.findAllByOrderById();
       List<TknKategoriModel> tknKategoriModelList = new ArrayList<TknKategoriModel>();

       for (TknKategori tknKategori: tknKategoriList) {
           TknKategoriModel tknKategoriModel = new TknKategoriModel();
           tknKategoriModel.setId(tknKategori.getId());
           tknKategoriModel.setKategoriAdi(tknKategori.getKategoriAdi());
           tknKategoriModel.setKategoriKisaAd(tknKategori.getKategoriKisaAd());

           tknKategoriModelList.add(tknKategoriModel);
       }

       return tknKategoriModelList;
    }

}
