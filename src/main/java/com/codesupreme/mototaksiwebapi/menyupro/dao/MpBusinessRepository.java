package com.codesupreme.mototaksiwebapi.menyupro.dao;

import com.codesupreme.mototaksiwebapi.menyupro.model.MpApprovalStatus;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MpBusinessRepository extends JpaRepository<MpBusiness, Long> {

    List<MpBusiness> findAllByApprovalStatusOrderByIdDesc(MpApprovalStatus status);

    Optional<MpBusiness> findByPhone(String phone);

    Optional<MpBusiness> findBySlug(String slug);

    boolean existsByPhone(String phone);

    boolean existsBySlug(String slug);

}
