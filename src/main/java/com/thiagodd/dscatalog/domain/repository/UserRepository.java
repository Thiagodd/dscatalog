package com.thiagodd.dscatalog.domain.repository;

import com.thiagodd.dscatalog.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}