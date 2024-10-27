package com.app.fku.amazon.fonksiyon.impl;

import com.app.fku.amazon.entity.AmzKategori;
import com.app.fku.amazon.entity.AmzTelegramConf;
import com.app.fku.amazon.entity.AmzUrun;
import com.app.fku.amazon.fonksiyon.service.AmzGenelService;
import com.app.fku.amazon.fonksiyon.service.AmzWorkerService;
import com.app.fku.amazon.repository.AmzFiyatRepository;
import com.app.fku.amazon.repository.AmzTelegramConfRepository;
import com.app.fku.amazon.repository.AmzUrunRepository;
import com.app.fku.amazon.repository.AmzWorkerIstatistikRepository;
import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.entity.HbTelegramConf;
import com.app.fku.hepsiburada.entity.HbUrun;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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
public class AmzWorkerServiceImpl implements AmzWorkerService {

    @Autowired
    GenelService genelService;

    @Autowired
    AmzUrunRepository amzUrunRepository;

    @Autowired
    AmzWorkerIstatistikRepository amzWorkerIstatistikRepository;

    @Autowired
    AmzFiyatRepository amzFiyatRepository;

    @Autowired
    AmzGenelService amzGenelService;

    @Autowired
    AmzTelegramConfRepository amzTelegramConfRepository;

