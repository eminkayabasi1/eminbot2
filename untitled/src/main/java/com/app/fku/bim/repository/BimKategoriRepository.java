package com.app.fku.bim.repository;

import com.app.fku.bim.entity.BimKategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BimKategoriRepository extends JpaRepository<BimKategori, Long> {

    List<BimKategori> findAllByOrderById();
    List<BimKategori> findAllByOrderByIdDesc();
}