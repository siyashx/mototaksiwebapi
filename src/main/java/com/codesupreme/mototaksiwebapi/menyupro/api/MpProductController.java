package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dto.MpProductDto;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menupro/product")
@RequiredArgsConstructor
public class MpProductController {

    private final MpProductService service;

    @PostMapping("/{businessId}")
    public MpProductDto create(@PathVariable Long businessId,
                               @RequestBody MpProductDto dto) {
        return service.create(dto, businessId);
    }

    @GetMapping("/{businessId}")
    public List<MpProductDto> getAll(@PathVariable Long businessId) {
        return service.getAll(businessId);
    }

    @PutMapping("/{id}")
    public MpProductDto update(@PathVariable Long id,
                               @RequestBody MpProductDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        return service.removeById(id);
    }
}
