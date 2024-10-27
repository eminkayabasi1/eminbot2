package com.app.fku.itopya.repository;

import com.app.fku.itopya.entity.ItopyaKategori;
import com.app.fku.itopya.entity.ItopyaTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItopyaTelegramConfRepository extends JpaRepository<ItopyaTelegramConf, Long> {

    List<ItopyaTelegramConf> findByItopyaKategori(ItopyaKategori itopyaKategori);
}