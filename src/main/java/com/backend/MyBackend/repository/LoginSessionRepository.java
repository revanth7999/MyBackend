package com.backend.MyBackend.repository;

import com.backend.MyBackend.modal.LoginSession;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * LoginSessionRepository provides database access methods for LoginSession entities.
 */
@Repository
public interface LoginSessionRepository extends JpaRepository<LoginSession, Long>{

    // Custom query to find user IDs with login_time less than or equal to the specified timestamp
    @Query(value = "SELECT user_id FROM login_session WHERE login_time <= :since", nativeQuery = true)
    List<Long> findUserIdsSince(@Param("since") Timestamp since);

}
