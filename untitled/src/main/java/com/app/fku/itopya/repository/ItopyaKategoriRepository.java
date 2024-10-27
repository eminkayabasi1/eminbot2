package com.app.fku.itopya.repository;

import com.app.fku.itopya.entity.ItopyaKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItopyaKategoriRepository extends JpaRepository<ItopyaKategori, Long> {

    List<ItopyaKategori> findAllByOrderById();
    List<ItopyaKategori> findAllByOrderByIdDesc();
}