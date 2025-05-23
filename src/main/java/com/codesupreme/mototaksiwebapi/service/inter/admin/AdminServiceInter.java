package com.codesupreme.mototaksiwebapi.service.inter.admin;

import com.codesupreme.mototaksiwebapi.dto.admin.AdminDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminServiceInter {

    List<AdminDto> getAllAdmins();
    AdminDto getAdminById(Long id);
    AdminDto updateAdmin(Long id, AdminDto adminDto);
    ResponseEntity<AdminDto> createAdmin(AdminDto adminDto);
    Boolean isAdminPhoneNumberTaken(String phoneNumber);
    AdminDto findAdminByPhoneNumber(String phoneNumber);
}
