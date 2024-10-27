package com.app.fku.yeniamazon.repository;

import com.app.fku.yeniamazon.entity.YeniAmazonKategori;
import com.app.fku.yeniamazon.entity.YeniAmazonTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YeniAmazonTelegramConfRepository extends JpaRepository<YeniAmazonTelegramConf, Long> {

    List<YeniAmazonTelegramConf> findByYeniAmazonKategori(YeniAmazonKategori yeniAmazonKategori);
}