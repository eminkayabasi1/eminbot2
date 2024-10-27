package com.app.fku.stoktakip.repository;

import com.app.fku.stoktakip.entity.Hesap;
import com.app.fku.stoktakip.entity.Magaza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HesapRepository extends JpaRepository<Hesap, Long> {

    List<Hesap> findByMagaza(Magaza magaza);
    Hesap findByMagazaAndEmail(Magaza magaza, String email);
}