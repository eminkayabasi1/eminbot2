package com.app.fku.teknosa.repository;

import com.app.fku.teknosa.entity.TknFiyat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TknFiyatRepository extends JpaRepository<TknFiyat, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM ( " +
                    "SELECT * " +
                    "FROM TKN_FIYAT f " +
                    "LEFT JOIN TKN_URUN u ON f.URUN_ID = u.ID AND f.ID IN (SELECT MAX(ff.ID) FROM TKN_FIYAT ff WHERE ff.KAYIT_TARIHI > (SYSDATE-1) GROUP BY ff.URUN_ID) " +
                    "LEFT JOIN TKN_KATEGORI k ON u.KATEGORI_ID=k.ID " +
                    "WHERE " +
                    "( u.KONTROL_EDILSIN_MI = 1 ) AND " +
                    "( :kategoriId = 0 OR k.ID= :kategoriId ) AND " +
                    "( :yeniKayitOlsunMu = 0 OR f.ESKI_BEKLENEN_FIYAT<>999999) AND " +
                    "( :guncelIndirimlerMi = 0 OR (f.FIYAT < u.BEKLENEN_FIYAT AND f.KAYIT_TARIHI > (SYSDATE-1/24))) " +
                    "ORDER BY f.ID DESC) " +
                    "WHERE rownum< :rowCount")
    List<TknFiyat> indirimleriGetir(Long rowCount, Long kategoriId, Integer yeniKayitOlsunMu, Integer guncelIndirimlerMi);

}