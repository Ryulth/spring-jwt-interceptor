package com.ryulth.jwtexample.security.repository;

import com.ryulth.jwtexample.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
}
