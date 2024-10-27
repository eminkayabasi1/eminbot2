package com.app.fku.genel.fonksiyon.impl;

import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.genel.sabitler.Sabitler;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Override
    public void yeniAmzLogYaz(String mesaj) {
        if (Sabitler.YENI_AMZ_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void trendyolLogYaz(String mesaj) {
        if (Sabitler.TRENDYOL_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void hepsiBuradaLogYaz(String mesaj) {
        if (Sabitler.HEPSI_BURADA_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void a101LogYaz(String mesaj) {
        if (Sabitler.A101_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void teknosaLogYaz(String mesaj) {
        if (Sabitler.TEKNOSA_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void mmLogYaz(String mesaj) {
        if (Sabitler.MM_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void itopyaLogYaz(String mesaj) {
        if (Sabitler.ITOPYA_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void tefalLogYaz(String mesaj) {
        if (Sabitler.TEFAL_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void vatanLogYaz(String mesaj) {
        if (Sabitler.VATAN_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void dysonLogYaz(String mesaj) {
        if (Sabitler.DYSON_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void turkcellLogYaz(String mesaj) {
        if (Sabitler.TURKCELL_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void gcLogYaz(String mesaj) {
        if (Sabitler.GC_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void mdLogYaz(String mesaj) {
        if (Sabitler.MD_LOG) {
            System.out.println(mesaj);
        }
    }

    @Override
    public void krcLogYaz(String mesaj) {
        if (Sabitler.KRC_LOG) {
            System.out.println(mesaj);
        }
    }
}
