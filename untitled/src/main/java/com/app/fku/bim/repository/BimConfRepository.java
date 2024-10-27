package com.app.fku.bim.repository;

import com.app.fku.bim.entity.BimConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BimConfRepository extends JpaRepository<BimConf, Long> {

    List<BimConf> findByHostname(String hostname);
}