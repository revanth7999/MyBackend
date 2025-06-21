package com.backend.MyBackend.repository;

import com.backend.MyBackend.modal.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long>{
}
