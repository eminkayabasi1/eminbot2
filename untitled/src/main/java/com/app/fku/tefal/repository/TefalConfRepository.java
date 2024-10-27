package com.app.fku.tefal.repository;

import com.app.fku.tefal.entity.TefalConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TefalConfRepository extends JpaRepository<TefalConf, Long> {

    List<TefalConf> findByHostname(String hostname);
}