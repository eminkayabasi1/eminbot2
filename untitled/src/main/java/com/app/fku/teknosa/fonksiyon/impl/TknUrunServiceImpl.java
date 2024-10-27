package com.app.fku.teknosa.fonksiyon.impl;

import com.app.fku.teknosa.entity.TknFiyat;
import com.app.fku.teknosa.entity.TknUrun;
import com.app.fku.teknosa.fonksiyon.service.TknUrunService;
import com.app.fku.teknosa.model.TknUrunModel;
import com.app.fku.teknosa.model.TknUrunTopluModel;
import com.app.fku.teknosa.repository.TknFiyatRepository;
import com.app.fku.teknosa.repository.TknUrunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TknUrunServiceImpl implements TknUrunService {

    @Autowired
    TknUrunRepository tknUrunRepository;

    @Autowired
    TknFiyatRepository tknFiyatRepository;

    @Override
    public List<TknUrunModel> son25UrunGetir() {
        List<TknUrun> tknUrunList = tknUrunRepository.findTop25ByOrderByIdDesc();
        List<TknUrunModel> tknUrunModelList = new ArrayList<TknUrunModel>();

        for(TknUrun tknUrun: tknUrunList) {
            TknUrunModel tknUrunModel = new TknUrunModel();
            tknUrunModel.setId(tknUrun.getId());
            tknUrunModel.setKayitTarihi(tknUrun.getKayitTarihi());
            tknUrunModel.setUrunUrl("https://www.teknosa.com/a-p-" + tknUrun.getTknNo());
            tknUrunModel.setGuncelFiyat(tknUrun.getGuncelFiyat());
            tknUrunModel.setModel(tknUrun.getModel());
            tknUrunModelList.add(tknUrunModel);
        }

        return tknUrunModelList;
    }

    @Override
    public String urunBeklenenFiyatEksilt(Long fiyatId, Double eksilecekMiktar) {
        TknFiyat tknFiyat = tknFiyatRepository.findById(fiyatId).orElse(null);
        TknUrun tknUrun = tknFiyat.getTknUrun();

        if (tknUrun == null) {
            return "Geçersiz fiyatId";
        }

        if (eksilecekMiktar == null) {
            return "Geçersiz yeniMinFiyat";
        }

        tknUrun.setBeklenenFiyat(tknUrun.getGuncelFiyat() - eksilecekMiktar);
        tknUrunRepository.save(tknUrun);
        tknUrunRepository.flush();
        return "İşlem başarılı.";
    }

    @Override
    public String urunBeklenenFiyatGuncelle(Long fiyatId, Double yeniBeklenenFiyat) {
        TknFiyat tknFiyat = tknFiyatRepository.findById(fiyatId).orElse(null);
        TknUrun tknUrun = tknFiyat.getTknUrun();

        if (tknUrun == null) {
            return "Geçersiz fiyatId";
        }

        if (yeniBeklenenFiyat == null) {
            return "Geçersiz yeniMinFiyat";
        }

        tknUrun.setBeklenenFiyat(yeniBeklenenFiyat);
        tknUrunRepository.save(tknUrun);
        tknUrunRepository.flush();
        return "İşlem başarılı.";
    }

    @Override
    public String urunuEngelle(Long fiyatId) {

        if (fiyatId == null) {
            return "FiyatId alanı boş olamaz.";
        }
        if (fiyatId < 0) {
            return "Geçersiz FiyatId";
        }

        TknFiyat tknFiyat = tknFiyatRepository.findById(fiyatId).orElse(null);

        if (tknFiyat == null) {
            return "Geçersiz FiyatId";
        }

        TknUrun tknUrun = tknFiyat.getTknUrun();
        tknUrun.setKontrolEdilsinMi(false);
        tknUrunRepository.save(tknUrun);
        tknUrunRepository.flush();
        return "İşlem Başarılı.";
    }

    @Override
    public String topluUrunBeklenenFiyatEksilt(TknUrunTopluModel tknUrunTopluModel) {

        if (tknUrunTopluModel.getTopluEksiltDeger() == null) {
            return "Toplu Eksiltme Değeri Geçersiz.";
        }
        if (tknUrunTopluModel.getTopluEksiltDeger() < 0) {
            return "Toplu Eksilmte Değeri Geçersiz.";
        }

        if (tknUrunTopluModel.getFiyatIdList() == null) {
            return "Ürün Listesi Yok. Eğer Ekranda Ürün Görüyorsanız Lütfen Sayfanın Yüklenmesini Bekleyiniz.";
        }

        if (tknUrunTopluModel.getFiyatIdList().length < 1) {
            return "Ürün Listesi Yok. Eğer Ekranda Ürün Görüyorsanız Lütfen Sayfanın Yüklenmesini Bekleyiniz.";
        }

        for (Long fiyatId: tknUrunTopluModel.getFiyatIdList()) {
            TknFiyat tknFiyat = tknFiyatRepository.findById(fiyatId).orElse(null);
            if (tknFiyat == null) {
                continue;
            }
            TknUrun tknUrun = tknFiyat.getTknUrun();
            tknUrun.setBeklenenFiyat(tknUrun.getGuncelFiyat() - tknUrunTopluModel.getTopluEksiltDeger());
            tknUrunRepository.save(tknUrun);
            tknUrunRepository.flush();
        }

        return "Toplu Eksiltme İşlemi Başarılı";
    }

    @Override
    public String topluUrunBeklenenFiyatCarp(TknUrunTopluModel tknUrunTopluModel) {

        if (tknUrunTopluModel.getTopluCarpDeger() == null) {
            return "Toplu Çarpma Değeri Geçersiz.";
        }
        if (tknUrunTopluModel.getTopluCarpDeger() < 0) {
            return "Toplu Çarpma Değeri Geçersiz.";
        }

        if (tknUrunTopluModel.getFiyatIdList() == null) {
            return "Ürün Listesi Yok. Eğer Ekranda Ürün Görüyorsanız Lütfen Sayfanın Yüklenmesini Bekleyiniz.";
        }

        if (tknUrunTopluModel.getFiyatIdList().length < 1) {
            return "Ürün Listesi Yok. Eğer Ekranda Ürün Görüyorsanız Lütfen Sayfanın Yüklenmesini Bekleyiniz.";
        }

        for (Long fiyatId: tknUrunTopluModel.getFiyatIdList()) {
            TknFiyat tknFiyat = tknFiyatRepository.findById(fiyatId).orElse(null);
            if (tknFiyat == null) {
                continue;
            }
            TknUrun tknUrun = tknFiyat.getTknUrun();
            tknUrun.setBeklenenFiyat(tknUrun.getGuncelFiyat() * tknUrunTopluModel.getTopluCarpDeger());
            tknUrunRepository.save(tknUrun);
            tknUrunRepository.flush();
        }

        return "Toplu Eksiltme İşlemi Başarılı";
    }
}