    @Override
    public void ekrandanFiyatOku(String asinNo, int hataCount) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");
        try {
            AmzUrun amzUrun = amzUrunRepository.findByAsinNo(asinNo);
            if (amzUrun == null) {
                return;
            }

//            Document amzDoc = amzGenelService.urleGit("https://www.amazon.com.tr/dp/" + asinNo);
            Document amzDoc = amzGenelService.urleGit("https://www.amazon.com.tr/dp/" + asinNo + "/ref=olp-opf-redir?aod=1");

            if (amzDoc == null) {
                throw new Exception();
            }

            //Captcha Controll START
            if (amzDoc.childNodes().size() > 5) {
                if (amzDoc.childNodes().get(5) != null) {
                    if (amzDoc.childNodes().get(10).toString().contains("Üzgünüz, sadece robot olmadığınızdan emin olmalıyız.")) {
                        //Captcha yemiş tekrar çağır
                        System.out.println("Amz Captcha Yedim: " + asinNo);
                        AmzGenelServiceImpl.lastUrl = "";
                        Thread.sleep(10000L);
                        ekrandanFiyatOku(asinNo, hataCount);
                    }
                }
            }
            //Captcha Controll END

            //Model Adı START
            if (amzUrun.getModel() != null && !amzUrun.getModel().equals("")) {

            } else {
                Elements titleElements = amzDoc.getElementsByClass("product-title-word-break");
                if (titleElements != null && titleElements.size() > 0) {
                    if (titleElements.get(0).childNodes() != null && titleElements.get(0).childNodes().size() > 0) {
                        String model = titleElements.get(0).childNodes().get(0).toString();
                        if (model != null && !model.equals("")) {
                            amzUrun.setModel(model);
                        }
                    }
                }
            }
            //Model Adı END

            //Fiyat Oku START
            if (amzDoc.getElementsByClass("a-price aok-align-center").size() > 0) {
                String fiyatStr = amzDoc.getElementsByClass("a-price aok-align-center").get(0).getElementsByClass("a-price-whole").get(0).childNodes().get(0).toString();
                fiyatStr = fiyatStr.split("TL")[0];
                Double fiyat = new Double(fiyatStr.replace(".", "").replace(",", "."));
                if (fiyat != null) {
                    if (amzUrun.getGuncelFiyat() != null) {
                        Double eskiFiyat = amzUrun.getGuncelFiyat();
                        Double indirimYuzdesi = amzUrun.getAmzKategori().getIndirimYuzdesi();
                        Double beklenenFiyat = eskiFiyat * (100 - indirimYuzdesi) / 100;

                        if (beklenenFiyat > fiyat) {
                            //indirim var
                            Double indirim = eskiFiyat - fiyat;
                            Double indirimYuzde = indirim / eskiFiyat * 100;
                            String satici = "";
                            String tarihStr = sdf.format(new Date());
                            String minFiyatTarihStr = "";//null olabilir
                            if (amzUrun.getMinFiyatTarihi() != null) {
                                minFiyatTarihStr = sdf.format(amzUrun.getMinFiyatTarihi());
                            }
                            String akakceLink = amzUrun.getModel().replace(" ", "%2B");
                            akakceLink = akakceLink.replace("+", "");

                            String mesaj =  "** " + amzUrun.getAmzKategori().getKategoriKisaAd() + " ** " + " %0A " +
                                    amzUrun.getModel() + " %0A " +
                                    "*Satıcı\t: " + satici + " %0A " +
                                    "*EskiFiyat\t: " + nf.format(eskiFiyat) + " %0A " +
                                    "*YeniFiyat\t: " + nf.format(fiyat) + " %0A " +
                                    "*İndirim\t: " + nf.format(indirim) + " %0A " +
                                    "*İnd.Yüzde\t: " + nf.format(indirimYuzde) + " %0A " +
                                    "*MinFiyat\t: " + amzUrun.getMinFiyat() + " %0A " +
                                    "*Min Fyt Trh: " + minFiyatTarihStr + " %0A " +
                                    "*Tarih: " + tarihStr + " %0A " +
                                    "*Ürün Link:https://www.amazon.com.tr/a/dp/" + amzUrun.getAsinNo() + " %0A " +
                                    "*Hostname\t: " + genelService.getHostName() + " %0A " +
                                    "*Akakçe Link\t:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                                    "****Generated By Emin Kayabaşı****";
                            telegramMesajGonder(mesaj, amzUrun.getAmzKategori(), amzUrun);
                        }
                    }

                    amzUrun.setGuncelFiyat(fiyat);
                    if (amzUrun.getMinFiyat() != null) {
                        if (fiyat < amzUrun.getMinFiyat()) {
                            amzUrun.setMinFiyat(fiyat);
                            amzUrun.setMinFiyatTarihi(new Date());
                        }
                    } else {
                        amzUrun.setMinFiyat(fiyat);
                        amzUrun.setMinFiyatTarihi(new Date());
                    }
                }
            }
            //Fiyat Oku END
            amzUrun.setSonKontrolTarihi(new Date());
            amzUrunRepository.save(amzUrun);

            Element renkler = amzDoc.getElementById("tp-inline-twister-dim-values-container");
            if (renkler != null) {
                List<Element> renkList = renkler.getElementsByClass("inline-twister-swatch");
                for (Element renk: renkList) {
                    String yeniAsinNo = renk.attributes().get("data-asin");
                    asinYoksaKaydet(yeniAsinNo, amzUrun.getAmzKategori());
                }
            }

        } catch (Exception e) {
            System.out.println("Amz Hata: " + asinNo);
            e.printStackTrace();
            Thread.sleep(3000L);
        }
    }

    public void asinYoksaKaydet(String asinNo, AmzKategori amzKategori) {
        if (asinNo == null) {
            return;
        }

        if (asinNo.equals("")) {
            return;
        }

        AmzUrun amzUrun = amzUrunRepository.findByAsinNo(asinNo);
        if (amzUrun != null) {
            return;
        }

        amzUrun = new AmzUrun();
        amzUrun.setAmzKategori(amzKategori);
        amzUrun.setAsinNo(asinNo);
        amzUrun.setKayitTarihi(new Date());
        amzUrun.setKontrolEdilsinMi(true);
        amzUrunRepository.save(amzUrun);
    }

    private void telegramMesajGonder(String mesaj, AmzKategori amzKategori, AmzUrun amzUrun) throws IOException, InterruptedException {
        List<AmzTelegramConf> amzTelegramConfList = amzTelegramConfRepository.findByAmzKategori(amzKategori);

        for (AmzTelegramConf amzTelegramConf: amzTelegramConfList) {
            //genelService.telegramMesajGonder(mesaj, amzTelegramConf.getTelegramId(), amzUrun.getId().toString());
        }
    }

    @Override
    public void ekrandanFiyatOkuHtmlUnit(String asinNo, int hataCount) throws InterruptedException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        NumberFormat nf = new DecimalFormat("#0.00");
        AmzUrun amzUrun = amzUrunRepository.findByAsinNo(asinNo);
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX, true, null, -1);
        final HtmlPage page = webClient.getPage("https://www.amazon.com.tr/dp/" + asinNo + "/ref=olp-opf-redir?aod=1");
        webClient.waitForBackgroundJavaScript(30 * 1000);
        HtmlDivision htmlDivision = (HtmlDivision) page.getElementById("aod-price-1");
        String fiyatStr = htmlDivision.getChildElements().iterator().next().getChildElements().iterator().next().getFirstChild().toString();/* will wait JavaScript to execute up to 30s */
        Double fiyat = new Double(fiyatStr.split(" ")[0].replace(".","").replace(",","."));
        if (amzUrun.getGuncelFiyat() != null) {
            Double eskiFiyat = amzUrun.getGuncelFiyat();
            Double indirimYuzdesi = amzUrun.getAmzKategori().getIndirimYuzdesi();
            Double beklenenFiyat = eskiFiyat * (100 - indirimYuzdesi) / 100;

            if (beklenenFiyat > fiyat) {
                //indirim var
                Double indirim = eskiFiyat - fiyat;
                Double indirimYuzde = indirim / eskiFiyat * 100;
                String satici = "";
                String tarihStr = sdf.format(new Date());
                String minFiyatTarihStr = "";//null olabilir
                if (amzUrun.getMinFiyatTarihi() != null) {
                    minFiyatTarihStr = sdf.format(amzUrun.getMinFiyatTarihi());
                }
                String akakceLink = amzUrun.getModel().replace(" ", "%2B");
                akakceLink = akakceLink.replace("+", "");

                String mesaj =  "** " + amzUrun.getAmzKategori().getKategoriKisaAd() + " ** " + " %0A " +
                        amzUrun.getModel() + " %0A " +
                        "*Satıcı\t: " + satici + " %0A " +
                        "*EskiFiyat\t: " + nf.format(eskiFiyat) + " %0A " +
                        "*YeniFiyat\t: " + nf.format(fiyat) + " %0A " +
                        "*İndirim\t: " + nf.format(indirim) + " %0A " +
                        "*İnd.Yüzde\t: " + nf.format(indirimYuzde) + " %0A " +
                        "*MinFiyat\t: " + amzUrun.getMinFiyat() + " %0A " +
                        "*Min Fyt Trh: " + minFiyatTarihStr + " %0A " +
                        "*Tarih: " + tarihStr + " %0A " +
                        "*Ürün Link:https://www.amazon.com.tr/a/dp/" + amzUrun.getAsinNo() + " %0A " +
                        "*Hostname\t: " + genelService.getHostName() + " %0A " +
                        "*Akakçe Link\t:https://www.akakce.com/arama/?q=" + akakceLink + " %0A " +
                        "****Generated By Emin Kayabaşı****";
                telegramMesajGonder(mesaj, amzUrun.getAmzKategori(), amzUrun);
            }
        }

        amzUrun.setGuncelFiyat(fiyat);
        if (amzUrun.getMinFiyat() != null) {
            if (fiyat < amzUrun.getMinFiyat()) {
                amzUrun.setMinFiyat(fiyat);
                amzUrun.setMinFiyatTarihi(new Date());
            }
        } else {
            amzUrun.setMinFiyat(fiyat);
            amzUrun.setMinFiyatTarihi(new Date());
        }
        amzUrun.setSonKontrolTarihi(new Date());
        amzUrunRepository.save(amzUrun);
    }
}