package com.backend.MyBackend.service;

import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.modal.User;
import com.backend.MyBackend.repository.UserRepository;
import com.backend.MyBackend.utils.Utility;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeadService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utility utility;

    public UserDto register(User user) {
        user.setPassword(utility.passwordEncrypt(user.getPassword()));
        user.setOccupation(user.getOccupation());
        userRepository.save(user);
        return new UserDto(user.getUsername(), user.getOccupation());
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(user -> allUsers.add(
                new UserDto(user.getUsername(), user.getOccupation())));
        return allUsers;
    }
}
