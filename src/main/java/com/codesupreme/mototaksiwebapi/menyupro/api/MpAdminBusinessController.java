package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dto.MpBusinessDto;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpApprovalStatus;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menupro/admin/business")
public class MpAdminBusinessController {

    private final MpBusinessService service;

    @GetMapping("/pending")
    public List<MpBusinessDto> pending() {
        return service.adminGetPending();
    }

    @GetMapping
    public List<MpBusinessDto> all() {
        return service.adminGetAll();
    }

    @PostMapping("/{id}/approve")
    public void approve(@PathVariable Long id) {
        service.adminSetStatus(id, MpApprovalStatus.APPROVED, null);
    }

    @PostMapping("/{id}/reject")
    public void reject(@PathVariable Long id, @RequestParam(required = false) String reason) {
        service.adminSetStatus(id, MpApprovalStatus.REJECTED, reason);
    }
}
