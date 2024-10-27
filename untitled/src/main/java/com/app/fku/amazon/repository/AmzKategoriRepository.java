package com.app.fku.amazon.repository;

import com.app.fku.amazon.entity.AmzKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmzKategoriRepository extends JpaRepository<AmzKategori, Long> {
    AmzKategori findByKategoriAdi(String kategoriAdi);
    Optional<AmzKategori> findById(Long kategoriId);
    List<AmzKategori> findAllByOrderById();
}