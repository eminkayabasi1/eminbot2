package com.app.fku.turkcell.repository;

import com.app.fku.turkcell.entity.TurkcellConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurkcellConfRepository extends JpaRepository<TurkcellConf, Long> {

    List<TurkcellConf> findByHostname(String hostname);
}