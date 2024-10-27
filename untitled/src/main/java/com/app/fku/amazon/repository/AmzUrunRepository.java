package com.app.fku.amazon.repository;

import com.app.fku.amazon.entity.AmzUrun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AmzUrunRepository extends JpaRepository<AmzUrun, Long> {

    AmzUrun findByAsinNo(String asinNo);

    @Query(value =
            "select u.asin_no from amz_urun_yeni u where u.kontrol_edilsin_mi = 1 "
            , nativeQuery = true)
    List<String> getAllAsinNo();

    @Query(value =
            "select u.asin_no from amz_urun_yeni u where u.kontrol_Edilsin_Mi = 1 and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<String> urunThreadSorgusuKategorisiz2(
            @Param("mod") int mod,
            @Param("kalan") int kalan);

    List<AmzUrun> findTop25ByOrderByIdDesc();








    @Query(value = "select u.beklenen_fiyat from amz_urun u where u.asin_no = :asinNo", nativeQuery = true)
    Double yenidenCekAsinNo(String asinNo);

    @Query(value =
            "select * from amz_urun u left join amz_kategori k on u.kategori_id = k.id where u.kontrol_Edilsin_Mi = 1 and u.kategori_id= :kategoriId and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<AmzUrun> urunThreadSorgusuKategorili(
            @Param("kategoriId") Long kategoriId,
            @Param("mod") int mod,
            @Param("kalan") int kalan);

    @Query(value =
            "select * from amz_urun u where u.kontrol_Edilsin_Mi = 1 and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<AmzUrun> urunThreadSorgusuKategorisiz(
            @Param("mod") int mod,
            @Param("kalan") int kalan);

}