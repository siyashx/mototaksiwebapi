package com.codesupreme.mototaksiwebapi.menyupro.service;

import com.codesupreme.mototaksiwebapi.menyupro.dto.MpBusinessDto;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpApprovalStatus;

import java.util.List;

public interface MpBusinessService {

    MpBusinessDto register(String businessName, String phone, String password);

    MpBusinessDto login(String phone, String password);

    MpBusinessDto getProfile(Long id);

    MpBusinessDto updateProfile(Long id, MpBusinessDto dto);

    // ✅ ADMIN
    List<MpBusinessDto> adminGetAll();

    List<MpBusinessDto> adminGetPending();

    void adminSetStatus(Long id, MpApprovalStatus status, String reason);
}

