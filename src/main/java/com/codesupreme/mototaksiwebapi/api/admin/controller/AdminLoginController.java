package com.codesupreme.mototaksiwebapi.api.admin.controller;

import com.codesupreme.mototaksiwebapi.dto.admin.AdminDto;
import com.codesupreme.mototaksiwebapi.model.LoginRequest;
import com.codesupreme.mototaksiwebapi.service.inter.admin.AdminServiceInter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v6/login/admin")
public class AdminLoginController {
    private final AdminServiceInter adminServiceInter;

    public AdminLoginController(AdminServiceInter adminServiceInter) {
        this.adminServiceInter = adminServiceInter;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String phoneNumber = loginRequest.getPhoneNumber();
        String password = loginRequest.getPassword();

        AdminDto adminDto = adminServiceInter.findAdminByPhoneNumber(phoneNumber);

        if (adminDto != null && adminDto.getPassword().equals(password)) {
                return ResponseEntity.ok(adminDto);
        }


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email, username or password");
    }
}
