package com.app.fku.a101.repository;

import com.app.fku.a101.entity.A101Conf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface A101ConfRepository extends JpaRepository<A101Conf, Long> {

    List<A101Conf> findByHostname(String hostname);
}