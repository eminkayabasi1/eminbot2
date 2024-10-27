package com.app.fku.stoktakip.service;

import com.app.fku.stoktakip.model.RenkModel;

import java.util.List;

public interface RenkService {

    List<RenkModel> getRenkList();
    RenkModel saveRenk(RenkModel renkModel) throws Exception;
    void deleteRenk(Long id) throws Exception;

}
