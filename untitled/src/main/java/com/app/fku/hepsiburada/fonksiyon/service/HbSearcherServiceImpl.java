package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.entity.HbUrun;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbSearcherService;
import com.app.fku.hepsiburada.repository.HbTelegramConfRepository;
import com.app.fku.hepsiburada.repository.HbUrunRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HbSearcherServiceImpl implements HbSearcherService {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    HbUrunRepository hbUrunRepository;

    @Autowired
    HbTelegramConfRepository hbTelegramConfRepository;

    @Override
    public boolean menudenUrunBul(HbKategori hbKategori, Long pageNumber, Long maxPageNumber, int siralamaDeger) throws InterruptedException {
        if (pageNumber > maxPageNumber) {
            return true;
        }

        try {
            Document hepsiBuradaDoc = null;
            try {
                if (hbKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    if (siralamaDeger == 0) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 1) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&siralama=coksatan&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 2) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&siralama=yorumsayisi&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 3) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&siralama=begenisayisi&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 4) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&siralama=artanfiyat&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 5) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&siralama=azalanfiyat&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 6) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&siralama=enyeni&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 7) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&siralama=degerlendirmepuani&sayfa=" + pageNumber);
                    }
                } else {
                    if (siralamaDeger == 0) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?sayfa=" + pageNumber);
                    } else if (siralamaDeger == 1) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?siralama=coksatan&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 2) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?siralama=yorumsayisi&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 3) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?siralama=begenisayisi&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 4) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?siralama=artanfiyat&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 5) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?siralama=azalanfiyat&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 6) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?siralama=enyeni&sayfa=" + pageNumber);
                    } else if (siralamaDeger == 7) {
                        hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?siralama=degerlendirmepuani&sayfa=" + pageNumber);
                    }
                }
                if (hepsiBuradaDoc == null) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Thread.sleep(3000L);
                return false;
            }

            Elements urunElementList = hepsiBuradaDoc.getElementsByClass("productListContent-item");
            for (Element urunElement: urunElementList) {
                try {
                    String[] hbNoArray = urunElement.childNodes().get(0).childNodes().get(0).attributes().get("href").split("-p-");
                    String hbNo = hbNoArray[hbNoArray.length - 1];
                    String modelAdi = urunElement.getElementsByAttributeValue("data-test-id", "product-card-name").get(0).childNodes().get(0).toString();
                    modelYoksaKaydet(hbNo, modelAdi, hbKategori);
                } catch (Exception e) {
                    System.out.println("a");
                }
            }

            try {
                String urunSayisiStr = hepsiBuradaDoc.getElementsByClass("paginatorStyle-label").get(0).childNodes().get(6).toString();
                Long urunSayisi = new Long(urunSayisiStr);
                Long sayfaSayisi = urunSayisi / 24;
                if (sayfaSayisi * 24 == urunSayisi) {

                } else {
                    sayfaSayisi++;
                }
                maxPageNumber = sayfaSayisi;
            } catch (Exception e) {

            }

            menudenUrunBul(hbKategori, pageNumber+1, maxPageNumber, siralamaDeger);
        } catch (Exception e) {
            Thread.sleep(3000L);
            return false;
        }
        return true;
    }

    private void modelYoksaKaydet(String hbNo, String modelAdi, HbKategori hbKategori) {
        List<HbUrun> urunList = hbUrunRepository.findByModel(modelAdi);
        if (urunList != null && urunList.size() > 0) {
            //ürün var kaydetmeye gerek yok
            return;
        } else {
            HbUrun urun = new HbUrun();
            urun.setHbKategori(hbKategori);
            urun.setModel(modelAdi);
            urun.setHbNo(hbNo);
            urun.setKayitTarihi(new Date());
            urun.setKontrolEdilsinMi(true);
            hbUrunRepository.save(urun);
        }
    }
}
