package com.backend.MyBackend.common.dto;

public class TokenDto{
    private String accessToken;
    private String refreshToken;

    private TokenDto(TokenDtoBuilder builder){
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public String getRefreshToken(){
        return refreshToken;
    }

    public static class TokenDtoBuilder{

        private String accessToken;
        private String refreshToken;

        public TokenDtoBuilder accessToken(String accessToken){
            this.accessToken = accessToken;
            return this;
        }

        public TokenDtoBuilder refreshToken(String refreshToken){
            this.refreshToken = refreshToken;
            return this;
        }

        public TokenDto build(){
            return new TokenDto(this);
        }
    }
}
