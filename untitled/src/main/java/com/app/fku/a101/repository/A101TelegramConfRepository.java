package com.app.fku.a101.repository;

import com.app.fku.a101.entity.A101Kategori;
import com.app.fku.a101.entity.A101TelegramConf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface A101TelegramConfRepository extends JpaRepository<A101TelegramConf, Long> {

    List<A101TelegramConf> findByA101Kategori(A101Kategori a101Kategori);
}