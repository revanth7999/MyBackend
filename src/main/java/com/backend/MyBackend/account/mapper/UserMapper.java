package com.backend.MyBackend.account.mapper;

import com.backend.MyBackend.account.dto.CreateUserDto;
import com.backend.MyBackend.account.dto.RegisterUserResponseDto;
import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.common.dto.MetaDto;
import com.backend.MyBackend.common.dto.TokenDto;
import com.backend.MyBackend.common.dto.UserDetailsDto;
import com.backend.MyBackend.common.util.PasswordUtil;
import java.sql.Timestamp;
import org.springframework.stereotype.Component;

@Component
public class UserMapper{
    private final PasswordUtil passwordUtil;

    public UserMapper(PasswordUtil passwordUtil){
        this.passwordUtil = passwordUtil;
    }

    public User toEntity(CreateUserDto createUserDto){

        User databaseUser = new User();
        databaseUser.setUsername(createUserDto.getUsername());
        databaseUser.setPassword(passwordUtil.passwordEncrypt(createUserDto.getPassword()));
        databaseUser.setRole(createUserDto.getRole() != null ? createUserDto.getRole() : "CUSTOMER");
        databaseUser.setIsActive(createUserDto.getIs_active() != null ? createUserDto.getIs_active() : true);
        databaseUser.setCreated_time_stamp(new Timestamp(System.currentTimeMillis()));
        databaseUser.setEmail(createUserDto.getEmail());
        databaseUser.setAddress("");
        return databaseUser;
    }

    public RegisterUserResponseDto toRegisterUserResponse(
            User user,
            String accessToken,
            String refreshToken,
            String environment){

        return new RegisterUserResponseDto.RegisterUserResponseDtoBuilder()
                .user(new UserDetailsDto.UserDtoBuilder(
                        user.getId(),
                        user.getUsername(),
                        user.getRole())
                                .email(user.getEmail())
                                .emailVerified(user.getIsEmailVerified())
                                .address(user.getAddress())
                                .build())
                .tokens(new TokenDto.TokenDtoBuilder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build())
                .meta(new MetaDto.MetaDtoBuilder()
                        .environment(environment)
                        .build())
                .build();
    }
}
