package com.app.fku.yemeksepeti.repository;

import com.app.fku.yemeksepeti.entity.YmkConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YmkConfRepository extends JpaRepository<YmkConf, Long> {

    List<YmkConf> findByHostname(String hostname);
}