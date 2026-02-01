package com.codesupreme.mototaksiwebapi.menyupro.service;

import com.codesupreme.mototaksiwebapi.menyupro.dto.MpProductDto;

import java.util.List;

public interface MpProductService {

    MpProductDto create(MpProductDto dto, Long businessId);

    List<MpProductDto> getAll(Long businessId);

    Boolean removeById(Long id);

    MpProductDto update(Long id, MpProductDto dto);
}

