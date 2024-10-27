package com.app.fku.stoktakip.service.impl;

import com.app.fku.stoktakip.entity.Renk;
import com.app.fku.stoktakip.model.RenkModel;
import com.app.fku.stoktakip.repository.RenkRepository;
import com.app.fku.stoktakip.service.RenkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RenkServiceImpl implements RenkService {

    @Autowired
    RenkRepository renkRepository;

    @Override
    public List<RenkModel> getRenkList() {
        List<Renk> renkList = renkRepository.findAll();
        List<RenkModel> renkModelList = new ArrayList<>();
        for (Renk renk: renkList) {
            RenkModel renkModel = new RenkModel();
            renkModel.setId(renk.getId());
            renkModel.setRenk(renk.getRenk());
            renkModelList.add(renkModel);
        }
        return renkModelList;
    }

    @Override
    public RenkModel saveRenk(RenkModel renkModel) throws Exception {
        Renk renk = renkRepository.findByRenk(renkModel.getRenk());
        if (renk != null) {
            throw new Exception(renk.getRenk() + " rengi önceden kayıt edilmiştir.");
        }
        renk = new Renk();
        renk.setRenk(renkModel.getRenk());
        renk = renkRepository.save(renk);
        renkModel.setId(renk.getId());
        return renkModel;
    }

    @Override
    public void deleteRenk(Long id) throws Exception {
        Renk renk = renkRepository.findById(id).orElse(null);
        if (renk == null) {
            throw new Exception("Renk Bulunamadı.");
        }
        renkRepository.delete(renk);
    }
}
