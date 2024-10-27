package com.app.fku.dyson.repository;

import com.app.fku.dyson.entity.DysonConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DysonConfRepository extends JpaRepository<DysonConf, Long> {

    List<DysonConf> findByHostname(String hostname);
}