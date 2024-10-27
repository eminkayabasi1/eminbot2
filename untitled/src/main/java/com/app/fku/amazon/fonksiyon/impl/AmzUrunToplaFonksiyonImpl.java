package com.app.fku.amazon.fonksiyon.impl;

import com.app.fku.amazon.entity.AmzKategori;
import com.app.fku.amazon.entity.AmzTelegramConf;
import com.app.fku.amazon.fonksiyon.service.AmzGenelService;
import com.app.fku.amazon.fonksiyon.service.AmzUrunToplaFonksiyon;
import com.app.fku.amazon.repository.AmzTelegramConfRepository;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.model.GenelUrunModel;
import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.entity.HbTelegramConf;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbUrunToplaFonksiyon;
import com.app.fku.hepsiburada.repository.HbTelegramConfRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class AmzUrunToplaFonksiyonImpl implements AmzUrunToplaFonksiyon {

    @Autowired
    AmzGenelService amzGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    AmzTelegramConfRepository amzTelegramConfRepository;

    @Override
    public HashMap<String, GenelUrunModel> menudenUrunBul(AmzKategori amzKategori, Integer page, Integer maxPage, HashMap<String, GenelUrunModel> urunHashMap, boolean ilkMi, HashMap<String, String> yasakliUrunHashMap, HashMap<String, Date> mesajHashMap) throws IOException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        HashMap<String, GenelUrunModel> yeniUrunHashMap = new HashMap<>();
        try {
            Document amzDoc = null;

            for (int i = 1; i > 0; i++) {
                try {
                    if (amzKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                        amzDoc = amzGenelService.urleGit(amzKategori.getSayfaAdresi() + "&page=" + page);
                    } else if (amzKategori.getFiltreliMi().equals(EvetHayirEnum.HAYIR)) {
                        amzDoc = amzGenelService.urleGit(amzKategori.getSayfaAdresi() + "?page=" + page);
                    }

                    if (amzDoc == null) {
                        //System.out.println("amzNewDoc null (Captcha)");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    //System.out.println("amzNewDoc (Captcha)");
                }
                Thread.sleep(10000);
            }

            Elements urunElementList = amzDoc.getElementById("g-items").getElementsByClass("a-spacing-none g-item-sortable");
            for (Element urunElement: urunElementList) {
                Elements priceElements = urunElement.getElementsByClass("a-section price-section");
                if (priceElements != null && priceElements.size() > 0) {

                } else {
                    continue;
                }

                String urunAsin = urunElement.attr("data-reposition-action-params").split("ASIN:")[1].split("\\|")[0];
                if (!urunAdiKontrol(urunAsin, yasakliUrunHashMap)) {
                    continue;
                }

                String urunAdi = urunElement.getElementsByClass("a-row a-size-small").get(0).getElementsByClass("a-link-normal").get(0).attributes().get("title");
                urunAdi = urunAdi.replace("%", "");

                String fiyatStr = urunElement.attributes().get("data-price").split("\\.")[0];
                Long yeniFiyat = new Long(fiyatStr.trim());
                //Long eskiFiyat = urunHashMap.get(urunAsin);
                GenelUrunModel urunModel = urunHashMap.get(urunAsin);

                if (urunModel == null) {
                    //yeni gelmiş ürün
                    urunModel = new GenelUrunModel();
                    urunModel.setUrunId(urunAsin);
                    urunModel.setEskiFiyat(yeniFiyat);
                    urunModel.setSonMesajTarihi(new Date());
                    urunHashMap.put(urunAsin, urunModel);
                    if (!ilkMi) {
                        //mesaj at
                        String akakceLink = urunAdi.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + urunAdi + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + "https://www.amazon.com.tr/dp/" + urunAsin + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, amzKategori, urunAdi, 0L);
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
                            String mesaj = "" +
                                    "Ürün Adı: " + urunAdi + "%0A" +
                                    "Eski Fiyat: " + nf.format(urunModel.getEskiFiyat()) + "%0A" +
                                    "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                    "İndirim Yüzde: " + nf.format(indYuzde) + "%0A" +
                                    "İndirim: " + nf.format(indirim) + "%0A" +
                                    "Ürün Link:" + "https://www.amazon.com.tr/dp/" + urunAsin + "%0A" +
                                    "Tarih: " + sdf.format(new Date()) + "%0A" +
                                    "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                    "***Generated By Emin KAYABASI***";

                            telegramMesajGonder(mesaj, amzKategori, urunAdi, indYuzde);
                            urunModel.setSonMesajTarihi(new Date());
                        }
                    }
                    urunModel.setEskiFiyat(yeniFiyat);
                    urunHashMap.remove(urunAsin);
                    urunHashMap.put(urunAsin, urunModel);
                }
            }

            return urunHashMap;
        } catch (Exception e) {
            //System.out.println("AMZ New Hata Kategori:" + amzKategori.getKategoriAdi() + " - Sayfa:");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println(sw.toString());
            Random rand = new Random();
            int sleepRand = rand.nextInt(6);
            TimeUnit.SECONDS.sleep(sleepRand + 5);
            return urunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, AmzKategori amzKategori, String urunId, Long indYuzde) throws IOException, InterruptedException {
        List<AmzTelegramConf> amzTelegramConfList = amzTelegramConfRepository.findByAmzKategori(amzKategori);

        for (AmzTelegramConf amzTelegramConf: amzTelegramConfList) {
            genelService.telegramMesajGonder(mesaj, amzTelegramConf.getTelegramId(), urunId, "5408509672:AAGGqO8w_M57aUeXvvxMycnDrJZ7fvonzsM");
        }

        if (indYuzde > 40) {
            genelService.telegramBombaGonder(mesaj, urunId);
        }

        if (urunId.toUpperCase().contains("DYSON")) {
            genelService.telegramDysonGonder(mesaj, urunId);
        }

        /**
        if (urunId.toUpperCase().contains("AIRFRYER") ||
                urunId.toUpperCase().contains("AİRFRYER") ||
                urunId.toUpperCase().contains("AIR FRYER") ||
                urunId.toUpperCase().contains("AİR FRYER") ||
                urunId.toUpperCase().contains("FRITOZ") ||
                urunId.toUpperCase().contains("FRİTOZ") ||
                urunId.toUpperCase().contains("FRİTÖZ") ||
                urunId.toUpperCase().contains("FRITÖZ")) {
            genelService.telegramFritozGonder(mesaj, urunId);
        }*/

        if (urunId.toUpperCase().contains("FISSLER") ||
                urunId.toUpperCase().contains("FİSSLER")) {
            genelService.telegramFisslerGonder(mesaj, urunId);
        }
    }

    private boolean urunAdiKontrol(String urunAsin, HashMap<String, String> yasakliUrunHashMap) {
        String yasak = yasakliUrunHashMap.get(urunAsin);
        if (yasak != null && !yasak.equals("")) {
            return false;
        }

        return true;
    }

    @Override
    public void fakeCagri() throws Exception {
        amzGenelService.urleGit("https://www.amazon.com.tr");
    }

    @Override
    public HashMap<String, GenelUrunModel> listedenUrunBul(AmzKategori amzKategori, Integer page, Integer maxPage, HashMap<String, GenelUrunModel> urunHashMap, boolean ilkMi, HashMap<String, String> yasakliUrunHashMap, HashMap<String, Date> mesajHashMap) throws IOException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");

        HashMap<String, GenelUrunModel> yeniUrunHashMap = new HashMap<>();
        try {
            Document amzDoc = null;

            for (int i = 1; i > 0; i++) {
                try {
                    if (amzKategori.getFiltreliMi().equals(EvetHayirEnum.EVET)) {
                        amzDoc = amzGenelService.urleGit(amzKategori.getSayfaAdresi() + "&page=" + page);
                    } else if (amzKategori.getFiltreliMi().equals(EvetHayirEnum.HAYIR)) {
                        amzDoc = amzGenelService.urleGit(amzKategori.getSayfaAdresi() + "?page=" + page);
                    }

                    if (amzDoc == null) {
                        System.out.println("amzNewListeDoc null (Captcha)");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("amzNewListeDoc (Captcha)");
                }
                Thread.sleep(10000);
            }

            Elements urunElementList = amzDoc.getElementById("g-items").getElementsByClass("a-spacing-none g-item-sortable");
            for (Element urunElement: urunElementList) {
                Elements priceElements = urunElement.getElementsByClass("a-section price-section");
                if (priceElements != null && priceElements.size() > 0) {

                } else {
                    continue;
                }

                String urunAsin = urunElement.attr("data-reposition-action-params").split("ASIN:")[1].split("\\|")[0];
                if (!urunAdiKontrol(urunAsin, yasakliUrunHashMap)) {
                    continue;
                }

                String urunAdi = urunElement.getElementsByClass("a-row a-size-small").get(0).getElementsByClass("a-link-normal").get(0).attributes().get("title");
                urunAdi = urunAdi.replace("%", "");

                String fiyatStr = urunElement.attributes().get("data-price").split("\\.")[0];
                Long yeniFiyat = new Long(fiyatStr.trim());
                //Long eskiFiyat = urunHashMap.get(urunAsin);
                GenelUrunModel urunModel = urunHashMap.get(urunAsin);

                if (urunModel == null) {
                    //yeni gelmiş ürün
                    urunModel = new GenelUrunModel();
                    urunModel.setUrunId(urunAsin);
                    urunModel.setEskiFiyat(yeniFiyat);
                    urunModel.setSonMesajTarihi(new Date());
                    urunHashMap.put(urunAsin, urunModel);
                    if (!ilkMi) {
                        //mesaj at
                        String akakceLink = urunAdi.replace(" ", "%2B");
                        akakceLink = akakceLink.replace("+", "");

                        String mesaj = "" +
                                "YENİ ÜRÜN%0A" +
                                "Ürün Adı: " + urunAdi + "%0A" +
                                "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                "Ürün Link:" + "https://www.amazon.com.tr/dp/" + urunAsin + "%0A" +
                                "Tarih: " + sdf.format(new Date()) + "%0A" +
                                "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                "***Generated By Emin KAYABASI***";

                        telegramMesajGonder(mesaj, amzKategori, urunAdi, 0L);
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
                            String mesaj = "" +
                                    "Ürün Adı: " + urunAdi + "%0A" +
                                    "Eski Fiyat: " + nf.format(urunModel.getEskiFiyat()) + "%0A" +
                                    "Yeni Fiyat: " + nf.format(yeniFiyat) + "%0A" +
                                    "İndirim Yüzde: " + nf.format(indYuzde) + "%0A" +
                                    "İndirim: " + nf.format(indirim) + "%0A" +
                                    "Ürün Link:" + "https://www.amazon.com.tr/dp/" + urunAsin + "%0A" +
                                    "Tarih: " + sdf.format(new Date()) + "%0A" +
                                    "Akakçe Link:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                    "***Generated By Emin KAYABASI***";

                            telegramMesajGonder(mesaj, amzKategori, urunAdi, indYuzde);
                            urunModel.setSonMesajTarihi(new Date());
                        }
                    }
                    urunModel.setEskiFiyat(yeniFiyat);
                    urunHashMap.remove(urunAsin);
                    urunHashMap.put(urunAsin, urunModel);
                }
            }

            return urunHashMap;
        } catch (Exception e) {
            System.out.println("AMZ New Hata Kategori:" + amzKategori.getKategoriAdi() + " - Sayfa:");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println(sw.toString());
            Random rand = new Random();
            int sleepRand = rand.nextInt(6);
            TimeUnit.SECONDS.sleep(sleepRand + 5);
            return urunHashMap;
        }
    }
}
