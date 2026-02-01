package com.codesupreme.mototaksiwebapi.menyupro.service.impl;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dto.MpBusinessDto;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpBusinessService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MpBusinessServiceImpl implements MpBusinessService {

    private final MpBusinessRepository businessRepo;
    private final ModelMapper mapper;

    @Override
    public MpBusinessDto register(String businessName, String phone, String password) {

        if (businessRepo.existsByPhone(phone)) {
            throw new RuntimeException("Phone already registered");
        }

        MpBusiness business = new MpBusiness();
        business.setBusinessName(businessName);
        business.setPhone(phone);
        business.setPassword(password);


        String baseSlug = businessName.toLowerCase().replaceAll(" ", "-");
        String slug = baseSlug;
        int i = 1;

        while (businessRepo.existsBySlug(slug)) {
            slug = baseSlug + "-" + i++;
        }

        business.setSlug(slug);

        return mapper.map(businessRepo.save(business), MpBusinessDto.class);
    }

    @Override
    public MpBusinessDto login(String phone, String password) {

        MpBusiness business = businessRepo.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Business not found"));

        if (!business.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        return mapper.map(business, MpBusinessDto.class);
    }

    @Override
    public MpBusinessDto getProfile(Long id) {
        MpBusiness business = businessRepo.findById(id).orElseThrow();
        return mapper.map(business, MpBusinessDto.class);
    }

    @Override
    public MpBusinessDto updateProfile(Long id, MpBusinessDto dto) {
        MpBusiness business = businessRepo.findById(id).orElseThrow();

        business.setBusinessName(dto.businessName);
        business.setBio(dto.bio);
        business.setProfileImage(dto.profileImage);

        return mapper.map(businessRepo.save(business), MpBusinessDto.class);
    }
}
