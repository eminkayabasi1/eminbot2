package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.hepsiburada.entity.*;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.hepsiburada.fonksiyon.impl.HbFiyatToplaFonksiyon;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.repository.*;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

@Service
public class HbFiyatToplaFonksiyonImpl implements HbFiyatToplaFonksiyon {
/**
    @Autowired
    HbFiyatRepository hbFiyatRepository;

    @Autowired
    HbKategoriRepository hbKategoriRepository;

    @Autowired
    HbUrunRepository hbUrunRepository;

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    HbEkranService sepetService;

    @Autowired
    HbSepetEklemeRepository hbSepetEklemeRepository;

    @Autowired
    HbWorkerIstatistikRepository hbWorkerIstatistikRepository;

    @Override
    public boolean sayfadanFiyatBul(HbUrun hbUrun, int hataCount, boolean hbNoGuncellenecekMi) throws IOException, InterruptedException {
        String hbNo = hbUrun.getHbNo();
        try {
            Document hepsiBuradaDoc = null;
            try {
                hepsiBuradaDoc = hbGenelService.urleGit("https://www.hepsiburada.com/a-p-" + hbNo);
                if (hepsiBuradaDoc == null) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Thread.sleep(3000L);
                return false;
            }

            //ÜRÜNÜN FİYATI START
            Elements ayniElements = hepsiBuradaDoc.getElementsByClass("variants-content ");

            if (ayniElements.size() > 0) {
                for (Element element: ayniElements) {
                    Attributes attributes = element.childNode(1).attributes();
                    String renk = attributes.get("value");

                    Element checkedElement = element.getElementById("pageVariantPrice");
                    if (checkedElement != null) {
                        //hbNo'nun ait olduğu renk bu
                        HbUrun yeniUrun = hbUrunRepository.findByModelAndRenk(hbUrun.getModel(), renk);
                        if (yeniUrun == null) {
                            yeniUrun = hbUrunRepository.findByModelAndRenk(hbUrun.getModel(), null);
                        }
                        if (yeniUrun.getHbNo() == null || yeniUrun.getHbNo().equals("")) {
                            yeniUrun.setHbNo(hbNo);
                        }
                        if (yeniUrun.getRenk() == null || yeniUrun.getRenk().equals("")) {
                            yeniUrun.setRenk(renk);
                        }
                        yeniUrun = hbUrunRepository.save(yeniUrun);
                        hbUrunRepository.flush();
                        hbUrun = yeniUrun;
                    } else {
                        //hbNo'nun ait olmadığı renk
                        HbUrun yeniUrun = hbUrunRepository.findByModelAndRenk(hbUrun.getModel(), renk);
                        if (yeniUrun == null) {
                            yeniUrun = new HbUrun();
                            yeniUrun.setModel(hbUrun.getModel());
                            yeniUrun.setRenk(renk);
                            yeniUrun.setHbKategori(hbUrun.getHbKategori());
                            yeniUrun = urunYoksaKaydet(yeniUrun);
                        }
                        hbUrun = yeniUrun;
                    }

                    String fiyatStr = element.childNode(3).childNode(3).childNode(3).childNode(0).toString();
                    fiyatStr = fiyatStr.replaceAll("^\\s+", "");
                    fiyatStr = fiyatStr.replaceAll("\\s+$", "");
                    String[] fiyatArr = fiyatStr.split(" ");
                    fiyatStr = fiyatArr[0];
                    fiyatStr = fiyatStr.replace(".", "");
                    fiyatStr = fiyatStr.replace(",", ".");

                    Double fiyatD = fiyatStringToDouble(fiyatStr);

                    if (fiyatD != null) {
                        HbFiyat hbFiyat = new HbFiyat();
                        hbFiyat.setHbUrun(hbUrun);
                        hbFiyat.setFiyat(fiyatD);
                        hbFiyat.setKayitTarihi(new Date());
                        hbFiyat.setHostname(genelService.getHostName());
                        fiyatKarsilastir(hbUrun, hbFiyat);
//                        return true;
                    }
                }
                hbUrun = sonFiyatKontrolTarihiGuncelle(hbUrun);
                return true;
            } else {
                Elements fiyatElements = hepsiBuradaDoc.getElementsByClass("extra-discount-price");
                String fiyatStr = fiyatElements.get(0).childNode(0).childNode(0).toString();
                fiyatStr = fiyatStr.replaceAll("^\\s+", "");
                fiyatStr = fiyatStr.replaceAll("\\s+$", "");
                String[] fiyatArr = fiyatStr.split(" ");
                fiyatStr = fiyatArr[0];
                fiyatStr = fiyatStr.replace(".", "");
                fiyatStr = fiyatStr.replace(",", ".");
                Double fiyatD = fiyatStringToDouble(fiyatStr);

                if (fiyatD != null) {
                    HbFiyat hbFiyat = new HbFiyat();
                    hbFiyat.setHbUrun(hbUrun);
                    hbFiyat.setFiyat(fiyatD);
                    hbFiyat.setKayitTarihi(new Date());
                    hbFiyat.setHostname(genelService.getHostName());
                    fiyatKarsilastir(hbUrun, hbFiyat);
//                    return true;
                }
                hbUrun = sonFiyatKontrolTarihiGuncelle(hbUrun);
                return true;
            }
            //ÜRÜNÜN FİYATI END
        } catch (Exception e) {
            Thread.sleep(3000L);
            return false;
        }
    }

    @Override
    public void menudenUrunBul(HbKategori hbKategori, int pageNumber, int hataCount) throws IOException, InterruptedException {
        try {
            Document hepsiBuradaDoc = null;
            try {
                if (hbKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&sayfa=" + pageNumber);
                } else {
                    hepsiBuradaDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?sayfa=" + pageNumber);
                }
                if (hepsiBuradaDoc == null) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Thread.sleep(3000L);
                if (hataCount < 10) {
                    menudenUrunBul(hbKategori, pageNumber, hataCount + 1);
                }
            }

            //KATEGORİ ADI BOŞSA DOLDURMAK START
            if (hbKategori.getKategoriAdi() == null || hbKategori.getKategoriAdi().equals("")) {
                String kategoriStr = "";
                Elements kategoriElements = hepsiBuradaDoc.getElementsByClass("breadcrumbs");
                Element kategoriElement = kategoriElements.get(1);
                for (Element kategoriChild: kategoriElement.children()) {
                    if (kategoriChild.childNodeSize() == 0) {
                        continue;
                    }
                    Attributes kategoriAttributes = kategoriChild.childNode(1).attributes();
                    if (kategoriStr.equals("")) {
                        kategoriStr += kategoriAttributes.get("title");
                    } else {
                        kategoriStr += " - " + kategoriAttributes.get("title");
                    }
                }
                hbKategori.setKategoriAdi(kategoriStr);
                hbKategoriRepository.save(hbKategori);
                hbKategoriRepository.flush();
            }
            //KATEGORİ ADI BOŞSA DOLDURMAK END

            Elements urunElements = hepsiBuradaDoc.getElementsByClass("box product no-hover hb-placeholder");
            Elements urunElements2 = hepsiBuradaDoc.getElementsByClass("box product  hb-placeholder");
            for (Element element: urunElements2) {
                urunElements.add(element);
            }

            for (Element element: urunElements) {
                Attributes hbNoAttributes = null;
                if (element.childNodeSize() == 7) {
                    hbNoAttributes = element.childNode(5).attributes();
                } else {
                    hbNoAttributes = element.childNode(7).attributes();
                }

                String hbNo = hbNoAttributes.get("data-sku");
                Elements titleElements = element.getElementsByClass("product-title title");
                if (titleElements == null) {
                    continue;
                }
                if (titleElements.size() < 1) {
                    continue;
                }

                Attributes titleAttributes = titleElements.get(0).attributes();

                String model = titleAttributes.get("title");

                HbUrun hbUrun = hbUrunRepository.findByHbNo(hbNo);
                if (hbUrun == null) {
                    List<HbUrun> hbUrunList = new ArrayList<HbUrun>();
                    hbUrunList = hbUrunRepository.findByModel(model);
                    if (hbUrunList != null && hbUrunList.size() > 0) {
                        //model var, fakat bu rengin hbNo'su yok
                        hbUrun = new HbUrun();
//                        hbUrun.setHbNo(hbNo);TODO:burası karışık gibi
                        hbUrun.setModel(model);
                        hbUrun.setHbKategori(hbKategori);
                        sayfadanFiyatBul(hbUrun, 0, true);
                    } else {
                        //bu urun hiç yok
                        hbUrun = new HbUrun();
                        hbUrun.setHbNo(hbNo);
                        hbUrun.setModel(model);
                        hbUrun.setHbKategori(hbKategori);
                        hbUrun = urunYoksaKaydet(hbUrun);
                        sayfadanFiyatBul(hbUrun, 0, false);
                    }
                }
            }

            Element pageElement = hepsiBuradaDoc.getElementById("pagination");
            if (pageElement == null) {
                menudenUrunBul(hbKategori, pageNumber + 1, 0);
            }
            if (pageElement.childNodeSize() < 2) {
                menudenUrunBul(hbKategori, pageNumber + 1, 0);
            }

            String pageSize = "";
            try {
                pageSize = pageElement.childNode(1).childNode(pageElement.childNode(1).childNodeSize() - 2).childNode(1).childNode(0).toString();
            } catch (Exception e) {
                menudenUrunBul(hbKategori, pageNumber + 1, 0);
            }
            int totalPageNumber = Integer.valueOf(pageSize);

            if (pageNumber < totalPageNumber) {
                menudenUrunBul(hbKategori, pageNumber + 1, 0);
            }
        } catch (Exception e) {
            Thread.sleep(3000L);
            if (hataCount < 10) {
                menudenUrunBul(hbKategori, pageNumber, hataCount + 1);
            }
        }
    }

    private HbKategori kategoriYoksaKaydet(String kategoriAdi) {
        HbKategori hbKategori = hbKategoriRepository.findByKategoriAdi(kategoriAdi);
        if (hbKategori != null) {
            return hbKategori;
        }

        hbKategori = new HbKategori();
        hbKategori.setKategoriAdi(kategoriAdi);
        hbKategori.setKayitTarihi(new Date());
        hbKategori = hbKategoriRepository.save(hbKategori);
        hbKategoriRepository.flush();
        return hbKategori;
    }

    private HbUrun urunYoksaKaydet(HbUrun hbUrun) {
        HbUrun tmpHbUrun = null;
        if (hbUrun.getHbNo() != null && !hbUrun.getHbNo().equals("")) {
            tmpHbUrun = hbUrunRepository.findByHbNo(hbUrun.getHbNo());
        }
        if (tmpHbUrun == null) {
            //model ve renk üzerinden kontrol
            HbUrun ikinciKontrol = hbUrunRepository.findByModelAndRenk(hbUrun.getModel(), hbUrun.getRenk());
            if (ikinciKontrol == null) {
                tmpHbUrun = new HbUrun();
                tmpHbUrun.setKayitTarihi(new Date());
                tmpHbUrun.setKontrolEdilsinMi(true);
                tmpHbUrun.setHbNo(hbUrun.getHbNo());
                tmpHbUrun.setModel(hbUrun.getModel());
                tmpHbUrun.setRenk(hbUrun.getRenk());
                tmpHbUrun.setHbKategori(hbUrun.getHbKategori());
                tmpHbUrun = hbUrunRepository.save(tmpHbUrun);
                hbUrunRepository.flush();
            }
        }

        return tmpHbUrun;
    }

    public Double fiyatStringToDouble(String fiyatStr) {
        Double fiyatD = null;
        try {
            fiyatD = Double.valueOf(fiyatStr);
        } catch (Exception e) {

        }
        return fiyatD;
    }

    public void fiyatKarsilastir(HbUrun hbUrun, HbFiyat hbFiyat) throws IOException, InterruptedException {

        if (hbFiyat.getFiyat() > 0) {
            hbUrun.setGuncelFiyat(hbFiyat.getFiyat());
            if (hbUrun.getBeklenenFiyat() == null || hbUrun.getMinFiyat() == null) {
                if (hbUrun.getBeklenenFiyat() == null) {
                    hbUrun.setBeklenenFiyat(999999D);
                }
                if (hbUrun.getMinFiyat() == null) {
                    hbUrun.setMinFiyat(hbFiyat.getFiyat());
                }
                hbUrun.setSonFiyatKontrolTarihi(new Date());
                hbUrun = hbUrunRepository.save(hbUrun);
                hbUrunRepository.flush();
            }

            if (hbFiyat.getFiyat() < hbUrun.getBeklenenFiyat()) {

                Double indirimYuzdesi = hbUrun.getHbKategori().getIndirimYuzdesi();
                Double beklenenIndirimFiyat = hbFiyat.getFiyat() * (100D - indirimYuzdesi) / 100;
                Double beklenendenDusulecekFiyat = hbFiyat.getFiyat() - beklenenIndirimFiyat;

                if (beklenendenDusulecekFiyat < hbUrun.getHbKategori().getMinIndirim()) {
                    beklenendenDusulecekFiyat = hbUrun.getHbKategori().getMinIndirim();
                }

                if (beklenendenDusulecekFiyat > hbUrun.getHbKategori().getMaxIndirim()) {
                    beklenendenDusulecekFiyat = hbUrun.getHbKategori().getMaxIndirim();
                }

                Double guncelBeklenenFiyat = hbUrunRepository.yenidenCekId(hbUrun.getId());
                hbUrun.setBeklenenFiyat(guncelBeklenenFiyat);

                if (hbFiyat.getFiyat() < hbUrun.getBeklenenFiyat()) {
                    hbFiyat.setEskiBeklenenFiyat(hbUrun.getBeklenenFiyat());

                    if (!hbUrun.getBeklenenFiyat().equals(999999D)) {
                        hbGenelService.mailGonderimi(hbUrun, hbFiyat);
                    }

                    if (hbUrun.getBeklenenFiyat() != null && hbUrun.getBeklenenFiyat().equals(999999D)) {
                        hbUrun.setBeklenenFiyat(hbFiyat.getFiyat() - beklenendenDusulecekFiyat);
                    }
                    hbFiyat = hbFiyatRepository.save(hbFiyat);
                    hbFiyatRepository.flush();
                    hbUrun.setSonFiyatKontrolTarihi(new Date());
                    hbUrunRepository.save(hbUrun);
                    hbUrunRepository.flush();
                }
            }

            if (hbFiyat.getFiyat() < hbUrun.getMinFiyat()) {
                hbUrun.setMinFiyat(hbFiyat.getFiyat());
                hbUrun.setSonFiyatKontrolTarihi(new Date());
                hbUrun = hbUrunRepository.save(hbUrun);
                hbUrunRepository.flush();
            }

            hbUrun.setGuncelFiyat(hbFiyat.getFiyat());
            hbUrun.setSonFiyatKontrolTarihi(new Date());
            hbUrunRepository.save(hbUrun);
            hbUrunRepository.flush();
        }
    }

    public HbUrun sonFiyatKontrolTarihiGuncelle(HbUrun hbUrun) {
        hbUrun.setSonFiyatKontrolTarihi(new Date());
        hbUrun = hbUrunRepository.save(hbUrun);
        hbUrunRepository.flush();
        return hbUrun;
    }

    public void sepeteEklemeSureci(HbUrun hbUrun) {
        HbSepetEkleme hbSepetEkleme = new HbSepetEkleme();
        hbSepetEkleme.setAdet(1L);
        hbSepetEkleme.setEklendiMi(EvetHayirEnum.HAYIR);
        hbSepetEkleme.setHbUrun(hbUrun);
        hbSepetEkleme.setKayitTarihi(new Date());
        hbSepetEklemeRepository.save(hbSepetEkleme);
    }

    @Override
    public void istatistikSayaciEkle(Long sayac) throws UnknownHostException {
        String hostname = genelService.getHostName();
        Date nowDate = new Date();
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Istanbul"));
        c.setTime(nowDate);
        int yil = c.get(Calendar.YEAR);
        int ay = c.get(Calendar.MONTH);
        int gun = c.get(Calendar.DAY_OF_MONTH);
        int saat = c.get(Calendar.HOUR_OF_DAY);

        List<HbWorkerIstatistik> hbWorkerIstatistikList = hbWorkerIstatistikRepository.findByHostnameAndYilAndAyAndGunAndSaatOrderById(hostname, yil, ay, gun, saat);

        HbWorkerIstatistik hbWorkerIstatistik = null;
        if (hbWorkerIstatistikList != null && hbWorkerIstatistikList.size() > 0) {
            hbWorkerIstatistik = hbWorkerIstatistikList.get(0);
        }

        if (hbWorkerIstatistik == null) {
            hbWorkerIstatistik = new HbWorkerIstatistik();
            hbWorkerIstatistik.setHostname(hostname);
            hbWorkerIstatistik.setYil(yil);
            hbWorkerIstatistik.setAy(ay);
            hbWorkerIstatistik.setGun(gun);
            hbWorkerIstatistik.setSaat(saat);
            hbWorkerIstatistik.setSayac(sayac);
        } else {
            if (hbWorkerIstatistik.getSayac() == null) {
                hbWorkerIstatistik.setSayac(sayac);
            } else {
                hbWorkerIstatistik.setSayac(hbWorkerIstatistik.getSayac() + sayac);
            }
        }
        hbWorkerIstatistikRepository.save(hbWorkerIstatistik);
        hbWorkerIstatistikRepository.flush();
    }
    */
}
