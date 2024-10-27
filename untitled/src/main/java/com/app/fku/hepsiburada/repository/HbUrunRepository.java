package com.app.fku.hepsiburada.repository;

import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.entity.HbUrun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface HbUrunRepository extends JpaRepository<HbUrun, Long> {

    HbUrun findByHbNo(String hbNo);

    List<HbUrun> findByModel(String model);

    HbUrun findByModelAndRenk(String model, String renk);

    @Query(value =
            "SELECT * FROM HB_URUN hu WHERE hu.KONTROL_EDILSIN_MI=1 AND hu.HB_NO IS NOT NULL AND hu.KATEGORI_ID = :kategoriId ORDER BY hu.ID DESC"
            ,nativeQuery = true)
    List<HbUrun> getUrunListByKategori(Long kategoriId);

    @Query(value =
            "SELECT * FROM HB_URUN hu WHERE rownum <= 50 ORDER BY hu.KAYIT_TARIHI DESC"
            , nativeQuery = true)
    List<HbUrun> getSon50UrunList();

    @Query(value =
            "select * from hb_urun u left join hb_kategori k on u.kategori_id = k.id where u.hb_no is not null and u.kontrol_Edilsin_Mi = 1 and u.kategori_id= :kategoriId and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<HbUrun> urunThreadSorgusuKategorili(
            @Param("kategoriId") Long kategoriId,
            @Param("mod") int mod,
            @Param("kalan") int kalan);

    @Query(value =
            "select u.hb_no from hb_urun u left join hb_kategori k on u.kategori_id = k.id where u.hb_no is not null and u.kontrol_Edilsin_Mi = 1 and u.kategori_id= :kategoriId and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<String> urunThreadSorgusuKategorili2(
            @Param("kategoriId") Long kategoriId,
            @Param("mod") int mod,
            @Param("kalan") int kalan);

    @Query(value =
            "select * from hb_urun u where u.hb_no is not null and u.kontrol_Edilsin_Mi = 1 and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<HbUrun> urunThreadSorgusuKategorisiz(
            @Param("mod") int mod,
            @Param("kalan") int kalan);

    @Query(value =
            "select u.hb_no from hb_urun u where u.hb_no is not null and u.kontrol_Edilsin_Mi = 1 and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<String> urunThreadSorgusuKategorisiz2(
            @Param("mod") int mod,
            @Param("kalan") int kalan);

}