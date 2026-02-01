package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dto.MpProductDto;
import com.codesupreme.mototaksiwebapi.menyupro.dto.MpPublicProfileDto;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menupro/public")
@RequiredArgsConstructor
public class MpPublicController {

    private final MpBusinessRepository businessRepo;
    private final MpProductService productService;

    @GetMapping("/{slug}")
    public List<MpProductDto> getMenu(@PathVariable String slug) {
        MpBusiness business = businessRepo.findBySlug(slug).orElseThrow();
        return productService.getAll(business.getId());
    }

    @GetMapping("/profile/{slug}")
    public MpPublicProfileDto getProfile(@PathVariable String slug) {
        MpBusiness business = businessRepo.findBySlug(slug).orElseThrow();

        MpPublicProfileDto dto = new MpPublicProfileDto();
        dto.businessName = business.getBusinessName();
        dto.bio = business.getBio();
        dto.profileImage = business.getProfileImage();
        dto.slug = business.getSlug();
        dto.phone = business.getPhone();

        return dto;
    }
}
