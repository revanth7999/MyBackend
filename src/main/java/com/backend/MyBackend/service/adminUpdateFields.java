package com.backend.MyBackend.service;

import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.modal.User;
import com.backend.MyBackend.repository.UserRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class adminUpdateFields implements AdminServiceInterface{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto updateFields(Long id,Map<String, Object> fields){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        fields.forEach((key,value) -> {
            switch (key){
                case "email" :
                    user.setEmail(value.toString());
                    break;
                case "isActive" :
                    user.setIsActive(Boolean.parseBoolean(value.toString()));
                    break;
                case "address" :
                    user.setAddress(value.toString());
                    break;
                default :
                    throw new RuntimeException("Field '" + key + "' is not allowed to update");
            }
        });

        userRepository.save(user);

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getIsActive(),
                "",
                user.getEmail(),
                user.getAddress());
    }
}
