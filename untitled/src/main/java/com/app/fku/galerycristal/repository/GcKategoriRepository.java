package com.app.fku.galerycristal.repository;

import com.app.fku.galerycristal.entity.GcKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GcKategoriRepository extends JpaRepository<GcKategori, Long> {
    GcKategori findByKategoriAdi(String kategoriAdi);
    Optional<GcKategori> findById(Long kategoriId);
    List<GcKategori> findAllByOrderById();
}