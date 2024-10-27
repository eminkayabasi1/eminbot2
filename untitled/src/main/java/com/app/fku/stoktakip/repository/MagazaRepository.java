package com.app.fku.stoktakip.repository;

import com.app.fku.stoktakip.entity.Magaza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazaRepository extends JpaRepository<Magaza, Long> {

    Magaza findByMagazaAdi(String magazaAdi);
}