package com.app.fku.dyson.repository;

import com.app.fku.dyson.entity.DysonKategori;
import com.app.fku.dyson.entity.DysonTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DysonTelegramConfRepository extends JpaRepository<DysonTelegramConf, Long> {

    List<DysonTelegramConf> findByDysonKategori(DysonKategori dysonKategori);
}