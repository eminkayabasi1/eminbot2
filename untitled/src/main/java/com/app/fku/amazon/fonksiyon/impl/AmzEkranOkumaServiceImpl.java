package com.app.fku.amazon.fonksiyon.impl;

import com.app.fku.amazon.entity.AmzFiyat;
import com.app.fku.amazon.entity.AmzKategori;
import com.app.fku.amazon.entity.AmzUrun;
import com.app.fku.amazon.entity.AmzWorkerIstatistik;
import com.app.fku.amazon.fonksiyon.service.AmzEkranOkumaService;
import com.app.fku.amazon.fonksiyon.service.AmzGenelService;
import com.app.fku.amazon.repository.AmzFiyatRepository;
import com.app.fku.amazon.repository.AmzUrunRepository;
import com.app.fku.amazon.repository.AmzWorkerIstatistikRepository;
import com.app.fku.genel.fonksiyon.service.GenelService;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class AmzEkranOkumaServiceImpl implements AmzEkranOkumaService {

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

    @Override
    public void kategoriIleUrunBul(AmzKategori amzKategori, int hataCount, int pageNumber, int maxPageNumber) throws InterruptedException {
        try {
            Document amzDoc = amzGenelService.urleGit(amzKategori.getSayfaAdresi() + "&page=" + pageNumber);
            if (amzDoc == null) {
                throw new Exception();
            }

            Elements urunElements = amzDoc.getElementsByClass("s-widget-spacing-small");

            for (Element urun: urunElements) {
                String asin = urun.attributes().get("data-asin");
                if (asin != null && !asin.equals("")) {
                    asinYoksaKaydet(asin, amzKategori);
                }
            }

            if (maxPageNumber == 0) {
                String maxSayfaStr = amzDoc.getElementsByClass("s-pagination-item").get(amzDoc.getElementsByClass("s-pagination-item").size() - 2).childNodes().get(0).toString();
                int maxSayfa = Integer.valueOf(maxSayfaStr);
                maxPageNumber = maxSayfa;
            }

            if (pageNumber < maxPageNumber) {
                kategoriIleUrunBul(amzKategori, 0, pageNumber + 1, maxPageNumber);
            }

        } catch (Exception e) {
            Thread.sleep(3000L);
            if (hataCount < 10) {
                kategoriIleUrunBul(amzKategori, hataCount + 1, pageNumber,maxPageNumber);
            }
        }
    }

    public void asinYoksaKaydet(String asinNo, AmzKategori amzKategori) throws InterruptedException {
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
        amzUrunRepository.flush();
    }

    @Override
    public void ekrandanFiyatOku(String asinNo) {
        try {
            AmzUrun amzUrun = amzUrunRepository.findByAsinNo(asinNo);
            if (amzUrun == null) {
                return;
            }

            Document amzDoc = amzGenelService.urleGit("https://www.amazon.com.tr/a/dp/" + asinNo);
            if (amzDoc == null) {
                throw new Exception();
            }


            //Captcha Controll START
            if (amzDoc.childNodes().size() > 5) {
                if (amzDoc.childNodes().get(5) != null) {
                    if (amzDoc.childNodes().get(5).toString().contains("Aşağıda gördüğünüz karakterleri girin")) {
                        //Captcha yemiş tekrar çağır
                        System.out.println("Captcha Yedim: " + asinNo);
                        ekrandanFiyatOku(asinNo);
                        return;
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
            Elements fiyatElements = amzDoc.getElementsByClass("a-section a-spacing-micro");
            if (fiyatElements != null && fiyatElements.size() > 0) {
                Element fiyatElement = fiyatElements.get(0);
                if (fiyatElement != null) {
                    List<Node> fiyatNodeList = fiyatElement.childNodes();
                    if (fiyatNodeList != null && fiyatNodeList.size() > 1) {
                        Node fiyatNode = fiyatNodeList.get(1);
                        List<Node> fiyatAltNodeList = fiyatNode.childNodes();
                        if (fiyatAltNodeList != null && fiyatAltNodeList.size() > 0) {
                            Node fiyatAltNode = fiyatAltNodeList.get(0);
                            List<Node> fiyatAlt2NodeList = fiyatAltNode.childNodes();
                            if (fiyatAlt2NodeList != null && fiyatAlt2NodeList.size() > 0) {
                                Node fiyatAlt2Node = fiyatAlt2NodeList.get(0);
                                String fiyatStr = fiyatAlt2Node.toString();
                                if (fiyatStr != null && !fiyatStr.equals("")) {
                                    fiyatStr = fiyatStr.substring(0,fiyatStr.length()-2);
                                    Double fiyat = new Double(fiyatStr.split(" ")[0].replace(".", "").replace(",", "."));
                                    if (fiyat != null) {
                                        if (amzUrun.getGuncelFiyat() != null) {
                                            Double eskiFiyat = amzUrun.getGuncelFiyat();
                                            Double indirimYuzdesi = amzUrun.getAmzKategori().getIndirimYuzdesi();
                                            Double beklenenFiyat = eskiFiyat * (100 - indirimYuzdesi) / 100;

                                            if (beklenenFiyat > fiyat) {
                                                //indirim var
                                                String tarihStr = "";
                                                String mesaj =  amzUrun.getModel() + " %0A " +
                                                        "EskiFiyat: " + eskiFiyat.toString() + " %0A " +
                                                        "YeniFiyat: " + fiyat + " %0A " +
                                                        "Tarih: " + tarihStr + " %0A " +
                                                        "Ürün Link: https://www.amazon.com.tr/a/dp/" + amzUrun.getAsinNo();
                                                //genelService.telegramMesajGonder(mesaj, "-635955522", amzUrun.getId().toString());
                                            }
                                        }

                                        amzUrun.setGuncelFiyat(fiyat);
                                        if (amzUrun.getMinFiyat() != null) {
                                            if (fiyat < amzUrun.getMinFiyat()) {
                                                amzUrun.setMinFiyat(fiyat);
                                            }
                                        } else {
                                            amzUrun.setMinFiyat(fiyat);
                                        }
                                    } else {
                                        amzUrun.setGuncelFiyat(new Double(333333));
                                    }
                                } else {
                                    amzUrun.setGuncelFiyat(new Double(444444));
                                }
                            } else {
                                amzUrun.setGuncelFiyat(new Double(555555));
                            }
                        } else {
                            amzUrun.setGuncelFiyat(new Double(666666));
                        }
                    } else {
                        amzUrun.setGuncelFiyat(new Double(777777));
                    }
                } else {
                    amzUrun.setGuncelFiyat(new Double(888888));
                }
            } else {
                amzUrun.setGuncelFiyat(new Double(999999));
            }
            //Fiyat Oku END
            amzUrun.setSonKontrolTarihi(new Date());
            amzUrunRepository.save(amzUrun);

            Element olcuElement = amzDoc.getElementById("variation_size_name");
            if (olcuElement != null) {
                Elements unavailableElements = olcuElement.getElementsByClass("swatchUnavailable");
                if (unavailableElements != null && unavailableElements.size() > 0) {
                    for (Element unavailable: unavailableElements) {
                        String dpUrl = unavailable.attributes().get("data-dp-url");
                        if (dpUrl != null && !dpUrl.equals("")) {
                            String[] strArr1 = dpUrl.split("dp/");
                            if (strArr1 != null && strArr1.length > 0) {
                                String[] strArr2 = strArr1[1].split("/ref");
                                if (strArr2 != null && strArr2.length > 0) {
                                    String asin = strArr2[0];
                                    asinYoksaKaydet(asin, amzUrun.getAmzKategori());
                                }
                            }
                        }
                    }
                }

                Elements availableElements = olcuElement.getElementsByClass("swatchAvailable");
                if (availableElements != null && availableElements.size() > 0) {
                    for (Element available: availableElements) {
                        String dpUrl = available.attributes().get("data-dp-url");
                        if (dpUrl != null && !dpUrl.equals("")) {
                            String[] strArr1 = dpUrl.split("dp/");
                            if (strArr1 != null && strArr1.length > 0) {
                                String[] strArr2 = strArr1[1].split("/ref");
                                if (strArr2 != null && strArr2.length > 0) {
                                    String asin = strArr2[0];
                                    asinYoksaKaydet(asin, amzUrun.getAmzKategori());
                                }
                            }
                        }
                    }
                }
            }

            Element renkElement = amzDoc.getElementById("variation_color_name");
            if (renkElement != null) {
                Elements unavailableElements = renkElement.getElementsByClass("swatchUnavailable");
                if (unavailableElements != null && unavailableElements.size() > 0) {
                    for (Element unavailable: unavailableElements) {
                        String dpUrl = unavailable.attributes().get("data-dp-url");
                        if (dpUrl != null && !dpUrl.equals("")) {
                            String[] strArr1 = dpUrl.split("dp/");
                            if (strArr1 != null && strArr1.length > 0) {
                                String[] strArr2 = strArr1[1].split("/ref");
                                if (strArr2 != null && strArr2.length > 0) {
                                    String asin = strArr2[0];
                                    asinYoksaKaydet(asin, amzUrun.getAmzKategori());
                                }
                            }
                        }
                    }
                }

                Elements availableElements = renkElement.getElementsByClass("swatchAvailable");
                if (availableElements != null && availableElements.size() > 0) {
                    for (Element available: availableElements) {
                        String dpUrl = available.attributes().get("data-dp-url");
                        if (dpUrl != null && !dpUrl.equals("")) {
                            String[] strArr1 = dpUrl.split("dp/");
                            if (strArr1 != null && strArr1.length > 0) {
                                String[] strArr2 = strArr1[1].split("/ref");
                                if (strArr2 != null && strArr2.length > 0) {
                                    String asin = strArr2[0];
                                    asinYoksaKaydet(asin, amzUrun.getAmzKategori());
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {

        }
    }
}