package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dto.MpAuthDto;
import com.codesupreme.mototaksiwebapi.menyupro.dto.MpBusinessDto;
import com.codesupreme.mototaksiwebapi.menyupro.service.MpBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menupro/business")
@RequiredArgsConstructor
public class MpBusinessController {

    private final MpBusinessService service;

    @PostMapping("/register")
    public MpBusinessDto register(@RequestBody MpAuthDto dto) {
        return service.register(dto.businessName, dto.phone, dto.password);
    }

    @PostMapping("/login")
    public MpBusinessDto login(@RequestBody MpAuthDto dto) {
        return service.login(dto.phone, dto.password);
    }


    @GetMapping("/profile/{id}")
    public MpBusinessDto profile(@PathVariable Long id) {
        return service.getProfile(id);
    }

    @PutMapping("/profile/{id}")
    public MpBusinessDto update(@PathVariable Long id,
                                @RequestBody MpBusinessDto dto) {
        return service.updateProfile(id, dto);
    }
}
