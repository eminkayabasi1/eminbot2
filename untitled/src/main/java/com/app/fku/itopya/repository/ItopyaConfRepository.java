package com.app.fku.itopya.repository;

import com.app.fku.itopya.entity.ItopyaConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItopyaConfRepository extends JpaRepository<ItopyaConf, Long> {

    List<ItopyaConf> findByHostname(String hostname);
}