package com.app.fku.karaca.repository;

import com.app.fku.karaca.entity.KrcKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KrcKategoriRepository extends JpaRepository<KrcKategori, Long> {
    KrcKategori findByKategoriAdi(String kategoriAdi);
    Optional<KrcKategori> findById(Long kategoriId);
    List<KrcKategori> findAllByOrderById();
}