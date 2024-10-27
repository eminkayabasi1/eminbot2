package com.app.fku.Utils;

public class EminKayabasi {

    public static void main(String[] args) throws InterruptedException {
        int sayac = 20;
        boolean yon = false;
        for (int i = 1; i > 0; i++) {
            String text = "";
            for (int j = 0; j < 20 - sayac; j++) {
                text = text + " ";
            }
            for (int j = 0; j < sayac; j++) {
                text = text + "*";
            }
            text = text + " Emin Cansu'yu Çok Seviyor ";
            for (int j = 0; j < sayac; j++) {
                text = text + "*";
            }
            System.out.println(text);
            if (!yon) {
                sayac = sayac - 1;
            } else {
                sayac = sayac + 1;
            }
            if (sayac == 1) {
                yon = true;
            }
            if (sayac == 20) {
                yon = false;
            }
            Thread.sleep(100);
        }
    }
}
