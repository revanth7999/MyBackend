package com.backend.MyBackend.jobs;

import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.modal.User;
import com.backend.MyBackend.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobService{

    @Autowired
    private UserRepository userRepository;

    // Run every day at midnight (00:00)
    @Scheduled(cron = "0 * * * * *")
    public List<UserDto> fetchInactiveUsers(){
        System.out.println("🚀 [JOB START] ⏰ Fetching inactive users from database...");
        List<User> inactiveUsers = userRepository.findByisActiveFalse();

        if (inactiveUsers.isEmpty()){
            System.out.println("No inactive users found.");
            return List.of();
        }

        System.out.println("Inactive users found: " + inactiveUsers.size());
        inactiveUsers.forEach(
                user -> System.out.println("Inactive user: " + user.getUsername() + " (ID: " + user.getId() + ")"));

        // You can add logic here like:
        // - Send reminder emails
        // - Log them to a file
        // - Trigger cleanup/deletion processes

        // Convert to DTO to exclude password
        return inactiveUsers.stream()
                .map(user -> new UserDto(user.getId(),user.getUsername(),user.getRole(),user.getIsActive(),""))
                .toList();
    }

}
