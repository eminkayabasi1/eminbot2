package com.app.fku.karaca.repository;

import com.app.fku.karaca.entity.KrcKategori;
import com.app.fku.karaca.entity.KrcTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KrcTelegramConfRepository extends JpaRepository<KrcTelegramConf, Long> {

    List<KrcTelegramConf> findByKrcKategori(KrcKategori krcKategori);
}