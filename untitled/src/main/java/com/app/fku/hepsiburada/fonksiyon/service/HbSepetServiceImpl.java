package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.model.SonucModel;
import com.app.fku.hepsiburada.entity.HbNoLinkSozluk;
import com.app.fku.hepsiburada.entity.HbSepetAlim;
import com.app.fku.hepsiburada.entity.HbUrun;
import com.app.fku.hepsiburada.fonksiyon.impl.HbSepetService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbUrunService;
import com.app.fku.hepsiburada.model.HbUrunModel;
import com.app.fku.hepsiburada.repository.HbKategoriRepository;
import com.app.fku.hepsiburada.repository.HbNoLinkSozlukRepository;
import com.app.fku.hepsiburada.repository.HbSepetAlimRepository;
import com.app.fku.hepsiburada.repository.HbUrunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HbSepetServiceImpl implements HbSepetService {

    @Autowired
    HbNoLinkSozlukRepository hbNoLinkSozlukRepository;

    @Autowired
    HbSepetAlimRepository hbSepetAlimRepository;

    @Override
    public Boolean sepetAlimEkle(String eposta, String hbNo, Integer adet) {
        HbNoLinkSozluk hbNoLinkSozluk = hbNoLinkSozlukRepository.findByHbNo(hbNo);
        HbSepetAlim hbSepetAlim = new HbSepetAlim();
        hbSepetAlim.setDurum(1);
        hbSepetAlim.setAdet(adet);
        hbSepetAlim.setEposta(eposta + "@atlasposta.com");
        hbSepetAlim.setHbNo(hbNo);
        hbSepetAlim.setLink(hbNoLinkSozluk.getLink());
        hbSepetAlim.setKayitTarihi(new Date());
        hbSepetAlimRepository.save(hbSepetAlim);
        return true;
    }

    @Override
    public Boolean sepetAlimEkleToplu(String hbNo, Integer adet) {
        HbNoLinkSozluk hbNoLinkSozluk = hbNoLinkSozlukRepository.findByHbNo(hbNo);
        HbSepetAlim hbSepetAlim = new HbSepetAlim();
        hbSepetAlim.setDurum(1);
        hbSepetAlim.setAdet(adet);
        hbSepetAlim.setEposta("atlastek1@atlasposta.com");
        hbSepetAlim.setHbNo(hbNo);
        hbSepetAlim.setLink(hbNoLinkSozluk.getLink());
        hbSepetAlim.setKayitTarihi(new Date());
        hbSepetAlimRepository.save(hbSepetAlim);

        hbSepetAlim = new HbSepetAlim();
        hbSepetAlim.setDurum(1);
        hbSepetAlim.setAdet(adet);
        hbSepetAlim.setEposta("meryemkaya@atlasposta.com");
        hbSepetAlim.setHbNo(hbNo);
        hbSepetAlim.setLink(hbNoLinkSozluk.getLink());
        hbSepetAlim.setKayitTarihi(new Date());
        hbSepetAlimRepository.save(hbSepetAlim);

        hbSepetAlim = new HbSepetAlim();
        hbSepetAlim.setDurum(1);
        hbSepetAlim.setAdet(adet);
        hbSepetAlim.setEposta("ahmetkara@atlasposta.com");
        hbSepetAlim.setHbNo(hbNo);
        hbSepetAlim.setLink(hbNoLinkSozluk.getLink());
        hbSepetAlim.setKayitTarihi(new Date());
        hbSepetAlimRepository.save(hbSepetAlim);
        return true;
    }
}
