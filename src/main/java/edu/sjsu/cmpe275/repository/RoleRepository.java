package edu.sjsu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
