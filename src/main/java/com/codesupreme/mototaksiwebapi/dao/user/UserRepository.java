package com.codesupreme.mototaksiwebapi.dao.user;

import com.codesupreme.mototaksiwebapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByPhoneNumber(String phoneNumber);
}
