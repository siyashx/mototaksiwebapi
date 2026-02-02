package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpApprovalStatus;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menupro/admin/business")
public class MpAdminBusinessController {

    private final MpBusinessRepository repo;

    @GetMapping("/pending")
    public List<MpBusiness> pending() {
        return repo.findAllByApprovalStatusOrderByIdDesc(MpApprovalStatus.PENDING);
    }

    @PostMapping("/{id}/approve")
    public void approve(@PathVariable Long id) {
        MpBusiness b = repo.findById(id).orElseThrow();
        b.setApprovalStatus(MpApprovalStatus.APPROVED);
        b.setIsActive(true);
        b.setRejectReason(null);
        repo.save(b);
    }

    @PostMapping("/{id}/reject")
    public void reject(@PathVariable Long id, @RequestParam(required = false) String reason) {
        MpBusiness b = repo.findById(id).orElseThrow();
        b.setApprovalStatus(MpApprovalStatus.REJECTED);
        b.setIsActive(false);
        b.setRejectReason(reason);
        repo.save(b);
    }
}

