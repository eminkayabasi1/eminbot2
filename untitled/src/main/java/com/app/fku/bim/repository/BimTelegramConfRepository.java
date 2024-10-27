package com.app.fku.bim.repository;

import com.app.fku.bim.entity.BimKategori;
import com.app.fku.bim.entity.BimTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BimTelegramConfRepository extends JpaRepository<BimTelegramConf, Long> {

    List<BimTelegramConf> findByBimKategori(BimKategori bimKategori);
}