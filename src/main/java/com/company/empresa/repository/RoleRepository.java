package com.company.empresa.repository;

import com.company.empresa.entity.ERole;
import com.company.empresa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //Role findByName(String role);

    Role findByName(ERole name);
}
