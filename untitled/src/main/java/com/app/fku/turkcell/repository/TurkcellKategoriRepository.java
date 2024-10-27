package com.app.fku.turkcell.repository;

import com.app.fku.turkcell.entity.TurkcellKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TurkcellKategoriRepository extends JpaRepository<TurkcellKategori, Long> {
    TurkcellKategori findByKategoriAdi(String kategoriAdi);
    Optional<TurkcellKategori> findById(Long kategoriId);
    List<TurkcellKategori> findAllByOrderById();
}