package com.backend.MyBackend.common.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user.inactivity")
@Getter
@Setter
public class UserInactivityProperties{

    private int days;
    private boolean enabled;
}
