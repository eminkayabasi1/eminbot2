package com.app.fku.yeniamazon.repository;

import com.app.fku.yeniamazon.entity.YeniAmazonConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YeniAmazonConfRepository extends JpaRepository<YeniAmazonConf, Long> {

    List<YeniAmazonConf> findByHostname(String hostname);
}