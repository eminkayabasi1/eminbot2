package com.app.fku.hepsiburada.repository;

import com.app.fku.hepsiburada.entity.HbConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HbConfRepository extends JpaRepository<HbConf, Long> {

    List<HbConf> findByHostname(String hostname);
}