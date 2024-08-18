package com.prelude.social_app.repository;

import com.prelude.social_app.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
    Optional<ApplicationUser> findByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
