package com.codesupreme.mototaksiwebapi.menyupro.dao;

import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MpBusinessRepository extends JpaRepository<MpBusiness, Long> {

    Optional<MpBusiness> findByPhone(String phone);

    Optional<MpBusiness> findBySlug(String slug);

    boolean existsByPhone(String phone);

    boolean existsBySlug(String slug);
}
