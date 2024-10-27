package com.app.fku.vatan.repository;

import com.app.fku.vatan.entity.VatanKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VatanKategoriRepository extends JpaRepository<VatanKategori, Long> {
    VatanKategori findByKategoriAdi(String kategoriAdi);
    Optional<VatanKategori> findById(Long kategoriId);
    List<VatanKategori> findAllByOrderById();
}