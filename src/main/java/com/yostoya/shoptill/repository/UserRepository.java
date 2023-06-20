package com.yostoya.shoptill.repository;

import com.yostoya.shoptill.domain.User;
import com.yostoya.shoptill.domain.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
