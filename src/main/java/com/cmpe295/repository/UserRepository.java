package com.cmpe295.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe295.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
