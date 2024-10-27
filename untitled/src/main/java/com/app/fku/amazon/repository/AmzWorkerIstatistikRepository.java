package com.app.fku.amazon.repository;

import com.app.fku.amazon.entity.AmzWorkerIstatistik;
import com.app.fku.hepsiburada.entity.HbWorkerIstatistik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmzWorkerIstatistikRepository extends JpaRepository<AmzWorkerIstatistik, Long> {
    List<AmzWorkerIstatistik> findByHostnameAndYilAndAyAndGunAndSaatOrderById(
            String hostname,
            Integer yil,
            Integer ay,
            Integer gun,
            Integer saat
            );

    List<AmzWorkerIstatistik> findTop25ByOrderByYilDescAyDescGunDescSaatDescHostname();
}