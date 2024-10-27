package com.app.fku.vatan.repository;

import com.app.fku.vatan.entity.VatanConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VatanConfRepository extends JpaRepository<VatanConf, Long> {

    List<VatanConf> findByHostname(String hostname);
}