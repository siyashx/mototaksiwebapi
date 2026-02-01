package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.service.impl.MpOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menupro/otp")
@RequiredArgsConstructor
public class MpOtpController {

    private final MpOtpService otpService;

    // ✅ SEND OTP (GET)
    @GetMapping("/send")
    public Boolean sendGet(@RequestParam String phone) {
        otpService.sendOtp(phone);
        return true;
    }

    // ✅ SEND OTP (POST)
    @PostMapping("/send")
    public Boolean sendPost(@RequestParam String phone) {
        otpService.sendOtp(phone);
        return true;
    }

    // ✅ VERIFY OTP (GET) - sizdəki kimi
    @GetMapping("/verify")
    public Boolean verifyGet(@RequestParam String phone,
                             @RequestParam String code) {
        return otpService.verify(phone, code);
    }

    // ✅ VERIFY OTP (POST)
    @PostMapping("/verify")
    public Boolean verifyPost(@RequestParam String phone,
                              @RequestParam String code) {
        return otpService.verify(phone, code);
    }
}

