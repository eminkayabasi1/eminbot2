package com.app.fku.galerycristal.repository;

import com.app.fku.galerycristal.entity.GcConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GcConfRepository extends JpaRepository<GcConf, Long> {

    List<GcConf> findByHostname(String hostname);
}