package com.app.fku.tefal.repository;

import com.app.fku.tefal.entity.TefalKategori;
import com.app.fku.tefal.entity.TefalTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TefalTelegramConfRepository extends JpaRepository<TefalTelegramConf, Long> {

    List<TefalTelegramConf> findByTefalKategori(TefalKategori tefalKategori);
}