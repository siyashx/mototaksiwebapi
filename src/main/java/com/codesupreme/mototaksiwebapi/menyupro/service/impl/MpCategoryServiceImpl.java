package com.codesupreme.mototaksiwebapi.menyupro.service.impl;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dao.MpCategoryRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dto.MpCategoryDto;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpCategory;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpCategoryServiceImpl implements MpCategoryService {

    private final MpCategoryRepository categoryRepo;
    private final MpBusinessRepository businessRepo;
    private final ModelMapper mapper;

    @Override
    public MpCategoryDto create(MpCategoryDto dto, Long businessId) {
        MpBusiness business = businessRepo.findById(businessId).orElseThrow();

        MpCategory category = new MpCategory();
        category.setName(dto.name);
        category.setBusiness(business);

        MpCategory saved = categoryRepo.save(category);

        MpCategoryDto result = mapper.map(saved, MpCategoryDto.class);
        result.businessId = business.getId();

        return result;
    }

    @Override
    public List<MpCategoryDto> getAll(Long businessId) {
        MpBusiness business = businessRepo.findById(businessId).orElseThrow();

        return categoryRepo.findAllByBusiness(business)
                .stream()
                .map(c -> {
                    MpCategoryDto dto = mapper.map(c, MpCategoryDto.class);
                    dto.businessId = c.getBusiness().getId();
                    return dto;
                })
                .toList();
    }


    @Override
    public Boolean removeById(Long id) {
        categoryRepo.deleteById(id);
        return true;
    }

    @Override
    public MpCategoryDto update(Long id, MpCategoryDto dto) {
        MpCategory category = categoryRepo.findById(id).orElseThrow();
        category.setName(dto.name);
        return mapper.map(categoryRepo.save(category), MpCategoryDto.class);
    }
}

