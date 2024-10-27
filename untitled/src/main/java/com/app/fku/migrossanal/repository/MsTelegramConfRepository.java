package com.app.fku.migrossanal.repository;

import com.app.fku.migrossanal.entity.MsKategori;
import com.app.fku.migrossanal.entity.MsTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsTelegramConfRepository extends JpaRepository<MsTelegramConf, Long> {

    List<MsTelegramConf> findByMsKategori(MsKategori msKategori);
}