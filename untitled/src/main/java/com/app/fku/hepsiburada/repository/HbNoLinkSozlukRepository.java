package com.app.fku.hepsiburada.repository;

import com.app.fku.hepsiburada.entity.HbNoLinkSozluk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HbNoLinkSozlukRepository extends JpaRepository<HbNoLinkSozluk, Long> {
    HbNoLinkSozluk findByHbNo(String hbNo);
}