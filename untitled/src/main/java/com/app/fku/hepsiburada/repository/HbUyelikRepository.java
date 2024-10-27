package com.app.fku.hepsiburada.repository;

import com.app.fku.genel.enums.EvetHayirEnum;
import com.app.fku.genel.enums.MailSahipEnum;
import com.app.fku.hepsiburada.entity.HbUyelik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HbUyelikRepository extends JpaRepository<HbUyelik, Long> {
    List<HbUyelik> findByAdresVarMi(EvetHayirEnum adresVarMi);

    List<HbUyelik> findByMailSahipEnum(MailSahipEnum mailSahipEnum);
}