package com.stilevo.store.back.stilevo.project.api.domain.repository;

import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
}
