package com.app.fku.vatan.repository;

import com.app.fku.vatan.entity.VatanKategori;
import com.app.fku.vatan.entity.VatanTelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VatanTelegramConfRepository extends JpaRepository<VatanTelegramConf, Long> {

    List<VatanTelegramConf> findByVatanKategori(VatanKategori vatanKategori);
}