package com.app.fku.genel.repository;

import com.app.fku.genel.entity.MailGonderim;
import com.app.fku.genel.enums.EvetHayirEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MailGonderimRepository extends JpaRepository<MailGonderim, Long> {
    List<MailGonderim> findByStatus(EvetHayirEnum status);

    @Query(value =
            "select * from gnl_mail_gonderim mg where mg.amz_urun_id = :amzUrunId and :kontrolTarihi < mg.kayit_tarihi "
            , nativeQuery = true)
    List<MailGonderim> amzMailGonderilmisMi(
            @Param("amzUrunId") Long amzUrunId,
            @Param("kontrolTarihi") Date kontrolTarihi);

    @Query(value =
            "select * from gnl_mail_gonderim mg where mg.hb_urun_id = :hbUrunId and :kontrolTarihi < mg.kayit_tarihi "
            , nativeQuery = true)
    List<MailGonderim> hbMailGonderilmisMi(
            @Param("hbUrunId") Long hbUrunId,
            @Param("kontrolTarihi") Date kontrolTarihi);

    @Query(value =
            "select * from gnl_mail_gonderim mg where mg.tkn_urun_id = :tknUrunId and :kontrolTarihi < mg.kayit_tarihi "
            , nativeQuery = true)
    List<MailGonderim> tknMailGonderilmisMi(
            @Param("tknUrunId") Long tknUrunId,
            @Param("kontrolTarihi") Date kontrolTarihi);
}