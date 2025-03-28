package com.codesupreme.mototaksiwebapi.service.impl.admin;

import com.codesupreme.mototaksiwebapi.dao.admin.AdminRepository;
import com.codesupreme.mototaksiwebapi.dto.admin.AdminDto;
import com.codesupreme.mototaksiwebapi.model.admin.Admin;
import com.codesupreme.mototaksiwebapi.service.inter.admin.AdminServiceInter;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminServiceInter {

    private final AdminRepository adminRepository;

    private final ModelMapper modelMapper;

    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<AdminDto> getAllAdmins() {
        List<AdminDto> listDto = new ArrayList<>();

        List<Admin> listEntity = adminRepository.findAll();

        for (Admin entity : listEntity) {
            AdminDto dto = modelMapper.map(entity, AdminDto.class);
            listDto.add(dto);
        }
        return listDto;
    }

    @Override
    public AdminDto getAdminById(Long id) {
        Admin entity = adminRepository.findById(id).orElse(null);
        if (entity != null) {
            return modelMapper.map(entity, AdminDto.class);
        }
        return null;
    }

    @Override
    public AdminDto updateAdmin(Long id, AdminDto adminDto) {
        Optional<Admin> adminOptional = adminRepository.findById(id);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();

            if (adminDto.getName() != null) {
                admin.setName(adminDto.getName());
            }

            if (adminDto.getPhoneNumber() != null) {
                admin.setPhoneNumber(adminDto.getPhoneNumber());
            }

            if (adminDto.getPassword() != null) {
                admin.setPassword(adminDto.getPassword());
            }

            admin = adminRepository.save(admin);
            return modelMapper.map(admin, AdminDto.class);
        }
        return null;
    }

    @Override
    public ResponseEntity<AdminDto> createAdmin(AdminDto adminDto) {
        Admin adminEntity = modelMapper.map(adminDto, Admin.class);
        Admin savedAdmin = adminRepository.save(adminEntity);
        return ResponseEntity.ok(modelMapper.map(savedAdmin, AdminDto.class));
    }

    @Override
    public Boolean isAdminPhoneNumberTaken(String phoneNumber) {
        return adminRepository.findAdminByPhoneNumber(phoneNumber) != null;
    }

    @Override
    public AdminDto findAdminByPhoneNumber(String phoneNumber) {
        Admin admin = adminRepository.findAdminByPhoneNumber(phoneNumber);
        if (admin != null) {
            return modelMapper.map(admin, AdminDto.class);
        }
        return null;
    }
}
