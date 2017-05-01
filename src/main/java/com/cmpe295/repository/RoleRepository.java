package com.cmpe295.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe295.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
