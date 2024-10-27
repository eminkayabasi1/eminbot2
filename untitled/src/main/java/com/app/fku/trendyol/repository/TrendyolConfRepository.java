package com.app.fku.trendyol.repository;

import com.app.fku.trendyol.entity.TrendyolConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrendyolConfRepository extends JpaRepository<TrendyolConf, Long> {

    List<TrendyolConf> findByHostname(String hostname);
}