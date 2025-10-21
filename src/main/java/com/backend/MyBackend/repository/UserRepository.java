package com.backend.MyBackend.repository;

import com.backend.MyBackend.modal.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    List<User> findByisActiveFalse();
}
