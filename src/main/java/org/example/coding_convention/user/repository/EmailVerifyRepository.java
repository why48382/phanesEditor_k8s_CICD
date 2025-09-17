package org.example.coding_convention.user.repository;


import org.example.coding_convention.user.model.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Integer> {
    Optional<EmailVerify> findByUuid(String uuid);
}
