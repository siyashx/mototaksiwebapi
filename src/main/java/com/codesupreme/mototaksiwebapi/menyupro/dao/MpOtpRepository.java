package com.codesupreme.mototaksiwebapi.menyupro.dao;

import com.codesupreme.mototaksiwebapi.menyupro.model.MpOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MpOtpRepository extends JpaRepository<MpOtp, Long> {
    Optional<MpOtp> findTopByPhoneOrderByIdDesc(String phone);
}
