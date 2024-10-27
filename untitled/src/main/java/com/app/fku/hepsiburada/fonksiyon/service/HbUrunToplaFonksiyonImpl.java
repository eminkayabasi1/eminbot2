package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.hepsiburada.entity.HbKahraman;
import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.entity.HbTelegramConf;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbUrunToplaFonksiyon;
import com.app.fku.hepsiburada.repository.HbTelegramConfRepository;
import com.app.fku.trendyol.entity.TrendyolKahraman;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class HbUrunToplaFonksiyonImpl implements HbUrunToplaFonksiyon {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    @Autowired
    HbTelegramConfRepository hbTelegramConfRepository;

    @Override
    public HashMap<String, GenelUrunModel> menudenUrunBul(HbKategori hbKategori, Integer page, Integer maxPage, HashMap<String, GenelUrunModel> urunHashMap, HashMap<String, GenelUrunModel> yeniUrunHashMap, boolean ilkMi, HashMap<String, String> yasakliUrunHashMap, HashMap<String, List<HbKahraman>> kahramanHashMap) throws IOException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");
        try {
            Document hbDoc = null;

            if (hbKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                hbDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "&sayfa=" + page);
            } else if (hbKategori.getFiltreliMi().equals(EvetHayirEnum.HAYIR)) {
                hbDoc = hbGenelService.urleGit(hbKategori.getSayfaAdresi() + "?sayfa=" + page);
            }

            if (hbDoc == null) {
                throw new Exception("hbNewDoc null (Captcha)");
            } else {

            }

            Random rand = new Random();
            int sleepRand = rand.nextInt(6);
            TimeUnit.SECONDS.sleep(sleepRand);

            String maxProductStr = hbDoc.getElementById("stickyVerticalFilter").childNodes().get(0).childNodes().get(0).childNodes().get(0).childNodes().get(0).toString();
            int maxProduct = new Integer(maxProductStr.trim());
            if (maxProduct % 24 == 0) {
                maxPage = maxProduct / 24;
            } else {
                maxPage = maxProduct / 24;
                maxPage = maxPage + 1;
            }

            Elements urunElementList = hbDoc.getElementsByClass("productListContent-zAP0Y5msy8OHn5z7T_K_");
            for (Element urunElement: urunElementList) {
                String urunAdi = urunElement.childNodes().get(0).childNodes().get(0).attributes().get("title");
                if (!urunAdiKontrol(urunAdi, yasakliUrunHashMap)) {
                    continue;
                }

                String urunLink = urunElement.childNodes().get(0).childNodes().get(0).attributes().get("href");
                String hbNo = urunLink.split("-")[urunLink.split("-").length - 1].split("\\?")[0];
                urunLink = urunLink.replace(" ", "%2520");
                urunLink = urunLink.replace("ş", "%25C5%259F");
                urunLink = urunLink.replace("Ş", "%25C5%259E");
                urunLink = urunLink.replace("ı", "%25C4%25B1");
                urunLink = urunLink.replace("İ", "%25C4%25B0");
                urunLink = urunLink.replace("ğ", "%25C4%259F");
                urunLink = urunLink.replace("Ğ", "%25C4%259E");
                urunLink = urunLink.replace("ö", "%25C3%25B6");
                urunLink = urunLink.replace("Ö", "%25C3%2596");
                urunLink = urunLink.replace("ç", "%25C3%25A7");
                urunLink = urunLink.replace("Ç", "%25C3%2587");
                urunLink = urunLink.replace("ü", "%25C3%25BC");
                urunLink = urunLink.replace("Ü", "%25C3%259C");
                String fiyatStr = urunElement.getElementsByAttributeValue("data-test-id", "price-current-price").get(0).childNodes().get(0).toString();

                Long yeniFiyat = new Long(fiyatStr.trim().split(",")[0].replace(".", ""));
                //Long eskiFiyat = urunHashMap.get(urunAdi);
                GenelUrunModel urunModel = urunHashMap.get(urunAdi);

                if (urunModel == null) {
                    //yeni gelmiş ürün
                    urunModel = new GenelUrunModel();
                    urunModel.setUrunId(urunAdi);
                    urunModel.setEskiFiyat(yeniFiyat);
                    urunModel.setSonMesajTarihi(new Date());
                    yeniUrunHashMap.put(urunAdi, urunModel);
                    if (!ilkMi) {
                        //mesaj at
                        String akakceLink = urunAdi.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + urunAdi + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + "https://www.hepsiburada.com" + urunLink + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, hbKategori, urunAdi, 0L, yeniFiyat, hbNo, kahramanHashMap);
                    }
                } else {
                    //zaten var olan ürün
                    if (yeniFiyat < urunModel.getEskiFiyat()) {
                        //mesaj at
                        Long diff = new Date().getTime() - urunModel.getSonMesajTarihi().getTime();
                        Long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                        if (minutes > 30) {
                            String akakceLink = urunAdi.replace(" ", "%2B");
                            akakceLink = akakceLink.replace("+", "");

                            Long indirim = urunModel.getEskiFiyat() - yeniFiyat;
                            Long indYuzde = 100 * indirim / urunModel.getEskiFiyat();

                            if (indYuzde > hbKategori.getIndirimYuzdesi()) {
                                String mesaj = "" +
                                        "Ürün Adı: " + urunAdi + "%0A" +
                                        "Eski Fiyat: " + nf.format(urunModel.getEskiFiyat()) + "%0A" +
                                        "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                        "İndirim Yüzde: " + nf.format(indYuzde) + "%0A" +
                                        "İndirim: " + nf.format(indirim) + "%0A" +
                                        "Ürün Link:" + "https://www.hepsiburada.com" + urunLink + "%0A" +
                                        "Tarih: " + sdf.format(new Date()) + "%0A" +
                                        "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                        "***Generated By Emin KAYABASI***";
                                telegramMesajGonder(mesaj, hbKategori, urunAdi, indYuzde, yeniFiyat, hbNo, kahramanHashMap);
                                urunModel.setSonMesajTarihi(new Date());
                            }
                        }
                    }
                    urunModel.setEskiFiyat(yeniFiyat);
                    //urunHashMap.remove(urunAdi);
                    yeniUrunHashMap.put(urunAdi, urunModel);
                }
            }

            if (maxPage != null) {
                if (page < maxPage) {
                    yeniUrunHashMap = menudenUrunBul(hbKategori, page + 1, maxPage, urunHashMap, yeniUrunHashMap, ilkMi, yasakliUrunHashMap, kahramanHashMap);
                }
            }
            return yeniUrunHashMap;
        } catch (Exception e) {
            logService.hepsiBuradaLogYaz("Hb New Hata Kategori:" + hbKategori.getKategoriAdi() + " - Sayfa:");
            Random rand = new Random();
            int sleepRand = rand.nextInt(6);
            TimeUnit.SECONDS.sleep(sleepRand + 5);
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, HbKategori hbKategori, String urunId, Long indYuzde, Long fiyat, String hbNo, HashMap<String, List<HbKahraman>> kahramanHashMap) throws IOException, InterruptedException {
        List<HbTelegramConf> hbTelegramConfList = hbTelegramConfRepository.findByHbKategori(hbKategori);

        for (HbTelegramConf hbTelegramConf: hbTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, hbTelegramConf.getTelegramId(), urunId, "5744458225:AAE1y1VmQpNaBckpU89-Jz3vS1Y9GU_8fsU");
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

        List<HbKahraman> hbKahramanList = kahramanHashMap.get(hbNo);
        if (hbKahramanList != null && hbKahramanList.size() > 0) {
            for (HbKahraman hbKahraman: hbKahramanList) {
                genelService.telegramKahramanGonder(mesaj, hbKahraman.getTelegramChatId(), hbNo);
            }
        }
    }

    private boolean urunAdiKontrol(String urunAdi, HashMap<String, String> yasakliUrunHashMap) {
        String yasak = yasakliUrunHashMap.get(urunAdi);
        if (yasak != null && !yasak.equals("")) {
            return false;
        }

        return true;
    }
}
