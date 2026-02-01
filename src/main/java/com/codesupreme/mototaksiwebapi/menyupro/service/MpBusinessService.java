package com.codesupreme.mototaksiwebapi.menyupro.service;

import com.codesupreme.mototaksiwebapi.menyupro.dto.MpBusinessDto;

public interface MpBusinessService {

    MpBusinessDto register(String businessName, String phone, String password);

    MpBusinessDto login(String phone, String password);

    MpBusinessDto getProfile(Long id);

    MpBusinessDto updateProfile(Long id, MpBusinessDto dto);
}

