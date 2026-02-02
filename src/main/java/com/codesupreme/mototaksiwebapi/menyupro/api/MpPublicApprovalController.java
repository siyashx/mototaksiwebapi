package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.util.PhoneUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menupro/public")
public class MpPublicApprovalController {

    private final MpBusinessRepository repo;

    @GetMapping("/approval-by-phone")
    public MpBusiness approvalByPhone(@RequestParam String phone) {
        String normalized = PhoneUtil.normalize(phone);
        return repo.findByPhone(normalized).orElseThrow();
    }
}