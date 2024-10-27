package com.app.fku.genel.repository;

import com.app.fku.genel.entity.FkuConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FkuConfRepository extends JpaRepository<FkuConf, String> {

    List<FkuConf> findByHostname(String hostname);
}