package com.app.fku.medimarkt.repository;

import com.app.fku.medimarkt.entity.MmKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MmKategoriRepository extends JpaRepository<MmKategori, Long> {
    MmKategori findByKategoriAdi(String kategoriAdi);
    Optional<MmKategori> findById(Long kategoriId);
    List<MmKategori> findAllByOrderById();
}