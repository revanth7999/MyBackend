package com.backend.MyBackend.account.dto;

public class LoginResponseDto{

    private UserDto user;
    private TokenDto tokens;
    private MetaDto meta;

    public LoginResponseDto(){
    }

    private LoginResponseDto(LoginResponseDtoBuilder builder){
        this.user = builder.user;
        this.tokens = builder.tokens;
        this.meta = builder.meta;
    }

    public UserDto getUser(){
        return user;
    }

    public TokenDto getTokens(){
        return tokens;
    }

    public MetaDto getMeta(){
        return meta;
    }

    public static class LoginResponseDtoBuilder{
        private UserDto user;
        private TokenDto tokens;
        private MetaDto meta;

        public LoginResponseDtoBuilder user(UserDto user){
            this.user = user;
            return this;
        }

        public LoginResponseDtoBuilder tokens(TokenDto tokens){
            this.tokens = tokens;
            return this;
        }

        public LoginResponseDtoBuilder meta(MetaDto meta){
            this.meta = meta;
            return this;
        }

        public LoginResponseDto build(){
            return new LoginResponseDto(this);
        }
    }

    // ========================= User DTO =========================

    public static class UserDto{

        private Long id;
        private String username;
        private String role;
        private String email;
        private boolean emailVerified;
        private String address;

        private UserDto(UserDtoBuilder builder){
            this.id = builder.id;
            this.username = builder.username;
            this.role = builder.role;
            this.email = builder.email;
            this.emailVerified = builder.emailVerified;
            this.address = builder.address;
        }

        public Long getId(){
            return id;
        }
        public String getUsername(){
            return username;
        }
        public String getRole(){
            return role;
        }
        public String getEmail(){
            return email;
        }
        public boolean isEmailVerified(){
            return emailVerified;
        }
        public String getAddress(){
            return address;
        }

        public static class UserDtoBuilder{
            private final Long id;
            private final String username;
            private final String role;

            private String email;
            private boolean emailVerified;
            private String address;

            public UserDtoBuilder(Long id,String username,String role){
                this.id = id;
                this.username = username;
                this.role = role;
            }

            public UserDtoBuilder email(String email){
                this.email = email;
                return this;
            }

            public UserDtoBuilder emailVerified(boolean emailVerified){
                this.emailVerified = emailVerified;
                return this;
            }

            public UserDtoBuilder address(String address){
                this.address = address;
                return this;
            }

            public UserDto build(){
                return new UserDto(this);
            }
        }
    }

    // ========================= Token DTO =========================

    public static class TokenDto{

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

    // ========================= Meta DTO =========================

    public static class MetaDto{

        private String environment;

        private MetaDto(MetaDtoBuilder builder){
            this.environment = builder.environment;
        }

        public String getEnvironment(){
            return environment;
        }

        public static class MetaDtoBuilder{

            private String environment;

            public MetaDtoBuilder environment(String environment){
                this.environment = environment;
                return this;
            }

            public MetaDto build(){
                return new MetaDto(this);
            }
        }
    }
}
