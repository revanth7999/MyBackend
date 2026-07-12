package com.backend.MyBackend.account.dto;

import com.backend.MyBackend.common.dto.MetaDto;
import com.backend.MyBackend.common.dto.TokenDto;
import com.backend.MyBackend.common.dto.UserDetailsDto;

public class RegisterUserResponseDto{

    private UserDetailsDto user;
    private TokenDto tokens;
    private MetaDto meta;

    public UserDetailsDto getUser(){
        return user;
    }

    public TokenDto getTokens(){
        return tokens;
    }

    public MetaDto getMeta(){
        return meta;
    }

    private RegisterUserResponseDto(RegisterUserResponseDtoBuilder builder){
        this.user = builder.user;
        this.tokens = builder.tokens;
        this.meta = builder.meta;
    }

    public RegisterUserResponseDto(){
    }

    public static class RegisterUserResponseDtoBuilder{
        private UserDetailsDto user;
        private TokenDto tokens;
        private MetaDto meta;

        public RegisterUserResponseDtoBuilder user(UserDetailsDto user){
            this.user = user;
            return this;
        }

        public RegisterUserResponseDtoBuilder tokens(TokenDto tokens){
            this.tokens = tokens;
            return this;
        }

        public RegisterUserResponseDtoBuilder meta(MetaDto meta){
            this.meta = meta;
            return this;
        }

        public RegisterUserResponseDto build(){
            return new RegisterUserResponseDto(this);
        }
    }

}
