package com.app.fku.karaca.repository;

import com.app.fku.karaca.entity.KrcConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KrcConfRepository extends JpaRepository<KrcConf, Long> {

    List<KrcConf> findByHostname(String hostname);
}