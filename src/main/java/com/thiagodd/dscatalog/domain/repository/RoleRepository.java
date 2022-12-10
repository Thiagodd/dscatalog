package com.thiagodd.dscatalog.domain.repository;

import com.thiagodd.dscatalog.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}