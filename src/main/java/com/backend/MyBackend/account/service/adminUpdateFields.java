package com.backend.MyBackend.account.service;

import com.backend.MyBackend.account.dto.UserDto;
import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.repository.UserRepository;
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
                case "email" -> user.setEmail(value.toString());

                case "isActive" -> user.setIsActive(Boolean.parseBoolean(value.toString()));

                case "address" -> user.setAddress(value.toString());

                default -> throw new RuntimeException(
                        "Field '" + key + "' is not allowed to update");
            }
        });

        userRepository.save(user);

        return new UserDto.UserDtoBuilder(user.getId(),user.getUsername(),user.getRole(),user.getIsActive())
                .token("")
                .email(user.getEmail())
                .address(user.getAddress())
                .build();
    }
}
