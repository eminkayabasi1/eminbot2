package com.app.fku.trendyol.fonksiyon.impl;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.trendyol.entity.*;
import com.app.fku.trendyol.fonksiyon.service.TrendyolGenelService;
import com.app.fku.trendyol.fonksiyon.service.TrendyolUrunToplaFonksiyon;
import com.app.fku.trendyol.repository.TrendyolTelegramConfRepository;
import com.app.fku.trendyol.repository.TrendyolUrunRepository;
import com.app.fku.trendyol.repository.TyIstatistikRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TrendyolUrunToplaFonksiyonImpl implements TrendyolUrunToplaFonksiyon {

    @Autowired
    TrendyolUrunRepository tyUrunRepository;

    @Autowired
    TrendyolGenelService tyGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    TrendyolTelegramConfRepository trendyolTelegramConfRepository;

    @Autowired
    TyIstatistikRepository tyIstatistikRepository;

    @Override
    public HashMap<String, GenelUrunModel> menudenUrunBul(TrendyolKategori tyKategori, int pageNumber, int maxSayfa, int hataCount, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, HashMap<String, String> yasakliUrunHashMap, Boolean ilkMi, HashMap<String, List<TrendyolKahraman>> kahramanHashMap) throws IOException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        try {
            Document tyDoc = null;

            for (int i = 1; i > 0; i++) {
                if (tyKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                    String urlSayfa = "";
                    if (pageNumber > 1) {
                        urlSayfa = "&pi=" + pageNumber;
                    }
                    tyDoc = tyGenelService.urleGit(tyKategori.getSayfaAdresi() + urlSayfa);
                } else {
                    String urlSayfa = "";
                    if (pageNumber > 1) {
                        urlSayfa = "?pi=" + pageNumber;
                    }
                    tyDoc = tyGenelService.urleGit(tyKategori.getSayfaAdresi() + urlSayfa);
                }

                if (tyDoc == null) {
//                    throw new Exception("tyDoc null (Captcha) - " + tyKategori.getKategoriAdi());
                } else {
                    break;
                }
            }

            Random rand = new Random();
            int sleepRand = rand.nextInt(6);
            TimeUnit.SECONDS.sleep(sleepRand);

            String toplamUrunStr = tyDoc.getElementsByClass("dscrptn").toString().split("için ")[1].split(" ")[0];
            if (toplamUrunStr.equals("ürün")) {
                //ürün yok
                return new HashMap<>();
            } else {
                int toplamUrun = Integer.parseInt(toplamUrunStr);
                maxSayfa = toplamUrun / 24;
                if (maxSayfa * 24 != toplamUrun) {
                    maxSayfa = maxSayfa + 1;
                }
            }

            Elements urunListesi = tyDoc.getElementsByClass("prdct-cntnr-wrppr");
            tyDoc.getElementsByClass("srch-ttl-cntnr-wrppr");
            Elements urunler = urunListesi.get(0).getElementsByClass("p-card-wrppr");

            for (Element urun: urunler) {
                String dataId = urun.attributes().get("data-id");
                if (!urunAdiKontrol(dataId, yasakliUrunHashMap)) {
                    continue;
                }

                Element twoLineElement = urun.getElementsByClass("two-line-text").get(0);
                Element markaElement = twoLineElement.getElementsByClass("prdct-desc-cntnr-ttl").get(0);
                String marka = markaElement.attributes().get("title");
                Element modelElement = twoLineElement.getElementsByClass("prdct-desc-cntnr-name").get(0);
                String model = modelElement.attributes().get("title");
                //prc-box-dscntd --> sepet
                //prc-box-sllng  --> normal
                String fiyatStr = "";
                try {
                    Element fiyatElement = urun.getElementsByClass("prc-box-dscntd").get(0);
                    fiyatStr = fiyatElement.childNodes().get(0).toString();
                } catch (Exception e) {
                    Element fiyatElement = urun.getElementsByClass("prc-box-sllng").get(0);
                    fiyatStr = fiyatElement.childNodes().get(0).toString();
                }

                if (fiyatStr == null || fiyatStr.equals("")) {
                    logService.trendyolLogYaz("Ty Fiyat Okuyamadım --> " + tyKategori.getKategoriAdi() + " --> " + pageNumber);
                }

                Double fiyat = new Double(fiyatStr.split(" ")[0].replace(".", "").replace(",", "."));
                Long yeniFiyat = fiyat.longValue();

                //Long eskiFiyat = urunHashMap.get(dataId);
                GenelUrunModel urunModel = urunHashMap.get(dataId);

                if (urunModel == null) {
                    //yeni kayıt
                    urunModel = new GenelUrunModel();
                    urunModel.setUrunId(dataId);
                    urunModel.setEskiFiyat(yeniFiyat);
                    urunModel.setSonMesajTarihi(new Date());
                    yeniUrunHashMap.put(dataId, urunModel);
                    if (!ilkMi) {
                        //mesaj at
                        String akakceLink = model.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");
                        String msjMarka = marka.replace(" ", "-");

                        String mesaj = "" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + marka + " - " + model + "%0A" +
                                "Satıcı: " + tyKategori.getKategoriAdi() + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:https://www.trendyol.com/" + msjMarka + "/a-p-" + dataId + " %0A " +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, tyKategori, marka + " - " + model, 0L, yeniFiyat, dataId, kahramanHashMap);
                    }

                } else {
                    if (yeniFiyat < urunModel.getEskiFiyat()) {
                        //mesaj at
                        Long diff = new Date().getTime() - urunModel.getSonMesajTarihi().getTime();
                        Long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                        if (minutes > 10) {
                            String akakceLink = model.replace(" ", "%2B");
                            akakceLink = akakceLink.replace("+", "");
                            String msjMarka = marka.replace(" ", "-");

                            Long indirim = urunModel.getEskiFiyat() - yeniFiyat;
                            Long indYuzde = 100 * indirim / urunModel.getEskiFiyat();

                            if (indYuzde > tyKategori.getIndirimYuzdesi()) {
                                String mesaj = "" +
                                        "Ürün Adı: " + marka + " - " + model + "%0A" +
                                        "Satıcı: " + tyKategori.getKategoriAdi() + "%0A" +
                                        "Eski Fiyat: " + nf.format(urunModel.getEskiFiyat()) + "%0A" +
                                        "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                        "İndirim Yüzde: " + nf.format(indYuzde) + "%0A" +
                                        "İndirim: " + nf.format(indirim) + "%0A" +
                                        "Ürün Link:https://www.trendyol.com/" + msjMarka + "/a-p-" + dataId + " %0A " +
                                        "Tarih: " + sdf.format(new Date()) + "%0A" +
                                        "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                        "***Generated By Emin KAYABASI***";

                                telegramMesajGonder(mesaj, tyKategori, marka + " - " + model, indYuzde, yeniFiyat, dataId, kahramanHashMap);
                                urunModel.setSonMesajTarihi(new Date());
                            }
                        }
                    }
                    urunModel.setEskiFiyat(yeniFiyat);
                    //urunHashMap.remove(dataId);
                    yeniUrunHashMap.put(dataId, urunModel);
                }
            }
            //Thread.sleep(5000L);
            if (pageNumber < maxSayfa) {
                yeniUrunHashMap = menudenUrunBul(tyKategori, pageNumber + 1, maxSayfa, hataCount, urunHashMap, yeniUrunHashMap, yasakliUrunHashMap, ilkMi, kahramanHashMap);
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            logService.trendyolLogYaz("Ty Hata Kategori:" + tyKategori.getKategoriAdi() + " - Sayfa:" + pageNumber);
            Thread.sleep(10000L);
            return urunHashMap;
        }
    }

    private TrendyolUrun urunVarMi(String dataId, TrendyolKategori tyKategori, HashMap<String, TrendyolUrun> trendyolUrunHashMap) {
        TrendyolUrun tyUrun = trendyolUrunHashMap.get(dataId);
        if (tyUrun != null) {
            return tyUrun;
        }
        return tyUrunRepository.findByTyNoAndTyKategori(dataId, tyKategori);
    }

    private boolean kontrol(TrendyolUrun tyUrun, Double fiyat) {

        Double eskiFiyat = tyUrun.getGuncelFiyat();
        Double indirimYuzdesi = tyUrun.getTyKategori().getIndirimYuzdesi();
        Double beklenenFiyat = eskiFiyat * (100 - indirimYuzdesi) / 100;

        if (beklenenFiyat > fiyat) {
            //indirim var
            //chatId -701237811
            return true;
        } else {
            tyUrun.setSonFiyatKontrolTarihi(new Date());
            tyUrun.setGuncelFiyat(fiyat);
            if (fiyat < tyUrun.getMinFiyat()) {
                tyUrun.setMinFiyat(fiyat);
                tyUrun.setMinFiyatTarihi(new Date());
            }
            tyUrun = tyUrunRepository.save(tyUrun);
        }
        return false;
    }

    private void telegramMesajGonder(String mesaj, TrendyolKategori tyKategori, String urunId, Long indYuzde, Long fiyat, String dataId, HashMap<String, List<TrendyolKahraman>> kahramanHashMap) throws IOException, InterruptedException {
        List<TrendyolTelegramConf> trendyolTelegramConfList = trendyolTelegramConfRepository.findByTrendyolKategori(tyKategori);

        for (TrendyolTelegramConf trendyolTelegramConf: trendyolTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, trendyolTelegramConf.getTelegramChatId(), urunId, "5784922114:AAGmwKon4HZUfvrx6l3Imemg7szDuVIAs54");
        }

        if (indYuzde > 40) {
            genelService.telegramBombaGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("DYSON")) {
            genelService.telegramDysonGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("AIRFRYER") ||
                urunId.toUpperCase().contains("AİRFRYER") ||
                urunId.toUpperCase().contains("AIR FRYER") ||
                urunId.toUpperCase().contains("AİR FRYER") ||
                urunId.toUpperCase().contains("FRITOZ") ||
                urunId.toUpperCase().contains("FRİTOZ") ||
                urunId.toUpperCase().contains("FRİTÖZ") ||
                urunId.toUpperCase().contains("FRITÖZ")) {
            genelService.telegramFritozGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("FISSLER") ||
                urunId.toUpperCase().contains("FİSSLER")) {
            genelService.telegramFisslerGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("PLAYSTATION") ||
                urunId.toUpperCase().contains("PLAYSTATİON") ||
                urunId.toUpperCase().contains("PLAY STATION") ||
                urunId.toUpperCase().contains("PLAY STATİON")) {
            genelService.telegramPlayStationGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("IPHONE 14") ||
                urunId.toUpperCase().contains("İPHONE 14") ||
                urunId.toUpperCase().contains("IPHONE14") ||
                urunId.toUpperCase().contains("İPHONE14")) {
            genelService.telegramIphone14Gonder(mesaj, urunId);
        }

        if (fiyat <= 50) {
            genelService.telegramFiyatHatasiGonder(mesaj, urunId);
        }

        List<TrendyolKahraman> trendyolKahramanList = kahramanHashMap.get(dataId);
        if (trendyolKahramanList != null && trendyolKahramanList.size() > 0) {
            for (TrendyolKahraman trendyolKahraman: trendyolKahramanList) {
                genelService.telegramKahramanGonder(mesaj, trendyolKahraman.getTelegramChatId(), dataId);
            }
        }
    }

    public void istatistikSayaciEkle(Long sayac) throws UnknownHostException {
        String hostname = genelService.getHostName();
        Date nowDate = new Date();
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Istanbul"));
        c.setTime(nowDate);
        int yil = c.get(Calendar.YEAR);
        int ay = c.get(Calendar.MONTH);
        int gun = c.get(Calendar.DAY_OF_MONTH);
        int saat = c.get(Calendar.HOUR_OF_DAY);

        List<TyIstatistik> tyIstatistikList = tyIstatistikRepository.findByHostnameAndYilAndAyAndGunAndSaatOrderById(hostname, yil, ay, gun, saat);

        TyIstatistik tyIstatistik = null;
        if (tyIstatistikList != null && tyIstatistikList.size() > 0) {
            tyIstatistik = tyIstatistikList.get(0);
        }

        if (tyIstatistik == null) {
            tyIstatistik = new TyIstatistik();
            tyIstatistik.setHostname(hostname);
            tyIstatistik.setYil(yil);
            tyIstatistik.setAy(ay);
            tyIstatistik.setGun(gun);
            tyIstatistik.setSaat(saat);
            tyIstatistik.setSayac(sayac);
        } else {
            if (tyIstatistik.getSayac() == null) {
                tyIstatistik.setSayac(sayac);
            } else {
                tyIstatistik.setSayac(tyIstatistik.getSayac() + sayac);
            }
        }
        tyIstatistikRepository.save(tyIstatistik);
    }

    @Override
    public HashMap<String, String> yildizKontrol(TrendyolKategori tyKategori, HashMap<String, String> urunHashMap, HashMap<String, String> yeniUrunHashMap, int pageNumber, int maxSayfa) throws Exception {

        if (maxSayfa < pageNumber) {
            return yeniUrunHashMap;
        }

        Document tyDoc = null;
        if (tyKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
            String urlSayfa = "";
            if (pageNumber > 1) {
                urlSayfa = "&pi=" + pageNumber;
            }
            for (int i = 1; i > 0; i++) {
                tyDoc = tyGenelService.urleGit(tyKategori.getSayfaAdresi() + urlSayfa);
                if (tyDoc != null) {
                    break;
                }
            }
        } else {
            String urlSayfa = "";
            if (pageNumber > 1) {
                urlSayfa = "?pi=" + pageNumber;
            }
            for (int i = 1; i > 0; i++) {
                tyDoc = tyGenelService.urleGit(tyKategori.getSayfaAdresi() + urlSayfa);
                if (tyDoc != null) {
                    break;
                }
            }
        }

        String toplamUrunStr = tyDoc.getElementsByClass("dscrptn").toString().split(" sonuç")[0].split(" ")[2];
        int toplamUrun = Integer.parseInt(toplamUrunStr);
        maxSayfa = toplamUrun / 24;
        if (maxSayfa * 24 != toplamUrun) {
            maxSayfa = maxSayfa + 1;
        }

        Elements urunListesi = tyDoc.getElementsByClass("prdct-cntnr-wrppr");
        tyDoc.getElementsByClass("srch-ttl-cntnr-wrppr");
        Elements urunler = urunListesi.get(0).getElementsByClass("p-card-wrppr");

        for (Element urun: urunler) {
            String dataId = urun.attributes().get("data-id");
            String markaModel = urunHashMap.get(dataId);
            if (!ObjectUtils.isEmpty(markaModel)) {
                //önceki tur varmış
                yeniUrunHashMap.put(dataId, markaModel);
                urunHashMap.put(dataId, markaModel);
                continue;
            }

            Element twoLineElement = urun.getElementsByClass("two-line-text").get(0);
            Element markaElement = twoLineElement.getElementsByClass("prdct-desc-cntnr-ttl").get(0);
            String marka = markaElement.attributes().get("title");
            Element modelElement = twoLineElement.getElementsByClass("prdct-desc-cntnr-name").get(0);
            String model = modelElement.attributes().get("title");

            yeniUrunHashMap.put(dataId, marka + " " + model);
            urunHashMap.put(dataId, marka + " " + model);

            //prc-box-dscntd --> sepet
            //prc-box-sllng  --> normal
            String fiyatStr = "";
            try {
                Element fiyatElement = urun.getElementsByClass("prc-box-dscntd").get(0);
                fiyatStr = fiyatElement.childNodes().get(0).toString();
            } catch (Exception e) {
                Element fiyatElement = urun.getElementsByClass("prc-box-sllng").get(0);
                fiyatStr = fiyatElement.childNodes().get(0).toString();
            }

            if (fiyatStr == null || fiyatStr.equals("")) {
                logService.trendyolLogYaz("Ty Fiyat Okuyamadım --> " + tyKategori.getKategoriAdi() + " --> " + pageNumber);
            }

            Double fiyat = new Double(fiyatStr.split(" ")[0].replace(".", "").replace(",", "."));

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            NumberFormat nf = new DecimalFormat("#0.00");

            String msjMarka = marka.replace(" ", "-");
            String tarihStr = sdf.format(new Date());
            String akakceLink = model.replace(" ", "%2B");
            String mesaj =  "** " + //tyKategori.getKategoriKisaAd() + " ** " + " %0A " +
                    marka + " %0A " +
                    model + " %0A " +
                    "*Fiyat: " + nf.format(fiyat) + " %0A " +
                    "*Tarih: " + tarihStr + " %0A " +
                    "*Ürün Link:https://www.trendyol.com/" + msjMarka + "/a-p-" + dataId + " %0A " +
                    "*Hostname\t: " + genelService.getHostName() + " %0A " +
                    "*Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                    "****Generated By Emin Kayabaşı****";

            try {
                if (urunHashMap.size() > 0) {
                    //genelService.telegramMesajGonder(mesaj, "-658235492", dataId);
                }
            } catch (Exception e) {

            }

        }

        yeniUrunHashMap = yildizKontrol(tyKategori, urunHashMap, yeniUrunHashMap, pageNumber + 1, maxSayfa);
        return yeniUrunHashMap;
    }

    private boolean urunAdiKontrol(String dataId, HashMap<String, String> yasakliUrunHashMap) {
        String yasak = yasakliUrunHashMap.get(dataId);
        if (yasak != null && !yasak.equals("")) {
            return false;
        }

        return true;
    }
}
