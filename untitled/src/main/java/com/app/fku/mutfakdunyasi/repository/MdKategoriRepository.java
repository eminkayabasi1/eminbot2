package com.app.fku.mutfakdunyasi.repository;

import com.app.fku.mutfakdunyasi.entity.MdKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MdKategoriRepository extends JpaRepository<MdKategori, Long> {
    MdKategori findByKategoriAdi(String kategoriAdi);
    Optional<MdKategori> findById(Long kategoriId);
    List<MdKategori> findAllByOrderById();
}