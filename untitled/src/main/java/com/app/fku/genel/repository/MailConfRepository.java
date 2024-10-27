package com.app.fku.genel.repository;

import com.app.fku.genel.entity.MailConf;
import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.enums.MailTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailConfRepository extends JpaRepository<MailConf, Long> {
    List<MailConf> findByAktifAndMailType(EvetHayirEnum aktif, MailTypeEnum mailType);
}