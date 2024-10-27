package com.app.fku.trendyol.repository;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.trendyol.entity.TrendyolUyelik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrendyolUyelikRepository extends JpaRepository<TrendyolUyelik, Long> {
    List<TrendyolUyelik> findAllByOrderBySonKontrolTarihi();

    List<TrendyolUyelik> findBySepeteEklendiMi(EvetHayirEnum sepeteEklendiMi);
}