package com.sante.reventionplatform.repository;

import com.sante.reventionplatform.entity.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository1 extends JpaRepository<User1, Long> {
    Optional<com.sante.reventionplatform.entity.User1> findByEmail(String email);
    boolean existsByEmail(String email);
}