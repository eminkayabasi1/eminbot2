package com.app.fku.hepsiburada.repository;

import com.app.fku.hepsiburada.entity.HbGerideKalanlar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface HbGerideKalanlarRepository extends JpaRepository<HbGerideKalanlar, Date> {

    List<HbGerideKalanlar> findAllByOrderByTarihDesc();
}