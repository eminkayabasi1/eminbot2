package com.app.fku.instagram.repository;

import com.app.fku.instagram.entity.InsConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsConfRepository extends JpaRepository<InsConf, Long> {

    List<InsConf> findByHostname(String hostname);
}