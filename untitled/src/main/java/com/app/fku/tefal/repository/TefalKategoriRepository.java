package com.app.fku.tefal.repository;

import com.app.fku.tefal.entity.TefalKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TefalKategoriRepository extends JpaRepository<TefalKategori, Long> {
    TefalKategori findByKategoriAdi(String kategoriAdi);
    Optional<TefalKategori> findById(Long kategoriId);
    List<TefalKategori> findAllByOrderById();
}