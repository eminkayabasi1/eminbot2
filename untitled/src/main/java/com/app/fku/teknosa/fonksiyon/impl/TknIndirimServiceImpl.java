package com.app.fku.teknosa.fonksiyon.impl;

import com.app.fku.teknosa.entity.TknFiyat;
import com.app.fku.teknosa.entity.TknKategori;
import com.app.fku.teknosa.fonksiyon.service.TknIndirimService;
import com.app.fku.teknosa.model.TknIndirimModel;
import com.app.fku.teknosa.model.TknIndirimSorguModel;
import com.app.fku.teknosa.repository.TknFiyatRepository;
import com.app.fku.teknosa.repository.TknKategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TknIndirimServiceImpl implements TknIndirimService {

    @Autowired
    TknFiyatRepository tknFiyatRepository;

    @Autowired
    TknKategoriRepository tknKategoriRepository;

    @Override
    public List<TknIndirimModel> sonIndirimleriGetir(TknIndirimSorguModel tknIndirimSorguModel) {

        TknKategori tknKategori = null;
        if (tknIndirimSorguModel.getTknKategoriModel() != null) {
            tknKategori = tknKategoriRepository.findById(tknIndirimSorguModel.getTknKategoriModel().getId()).orElse(null);
        }

        Long kategoriId = 0L;
        if (tknKategori != null) {
            kategoriId = tknKategori.getId();
        }

        List<TknFiyat> tknFiyatList = tknFiyatRepository.indirimleriGetir(tknIndirimSorguModel.getKayitSayisi(), kategoriId, tknIndirimSorguModel.getYeniKayitOlsunMu().getId(), tknIndirimSorguModel.getGuncelIndirimlerMi().getId());
        List<TknIndirimModel> tknIndirimModelList = new ArrayList<TknIndirimModel>();

        for(TknFiyat tknFiyat : tknFiyatList) {
            TknIndirimModel tknIndirimModel = new TknIndirimModel();
            tknIndirimModel.setFiyatKayitId(tknFiyat.getId());
            tknIndirimModel.setUrunLink("https://www.teknosa.com/a-p-" + tknFiyat.getTknUrun().getTknNo());
            tknIndirimModel.setKategori(tknFiyat.getTknUrun().getTknKategori().getKategoriAdi());
            tknIndirimModel.setModelAdi(tknFiyat.getTknUrun().getModel());
            tknIndirimModel.setMinFiyat(tknFiyat.getTknUrun().getMinFiyat());
            tknIndirimModel.setMinFiyatStr(doubleToString(tknFiyat.getTknUrun().getMinFiyat()));
            tknIndirimModel.setGuncelFiyat(tknFiyat.getFiyat());
            tknIndirimModel.setGuncelFiyatStr(doubleToString(tknFiyat.getFiyat()));
            tknIndirimModel.setGuncelFiyatKayitTarihi(tknFiyat.getKayitTarihi());
            tknIndirimModel.setBeklenenFiyat(tknFiyat.getTknUrun().getBeklenenFiyat());
            tknIndirimModel.setBeklenenFiyatStr(doubleToString(tknFiyat.getTknUrun().getBeklenenFiyat()));
            tknIndirimModel.setEskiBeklenenFiyat(tknFiyat.getEskiBeklenenFiyat());
            tknIndirimModel.setEskiBeklenenFiyatStr(doubleToString(tknFiyat.getEskiBeklenenFiyat()));
            tknIndirimModelList.add(tknIndirimModel);
        }
        return tknIndirimModelList;
    }

    private String doubleToString(Double d) {
        if (d == null) {
            return "-";
        }
        if (d < 0) {
            return "-";
        }

        DecimalFormat df = new DecimalFormat("#.##");
        String s = df.format(d);
        s = binlikAyrac(s);
        return s;
    }

    private String binlikAyrac(String miktar) {

        if (miktar == null || miktar.equals("")) {
            return miktar;
        }

        if (miktar.length() < 4) {
            return miktar;
        }

        miktar = miktar.replace(".", ",");

        String[] miktarList = miktar.split(",");

        if (miktarList[0].length() < 4) {
            return miktar;
        }

        String sayi = miktarList[0];
        int buyukSayac = 1;
        for (int i = 0; i < sayi.length(); i=i+3) {
            if (sayi.length() - i - buyukSayac < 3) {
                break;
            }
            sayi = sayi.substring(0, sayi.length() - ((buyukSayac*3) + (buyukSayac-1))) + "." + sayi.substring(sayi.length() - ((buyukSayac*3) + (buyukSayac-1)),sayi.length());
            buyukSayac++;
        }

        if (miktarList.length > 1)
            sayi = sayi + "," + miktarList[1];
        return sayi;
    }
}
