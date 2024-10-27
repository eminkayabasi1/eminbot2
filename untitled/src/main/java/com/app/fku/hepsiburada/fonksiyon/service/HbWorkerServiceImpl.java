package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.entity.HbTelegramConf;
import com.app.fku.hepsiburada.entity.HbUrun;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbWorkerService;
import com.app.fku.hepsiburada.repository.HbTelegramConfRepository;
import com.app.fku.hepsiburada.repository.HbUrunRepository;
import com.app.fku.trendyol.entity.TrendyolKategori;
import com.app.fku.trendyol.entity.TrendyolTelegramConf;
import com.app.fku.trendyol.entity.TrendyolUrun;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HbWorkerServiceImpl implements HbWorkerService {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    HbUrunRepository hbUrunRepository;

    @Autowired
    HbTelegramConfRepository hbTelegramConfRepository;

    @Override
    public boolean sayfadanFiyatBul(String hbNo) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");
        try {
            Document hepsiBuradaDoc = null;
            try {
                hepsiBuradaDoc = hbGenelService.urleGit("https://www.hepsiburada.com/eminkayabasi-p-" + hbNo);
                if (hepsiBuradaDoc == null) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Thread.sleep(3000L);
                System.out.println("Hb Hata Aldım 2222222 --- " + hbNo);
                return false;
            }

            String modelAdi = hepsiBuradaDoc.getElementsByClass("product-name best-price-trick").get(0).childNodes().get(0).toString().trim();
            HbUrun masterHbUrun = hbUrunRepository.findByHbNo(hbNo);

            String satici = hepsiBuradaDoc.getElementsByClass("seller").get(0).childNodes().get(3).childNodes().get(1).childNodes().get(0).toString();

            //ÜRÜNÜN FİYATI START
            Elements renkElements = hepsiBuradaDoc.getElementsByClass("variants-content ");
            if (renkElements != null && renkElements.size() > 0) {
                for (Element renkElement: renkElements) {
                    HbUrun kontrolHbUrun = new HbUrun();
                    String renk = renkElement.getElementsByClass("variant-name").get(0).childNodes().get(0).toString().trim();
                    if (renk.equals(masterHbUrun.getRenk())) {
                        kontrolHbUrun = masterHbUrun;
                    } else {
                        kontrolHbUrun = hbUrunRepository.findByModelAndRenk(modelAdi, renk);
                        if (kontrolHbUrun == null) {
                            kontrolHbUrun = new HbUrun();
                            kontrolHbUrun.setModel(modelAdi);
                            kontrolHbUrun.setRenk(renk);
                            kontrolHbUrun.setKayitTarihi(new Date());
                            kontrolHbUrun.setHbKategori(masterHbUrun.getHbKategori());
                            kontrolHbUrun.setKontrolEdilsinMi(true);
                        }
                    }

                    String fiyatStr = renkElement.getElementsByClass("variant-property-price").get(0).childNodes().get(0).toString();
                    if (fiyatStr.equals(" ") || fiyatStr.equals(" Stokta Yok ")) {
                        kontrolHbUrun.setGuncelFiyat(new Double(888888));
                        kontrolHbUrun.setSonFiyatKontrolTarihi(new Date());
                        hbUrunRepository.save(kontrolHbUrun);
                        continue;
                    } else {
                        Double guncelFiyat = new Double(fiyatStr.trim().split(" ")[0].replace(".", "").replace(",", "."));
                        if (kontrolHbUrun.getGuncelFiyat() == null) {
                            kontrolHbUrun.setGuncelFiyat(guncelFiyat);
                        }
                        if (kontrolHbUrun.getMinFiyat() == null) {
                            kontrolHbUrun.setMinFiyat(guncelFiyat);
                            kontrolHbUrun.setMinFiyatTarihi(new Date());
                        }

                        if (!kontrolHbUrun.getGuncelFiyat().equals(new Double(888888)) && kontrol(kontrolHbUrun, guncelFiyat)) {
                            Double eskiFiyat = kontrolHbUrun.getGuncelFiyat();
                            String tarihStr = sdf.format(new Date());
                            String minFiyatTarihStr = "";//null olabilir
                            if (kontrolHbUrun.getMinFiyatTarihi() != null) {
                                minFiyatTarihStr = sdf.format(kontrolHbUrun.getMinFiyatTarihi());
                            }
                            Double indirim = eskiFiyat - guncelFiyat;
                            Double indirimYuzde = indirim / eskiFiyat * 100;
                            String akakceLink = kontrolHbUrun.getModel().replace(" ", "%2B");
                            akakceLink = akakceLink.replace("+", "");
                            String mesaj =  "** " + //kontrolHbUrun.getHbKategori().getKategoriKisaAd() + " ** " + " %0A " +
                                    kontrolHbUrun.getModel() + " %0A " +
                                    "*Satıcı\t: " + satici + " %0A" +
                                    "*EskiFiyat\t: " + nf.format(eskiFiyat) + " %0A " +
                                    "*YeniFiyat\t: " + nf.format(guncelFiyat) + " %0A " +
                                    "*İndirim\t: " + nf.format(indirim) + " %0A " +
                                    "*İnd.Yüzde\t: " + nf.format(indirimYuzde) + " %0A " +
                                    "*Min Fiyat\t: " + nf.format(kontrolHbUrun.getMinFiyat()) + " %0A " +
                                    "*Min Fyt Trh: " + minFiyatTarihStr + " %0A " +
                                    "*Tarih\t\t: " + tarihStr + " %0A " +
                                    "*Ürün Link\t:https://www.hepsiburada.com/eminkayabasi-p-" + hbNo + " %0A " +
                                    "*Hostname\t: " + genelService.getHostName() + " %0A " +
                                    "*Akakçe Link\t:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                    "****Generated By Emin Kayabaşı****";

                            kontrolHbUrun.setSonFiyatKontrolTarihi(new Date());
                            kontrolHbUrun.setGuncelFiyat(guncelFiyat);
                            if (guncelFiyat < kontrolHbUrun.getMinFiyat()) {
                                kontrolHbUrun.setMinFiyat(guncelFiyat);
                                kontrolHbUrun.setMinFiyatTarihi(new Date());
                            }
                            kontrolHbUrun = hbUrunRepository.save(kontrolHbUrun);
                            telegramMesajGonder(mesaj, kontrolHbUrun.getHbKategori(), kontrolHbUrun);
                        } else {
                            kontrolHbUrun.setSonFiyatKontrolTarihi(new Date());
                            kontrolHbUrun.setGuncelFiyat(guncelFiyat);
                            if (guncelFiyat < kontrolHbUrun.getMinFiyat()) {
                                kontrolHbUrun.setMinFiyat(guncelFiyat);
                                kontrolHbUrun.setMinFiyatTarihi(new Date());
                            }
                            kontrolHbUrun = hbUrunRepository.save(kontrolHbUrun);
                        }
                    }
                }
            } else {
                Elements elements = hepsiBuradaDoc.getElementsByAttributeValue("data-bind", "markupText:'currentPriceBeforePoint'");
                Double guncelFiyat = new Double(elements.get(0).childNodes().get(0).toString().replace(".", "").replace(",", "."));
                if (guncelFiyat.equals(new Double(0))) {
                    masterHbUrun.setGuncelFiyat(new Double(888888));
                    masterHbUrun.setSonFiyatKontrolTarihi(new Date());
                    hbUrunRepository.save(masterHbUrun);
                    return true;
                }
                if (masterHbUrun.getGuncelFiyat() == null) {
                    masterHbUrun.setGuncelFiyat(guncelFiyat);
                }
                if (masterHbUrun.getMinFiyat() == null) {
                    masterHbUrun.setMinFiyat(guncelFiyat);
                    masterHbUrun.setMinFiyatTarihi(new Date());
                }
                if (!masterHbUrun.getGuncelFiyat().equals(new Double(888888)) && kontrol(masterHbUrun, guncelFiyat)) {
                    Double eskiFiyat = masterHbUrun.getGuncelFiyat();
                    String tarihStr = sdf.format(new Date());
                    String minFiyatTarihStr = "";//null olabilir
                    if (masterHbUrun.getMinFiyatTarihi() != null) {
                        minFiyatTarihStr = sdf.format(masterHbUrun.getMinFiyatTarihi());
                    }
                    Double indirim = eskiFiyat - guncelFiyat;
                    Double indirimYuzde = indirim / eskiFiyat * 100;
                    String akakceLink = masterHbUrun.getModel().replace(" ", "%2B");
                    akakceLink = akakceLink.replace("+", "");
                    String mesaj =  "** " + //masterHbUrun.getHbKategori().getKategoriKisaAd() + " ** " + " %0A " +
                            masterHbUrun.getModel() + " %0A " +
                            "*Satıcı\t: " + satici + " %0A" +
                            "*EskiFiyat\t: " + nf.format(eskiFiyat) + " %0A " +
                            "*YeniFiyat\t: " + nf.format(guncelFiyat) + " %0A " +
                            "*İndirim\t: " + nf.format(indirim) + " %0A " +
                            "*İnd.Yüzde\t: " + nf.format(indirimYuzde) + " %0A " +
                            "*Min Fiyat\t: " + nf.format(masterHbUrun.getMinFiyat()) + " %0A " +
                            "*Min Fyt Trh: " + minFiyatTarihStr + " %0A " +
                            "*Tarih\t\t: " + tarihStr + " %0A " +
                            "*Ürün Link\t:https://www.hepsiburada.com/eminkayabasi-p-" + masterHbUrun.getHbNo() + " %0A " +
                            "*Hostname\t: " + genelService.getHostName() + " %0A " +
                            "*Akakçe Link\t:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                            "****Generated By Emin Kayabaşı****";

                    masterHbUrun.setSonFiyatKontrolTarihi(new Date());
                    masterHbUrun.setGuncelFiyat(guncelFiyat);
                    if (guncelFiyat < masterHbUrun.getMinFiyat()) {
                        masterHbUrun.setMinFiyat(guncelFiyat);
                        masterHbUrun.setMinFiyatTarihi(new Date());
                    }
                    masterHbUrun = hbUrunRepository.save(masterHbUrun);
                    telegramMesajGonder(mesaj, masterHbUrun.getHbKategori(), masterHbUrun);
                }
            }
            masterHbUrun.setSonFiyatKontrolTarihi(new Date());
            hbUrunRepository.save(masterHbUrun);
            //ÜRÜNÜN FİYATI END
        } catch (Exception e) {
            Thread.sleep(3000L);
            System.out.println("Hb Hata Aldım 1111111 --- " + hbNo);
            return false;
        }
        return true;
    }

    private boolean kontrol(HbUrun hbUrun, Double fiyat) {
        /**
        Double eskiFiyat = hbUrun.getGuncelFiyat();
        Double indirimYuzdesi = hbUrun.getHbKategori().getIndirimYuzdesi();
        Double beklenenFiyat = eskiFiyat * (100 - indirimYuzdesi) / 100;

        if (beklenenFiyat > fiyat) {
            //indirim var
            //chatId -701237811
            return true;
        } else {
            hbUrun.setGuncelFiyat(fiyat);
            if (fiyat < hbUrun.getMinFiyat()) {
                hbUrun.setMinFiyat(fiyat);
                hbUrun.setMinFiyatTarihi(new Date());
            }
            hbUrun = hbUrunRepository.save(hbUrun);
        }*/
        return false;
    }

    private void telegramMesajGonder(String mesaj, HbKategori hbKategori, HbUrun hbUrun) throws IOException, InterruptedException {
        List<HbTelegramConf> hbTelegramConfList = hbTelegramConfRepository.findByHbKategori(hbKategori);

        for (HbTelegramConf hbTelegramConf: hbTelegramConfList) {
            //genelService.telegramMesajGonder(mesaj, hbTelegramConf.getTelegramId(), hbUrun.getId().toString());
        }
    }
}
