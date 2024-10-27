package com.app.fku.teknosa.repository;

import com.app.fku.teknosa.entity.TknWorkerIstatistik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TknWorkerIstatistikRepository extends JpaRepository<TknWorkerIstatistik, Long> {
    List<TknWorkerIstatistik> findByHostnameAndYilAndAyAndGunAndSaatOrderById(
            String hostname,
            Integer yil,
            Integer ay,
            Integer gun,
            Integer saat
            );

    List<TknWorkerIstatistik> findTop25ByOrderByYilDescAyDescGunDescSaatDescHostname();
}