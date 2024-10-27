package com.app.fku.amazon.repository;

import com.app.fku.amazon.entity.AmzKategori;
import com.app.fku.amazon.entity.AmzTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmzTelegramConfRepository extends JpaRepository<AmzTelegramConf, Long> {

    List<AmzTelegramConf> findByAmzKategori(AmzKategori amzKategori);
}