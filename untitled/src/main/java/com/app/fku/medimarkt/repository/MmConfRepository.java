package com.app.fku.medimarkt.repository;

import com.app.fku.medimarkt.entity.MmConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MmConfRepository extends JpaRepository<MmConf, Long> {

    List<MmConf> findByHostname(String hostname);
}