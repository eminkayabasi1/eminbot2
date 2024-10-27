package com.app.fku.trendyol.repository;

import com.app.fku.trendyol.entity.TrendyolKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrendyolKategoriRepository extends JpaRepository<TrendyolKategori, Long> {

    List<TrendyolKategori> findAllByOrderById();
    List<TrendyolKategori> findAllByOrderByIdDesc();
}