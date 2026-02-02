package com.codesupreme.mototaksiwebapi.menyupro.service.impl;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.dao.MpOtpRepository;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpOtp;
import com.codesupreme.mototaksiwebapi.menyupro.util.PhoneUtil;
import com.codesupreme.mototaksiwebapi.menyupro.whatsapp.evolution.EvolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MpOtpService {

    private final MpOtpRepository otpRepo;
    private final MpBusinessRepository businessRepo;
    private final EvolutionService evolutionService;

    public void sendOtp(String phone) {
        String normalized = PhoneUtil.normalize(phone);

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        MpOtp rec = new MpOtp();
        rec.setPhone(normalized); // ✅ burda normalize
        rec.setCode(otp);
        rec.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        rec.setUsed(false);
        rec.setAttempts(0);

        otpRepo.save(rec);

        // ✅ artıq normalized göndər (Evolution içində yenə normalize etsə də problem deyil)
        evolutionService.sendOtp(normalized, otp);
    }

    public boolean verify(String phone, String code) {
        String normalized = PhoneUtil.normalize(phone);

        String cleanCode = (code == null ? "" : code.trim().replaceAll("\\D", ""));
        if (cleanCode.length() != 6) {
            throw new RuntimeException("OTP formatı yanlışdır");
        }

        MpOtp rec = otpRepo.findTopByPhoneOrderByIdDesc(normalized)
                .orElseThrow(() -> new RuntimeException("OTP tapılmadı, yenidən göndərin"));

        if (rec.getUsed()) throw new RuntimeException("OTP artıq istifadə edilib");
        if (rec.getExpiresAt().isBefore(LocalDateTime.now())) throw new RuntimeException("OTP vaxtı bitib");
        if (rec.getAttempts() >= 5) throw new RuntimeException("Çox cəhd edildi");

        rec.setAttempts(rec.getAttempts() + 1);

        if (!rec.getCode().equals(cleanCode)) {
            otpRepo.save(rec);
            throw new RuntimeException("OTP yanlışdır");
        }

        rec.setUsed(true);
        otpRepo.save(rec);

        MpBusiness b = businessRepo.findByPhone(normalized)
                .orElseThrow(() -> new RuntimeException("Biznes tapılmadı (telefonla)"));
        b.setPhoneVerified(true);
        businessRepo.save(b);

        return true;
    }

}
