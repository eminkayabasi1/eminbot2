package com.app.fku.mutfakdunyasi.repository;

import com.app.fku.mutfakdunyasi.entity.MdConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MdConfRepository extends JpaRepository<MdConf, Long> {

    List<MdConf> findByHostname(String hostname);
}