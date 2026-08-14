package com.backend.MyBackend.feignClient.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationServiceApiResponse{

    private String message;
    private Object data;

    public NotificationServiceApiResponse(){
    }

    public NotificationServiceApiResponse(String message,Object data){
        this.message = message;
        this.data = data;
    }

}
