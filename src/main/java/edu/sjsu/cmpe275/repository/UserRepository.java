package edu.sjsu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
