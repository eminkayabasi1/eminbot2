package com.app.fku.trendyol.repository;

import com.app.fku.trendyol.entity.TyIstatistik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TyIstatistikRepository extends JpaRepository<TyIstatistik, Long> {
    List<TyIstatistik> findByHostnameAndYilAndAyAndGunAndSaatOrderById(
            String hostname,
            Integer yil,
            Integer ay,
            Integer gun,
            Integer saat
            );

    List<TyIstatistik> findTop25ByOrderByYilDescAyDescGunDescSaatDescHostname();
}