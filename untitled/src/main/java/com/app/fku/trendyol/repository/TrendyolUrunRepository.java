package com.app.fku.trendyol.repository;

import com.app.fku.trendyol.entity.TrendyolKategori;
import com.app.fku.trendyol.entity.TrendyolUrun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrendyolUrunRepository extends JpaRepository<TrendyolUrun, Long> {

    //TrendyolUrun findByTyNo(String tyNo);
    TrendyolUrun findByTyNoAndTyKategori(String tyNo, TrendyolKategori tyKategori);
    List<TrendyolUrun> findByTyKategori(TrendyolKategori trendyolKategori);
}