package com.mielibi.mielibi.repository;

import com.mielibi.mielibi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteById(Long userID);

    Optional<User> findByUsername(String username);
}
