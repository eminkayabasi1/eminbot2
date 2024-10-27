package com.app.fku.medimarkt.repository;

import com.app.fku.medimarkt.entity.MmKategori;
import com.app.fku.medimarkt.entity.MmTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MmTelegramConfRepository extends JpaRepository<MmTelegramConf, Long> {

    List<MmTelegramConf> findByMmKategori(MmKategori mmKategori);
}