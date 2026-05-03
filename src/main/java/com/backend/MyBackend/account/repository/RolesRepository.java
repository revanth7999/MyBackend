package com.backend.MyBackend.account.repository;

import com.backend.MyBackend.account.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long>{
}
