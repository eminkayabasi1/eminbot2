package com.app.fku.teknosa.repository;

import com.app.fku.teknosa.entity.TknKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TknKategoriRepository extends JpaRepository<TknKategori, Long> {
    TknKategori findByKategoriAdi(String kategoriAdi);
    Optional<TknKategori> findById(Long kategoriId);
    List<TknKategori> findAllByOrderById();
}