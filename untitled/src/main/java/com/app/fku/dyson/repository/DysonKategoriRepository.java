package com.app.fku.dyson.repository;

import com.app.fku.dyson.entity.DysonKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DysonKategoriRepository extends JpaRepository<DysonKategori, Long> {
    DysonKategori findByKategoriAdi(String kategoriAdi);
    Optional<DysonKategori> findById(Long kategoriId);
    List<DysonKategori> findAllByOrderById();
}