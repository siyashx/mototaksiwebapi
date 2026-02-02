package com.codesupreme.mototaksiwebapi.menyupro.service.impl;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dto.MpBusinessDto;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpApprovalStatus;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpBusinessService;
import com.codesupreme.mototaksiwebapi.menyupro.util.PhoneUtil;
import com.codesupreme.mototaksiwebapi.menyupro.whatsapp.evolution.EvolutionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpBusinessServiceImpl implements MpBusinessService {

    private final MpBusinessRepository businessRepo;
    private final ModelMapper mapper;
    private final MpOtpService otpService;
    private final EvolutionService evolutionService;

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

        evolutionService.notifyAdminNewBusiness(businessName, normalized);

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


    @Override
    public List<MpBusinessDto> adminGetAll() {
        return businessRepo.findAll()
                .stream()
                .map(b -> mapper.map(b, MpBusinessDto.class))
                .toList();
    }

    @Override
    public List<MpBusinessDto> adminGetPending() {
        return businessRepo.findAllByApprovalStatusOrderByIdDesc(MpApprovalStatus.PENDING)
                .stream()
                .map(b -> mapper.map(b, MpBusinessDto.class))
                .toList();
    }

    @Override
    public void adminSetStatus(Long id, MpApprovalStatus status, String reason) {
        MpBusiness b = businessRepo.findById(id).orElseThrow();

        b.setApprovalStatus(status);

        if (status == MpApprovalStatus.APPROVED) {
            b.setIsActive(true);
            b.setRejectReason(null);
        } else if (status == MpApprovalStatus.REJECTED) {
            b.setIsActive(false);
            b.setRejectReason(reason);
        } else {
            // PENDING (praktikada admin bunu eləmir, amma fallback)
            b.setIsActive(false);
        }

        businessRepo.save(b);
    }


}
