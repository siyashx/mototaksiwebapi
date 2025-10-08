package com.codesupreme.mototaksiwebapi.api.qrcode.controller;

import com.codesupreme.mototaksiwebapi.dto.qrcode.QrCodeDto;
import com.codesupreme.mototaksiwebapi.service.impl.qrcode.QrCodeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/qrcode")
public class QrCodeController {

    private final QrCodeServiceImpl QrCodeServiceImpl;

    public QrCodeController(QrCodeServiceImpl QrCodeServiceImpl) {
        this.QrCodeServiceImpl = QrCodeServiceImpl;
    }

    // List
    @GetMapping
    public List<QrCodeDto> getAllQrCodes() {
        return QrCodeServiceImpl.getAllQrCodes();
    }

    // Id
    @GetMapping("/{qrCodeId}")
    public ResponseEntity<?> getQrCodeById(@PathVariable("qrCodeId") Long qrCodeId) {
        QrCodeDto qrCodeDto = QrCodeServiceImpl.getQrCodeById(qrCodeId);
        if (qrCodeDto != null) {
            return ResponseEntity.ok(qrCodeDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("QrCode doesn't exist with given id..");
    }

    // Update
    @PutMapping("/{qrCodeId}")
    public ResponseEntity<?> updateQrCode(
            @PathVariable("qrCodeId") Long id,
            @RequestBody QrCodeDto qrCodeDto
    ) {
        QrCodeDto updatedQrCode = QrCodeServiceImpl.updateQrCode(id, qrCodeDto);
        if (updatedQrCode != null) {
            return ResponseEntity.ok(updatedQrCode);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{qrCodeId}")
    public ResponseEntity<?> deleteQrCodeById(@PathVariable("qrCodeId") Long qrCodeId) {
        QrCodeServiceImpl.deleteQrCodeById(qrCodeId);
        return ResponseEntity.ok().build();
    }

    // Create
    @PostMapping
    public ResponseEntity<QrCodeDto> createQrCode(@RequestBody QrCodeDto qrCodeDto) {
        return QrCodeServiceImpl.createQrCode(qrCodeDto);
    }
}
