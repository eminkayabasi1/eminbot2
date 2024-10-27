package com.app.fku.migrossanal.repository;

import com.app.fku.migrossanal.entity.MsConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsConfRepository extends JpaRepository<MsConf, Long> {

    List<MsConf> findByHostname(String hostname);
}