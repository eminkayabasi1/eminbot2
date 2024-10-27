package com.app.fku.yeniamazon.repository;

import com.app.fku.yeniamazon.entity.YeniAmazonKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface YeniAmazonKategoriRepository extends JpaRepository<YeniAmazonKategori, Long> {
    YeniAmazonKategori findByKategoriAdi(String kategoriAdi);
    Optional<YeniAmazonKategori> findById(Long kategoriId);
    List<YeniAmazonKategori> findAllByOrderById();
}