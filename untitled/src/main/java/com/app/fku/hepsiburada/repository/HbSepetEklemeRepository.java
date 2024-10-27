package com.app.fku.hepsiburada.repository;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.hepsiburada.entity.HbSepetEkleme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HbSepetEklemeRepository extends JpaRepository<HbSepetEkleme, Long> {

    List<HbSepetEkleme> findByEklendiMi(EvetHayirEnum eklendiMi);

    List<HbSepetEkleme> findTop25ByOrderByIdDesc();
}