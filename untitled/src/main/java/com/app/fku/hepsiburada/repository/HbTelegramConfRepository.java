package com.app.fku.hepsiburada.repository;

import com.app.fku.hepsiburada.entity.HbKategori;
import com.app.fku.hepsiburada.entity.HbTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HbTelegramConfRepository extends JpaRepository<HbTelegramConf, Long> {

    List<HbTelegramConf> findByHbKategori(HbKategori hbKategori);
}