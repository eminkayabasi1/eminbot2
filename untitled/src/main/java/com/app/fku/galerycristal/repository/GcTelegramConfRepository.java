package com.app.fku.galerycristal.repository;

import com.app.fku.galerycristal.entity.GcKategori;
import com.app.fku.galerycristal.entity.GcTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GcTelegramConfRepository extends JpaRepository<GcTelegramConf, Long> {

    List<GcTelegramConf> findByGcKategori(GcKategori gcKategori);
}