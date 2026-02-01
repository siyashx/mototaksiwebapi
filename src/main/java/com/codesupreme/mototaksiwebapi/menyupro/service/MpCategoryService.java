package com.codesupreme.mototaksiwebapi.menyupro.service;

import com.codesupreme.mototaksiwebapi.menyupro.dto.MpCategoryDto;

import java.util.List;

public interface MpCategoryService {

    MpCategoryDto create(MpCategoryDto dto, Long businessId);

    List<MpCategoryDto> getAll(Long businessId);

    Boolean removeById(Long id);

    MpCategoryDto update(Long id, MpCategoryDto dto);
}
