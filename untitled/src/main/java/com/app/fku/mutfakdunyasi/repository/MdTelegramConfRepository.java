package com.app.fku.mutfakdunyasi.repository;

import com.app.fku.mutfakdunyasi.entity.MdKategori;
import com.app.fku.mutfakdunyasi.entity.MdTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MdTelegramConfRepository extends JpaRepository<MdTelegramConf, Long> {

    List<MdTelegramConf> findByMdKategori(MdKategori mdKategori);
}