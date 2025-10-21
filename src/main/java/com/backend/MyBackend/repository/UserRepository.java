package com.backend.MyBackend.repository;

import com.backend.MyBackend.modal.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return the User entity if found, otherwise null
     */
    User findByUsername(String username);

    /**
     * Deactivates users by setting their isActive field to false for the given list of user IDs.
     *
     * @param ids the list of user IDs to deactivate
     * @return the number of users deactivated
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE User u SET u.isActive = false WHERE u.id IN :ids")
    int deactivateUsersByIds(@Param("ids") List<Long> ids);
}
