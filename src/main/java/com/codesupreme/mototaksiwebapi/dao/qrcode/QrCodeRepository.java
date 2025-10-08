package com.codesupreme.mototaksiwebapi.dao.qrcode;

import com.codesupreme.mototaksiwebapi.model.qrcode.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
}
