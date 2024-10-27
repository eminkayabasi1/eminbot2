package com.app.fku.stoktakip.repository;

import com.app.fku.stoktakip.entity.Renk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenkRepository extends JpaRepository<Renk, Long> {

    Renk findByRenk(String renk);
}