package com.codesupreme.mototaksiwebapi.menyupro.service.impl;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dao.MpCategoryRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dao.MpProductRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dto.MpProductDto;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpCategory;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpProduct;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MpProductServiceImpl implements MpProductService {

    private final MpProductRepository productRepo;
    private final MpBusinessRepository businessRepo;
    private final MpCategoryRepository categoryRepo;
    private final ModelMapper mapper;

    @Override
    public MpProductDto create(MpProductDto dto, Long businessId) {
        MpBusiness business = businessRepo.findById(businessId).orElseThrow();
        MpCategory category = categoryRepo.findById(dto.categoryId).orElseThrow();

        MpProduct product = new MpProduct();
        product.setTitle(dto.title);
        product.setDescription(dto.description);
        product.setPrice(dto.price);
        product.setImage(dto.image);
        product.setBusiness(business);
        product.setCategory(category);

        MpProductDto result = mapper.map(productRepo.save(product), MpProductDto.class);
        result.categoryId = category.getId();
        return result;
    }

    @Override
    public List<MpProductDto> getAll(Long businessId) {
        MpBusiness business = businessRepo.findById(businessId).orElseThrow();

        return productRepo.findAllByBusiness(business)
                .stream()
                .map(p -> {
                    MpProductDto dto = mapper.map(p, MpProductDto.class);
                    dto.categoryId = p.getCategory().getId();
                    return dto;
                })
                .toList();
    }

    @Override
    public Boolean removeById(Long id) {
        productRepo.deleteById(id);
        return true;
    }

    @Override
    public MpProductDto update(Long id, MpProductDto dto) {
        MpProduct product = productRepo.findById(id).orElseThrow();

        product.setTitle(dto.title);
        product.setDescription(dto.description);
        product.setPrice(dto.price);
        product.setImage(dto.image);

        return mapper.map(productRepo.save(product), MpProductDto.class);
    }
}
