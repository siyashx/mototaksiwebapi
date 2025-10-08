package com.codesupreme.mototaksiwebapi.service.inter.qrcode;

import com.codesupreme.mototaksiwebapi.dto.qrcode.QrCodeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QrCodeServiceInter {

    List<QrCodeDto> getAllQrCodes();
    QrCodeDto getQrCodeById(Long id);
    ResponseEntity<QrCodeDto> createQrCode(QrCodeDto QrCodeDto);
    QrCodeDto updateQrCode(Long id, QrCodeDto QrCodeDto);
    void deleteQrCodeById(Long id);
}
