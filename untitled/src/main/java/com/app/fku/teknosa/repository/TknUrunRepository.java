package com.app.fku.teknosa.repository;

import com.app.fku.teknosa.entity.TknUrun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TknUrunRepository extends JpaRepository<TknUrun, Long> {
    TknUrun findByTknNo(String tknNo);

    @Query(value =
            "select * from tkn_urun u left join tkn_kategori k on u.kategori_id = k.id where u.kontrol_Edilsin_Mi = 1 and u.kategori_id= :kategoriId and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<TknUrun> urunThreadSorgusuKategorili(
            @Param("kategoriId") Long kategoriId,
            @Param("mod") int mod,
            @Param("kalan") int kalan);

    @Query(value =
            "select * from tkn_urun u where u.kontrol_Edilsin_Mi = 1 and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<TknUrun> urunThreadSorgusuKategorisiz(
            @Param("mod") int mod,
            @Param("kalan") int kalan);

    List<TknUrun> findTop25ByOrderByIdDesc();

    @Query(value = "select u.beklenen_fiyat from tkn_urun u where u.tkn_no = :tknNo", nativeQuery = true)
    Double yenidenCekTknNo(String tknNo);

    @Query(value =
            "select u.tkn_no from tkn_urun u where u.kontrol_Edilsin_Mi = 1 and MOD(u.id,:mod) = :kalan ORDER BY DBMS_RANDOM.RANDOM"
            , nativeQuery = true)
    List<String> urunThreadSorgusuKategorisiz2(
            @Param("mod") int mod,
            @Param("kalan") int kalan);
}