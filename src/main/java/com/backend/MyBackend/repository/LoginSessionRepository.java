package com.backend.MyBackend.repository;

import com.backend.MyBackend.modal.LoginSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginSessionRepository extends JpaRepository<LoginSession, Long>{
}
