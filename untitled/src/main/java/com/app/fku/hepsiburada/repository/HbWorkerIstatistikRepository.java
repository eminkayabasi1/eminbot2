package com.app.fku.hepsiburada.repository;

import com.app.fku.hepsiburada.entity.HbWorkerIstatistik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HbWorkerIstatistikRepository extends JpaRepository<HbWorkerIstatistik, Long> {
    List<HbWorkerIstatistik> findByHostnameAndYilAndAyAndGunAndSaatOrderById(
            String hostname,
            Integer yil,
            Integer ay,
            Integer gun,
            Integer saat
            );

    List<HbWorkerIstatistik> findTop25ByOrderByYilDescAyDescGunDescSaatDescHostname();
}