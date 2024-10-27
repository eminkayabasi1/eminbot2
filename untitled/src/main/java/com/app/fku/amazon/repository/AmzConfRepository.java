package com.app.fku.amazon.repository;

import com.app.fku.amazon.entity.AmzConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmzConfRepository extends JpaRepository<AmzConf, Long> {

    List<AmzConf> findByHostname(String hostname);
}