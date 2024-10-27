package com.app.fku.teknosa.repository;

import com.app.fku.teknosa.entity.TknKategori;
import com.app.fku.teknosa.entity.TknTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TknTelegramConfRepository extends JpaRepository<TknTelegramConf, Long> {

    List<TknTelegramConf> findByTknKategori(TknKategori tknKategori);
}