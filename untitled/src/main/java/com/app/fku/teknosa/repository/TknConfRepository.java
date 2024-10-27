package com.app.fku.teknosa.repository;

import com.app.fku.teknosa.entity.TknConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TknConfRepository extends JpaRepository<TknConf, Long> {

    List<TknConf> findByHostname(String hostname);
}