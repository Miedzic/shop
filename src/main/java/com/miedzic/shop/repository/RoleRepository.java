package com.miedzic.shop.repository;

import com.miedzic.shop.domain.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long>  {
    Optional<Role> findByName(String name);
}
