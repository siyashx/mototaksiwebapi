package com.codesupreme.mototaksiwebapi.dao.admin;

import com.codesupreme.mototaksiwebapi.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findAdminByPhoneNumber(String phoneNumber);

}
