package com.app.fku.turkcell.repository;

import com.app.fku.turkcell.entity.TurkcellKategori;
import com.app.fku.turkcell.entity.TurkcellTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurkcellTelegramConfRepository extends JpaRepository<TurkcellTelegramConf, Long> {

    List<TurkcellTelegramConf> findByTurkcellKategori(TurkcellKategori turkcellKategori);
}