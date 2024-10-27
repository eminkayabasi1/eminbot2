package com.app.fku.hepsiburada.repository;

import com.app.fku.hepsiburada.entity.HbSepetAlim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HbSepetAlimRepository extends JpaRepository<HbSepetAlim, Long> {

    List<HbSepetAlim> findByEpostaAndDurum(String eposta, Integer durum);
}