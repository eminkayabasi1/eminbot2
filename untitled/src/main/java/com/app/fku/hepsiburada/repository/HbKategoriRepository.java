package com.app.fku.hepsiburada.repository;

import com.app.fku.hepsiburada.entity.HbKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HbKategoriRepository extends JpaRepository<HbKategori, Long> {
    HbKategori findByKategoriAdi(String kategoriAdi);
    Optional<HbKategori> findById(Long kategoriId);
    List<HbKategori> findAllByOrderById();
    List<HbKategori> findAllByOrderByIdDesc();
}