package com.app.fku.migrossanal.repository;

import com.app.fku.migrossanal.entity.MsKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MsKategoriRepository extends JpaRepository<MsKategori, Long> {
    MsKategori findByKategoriAdi(String kategoriAdi);
    Optional<MsKategori> findById(Long kategoriId);
    List<MsKategori> findAllByOrderById();
}