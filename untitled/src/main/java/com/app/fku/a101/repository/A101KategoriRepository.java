package com.app.fku.a101.repository;

import com.app.fku.a101.entity.A101Kategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface A101KategoriRepository extends JpaRepository<A101Kategori, Long> {

    List<A101Kategori> findAllByOrderById();
    List<A101Kategori> findAllByOrderByIdDesc();
}