package com.codesupreme.mototaksiwebapi.menyupro.service.impl;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dto.MpBusinessDto;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpApprovalStatus;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpBusinessService;
import com.codesupreme.mototaksiwebapi.menyupro.util.PhoneUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MpBusinessServiceImpl implements MpBusinessService {

    private final MpBusinessRepository businessRepo;
    private final ModelMapper mapper;
    private final MpOtpService otpService;

    @Override
    public MpBusinessDto register(String businessName, String phone, String password) {

        String normalized = PhoneUtil.normalize(phone);

        if (businessRepo.existsByPhone(normalized)) {
            throw new RuntimeException("Phone already registered");
        }

        MpBusiness business = new MpBusiness();
        business.setBusinessName(businessName);
        business.setPhone(normalized); // ✅ normalize saxla
        business.setPassword(password);
        business.setPhoneVerified(false);
        business.setApprovalStatus(MpApprovalStatus.PENDING);
        business.setIsActive(false);


        String baseSlug = businessName.toLowerCase().replaceAll(" ", "-");
        String slug = baseSlug;
        int i = 1;

        while (businessRepo.existsBySlug(slug)) {
            slug = baseSlug + "-" + i++;
        }

        business.setSlug(slug);

        // OTP göndər
        otpService.sendOtp(phone);

        return mapper.map(businessRepo.save(business), MpBusinessDto.class);
    }

    @Override
    public MpBusinessDto login(String phone, String password) {

        String normalized = PhoneUtil.normalize(phone);

        MpBusiness business = businessRepo.findByPhone(normalized)
                .orElseThrow(() -> new RuntimeException("Business not found"));

        if (!business.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        if (!Boolean.TRUE.equals(business.getPhoneVerified())) {
            throw new RuntimeException("PHONE_NOT_VERIFIED");
        }

        if (business.getApprovalStatus() == MpApprovalStatus.PENDING) {
            throw new RuntimeException("APPROVAL_PENDING");
        }

        if (business.getApprovalStatus() == MpApprovalStatus.REJECTED) {
            throw new RuntimeException("APPROVAL_REJECTED");
        }

        if (!Boolean.TRUE.equals(business.getIsActive())) {
            throw new RuntimeException("ACCOUNT_INACTIVE");
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
