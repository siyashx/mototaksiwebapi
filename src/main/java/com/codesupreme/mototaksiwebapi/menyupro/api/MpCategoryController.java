package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dto.MpCategoryDto;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menupro/category")
@RequiredArgsConstructor
public class MpCategoryController {

    private final MpCategoryService service;

    @PostMapping("/{businessId}")
    public MpCategoryDto create(@PathVariable Long businessId,
                                @RequestBody MpCategoryDto dto) {
        return service.create(dto, businessId);
    }

    @GetMapping("/{businessId}")
    public List<MpCategoryDto> getAll(@PathVariable Long businessId) {
        return service.getAll(businessId);
    }

    @PutMapping("/{id}")
    public MpCategoryDto update(@PathVariable Long id,
                                @RequestBody MpCategoryDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        return service.removeById(id);
    }
}

