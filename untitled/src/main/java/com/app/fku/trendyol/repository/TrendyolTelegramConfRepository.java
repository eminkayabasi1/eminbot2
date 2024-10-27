package com.app.fku.trendyol.repository;

import com.app.fku.trendyol.entity.TrendyolKategori;
import com.app.fku.trendyol.entity.TrendyolTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrendyolTelegramConfRepository extends JpaRepository<TrendyolTelegramConf, Long> {

    List<TrendyolTelegramConf> findByTrendyolKategori(TrendyolKategori trendyolKategori);
}