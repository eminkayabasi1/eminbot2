package com.app.fku.trendyol.repository;

import com.app.fku.trendyol.entity.TrendyolUyelikEski;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrendyolUyelikEskiRepository extends JpaRepository<TrendyolUyelikEski, Long> {
    List<TrendyolUyelikEski> findAllByOrderBySonKontrolTarihi();
}