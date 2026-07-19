package com.backend.MyBackend.account.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequestDto{

    @NotBlank(message = "OldPassword is Required")
    private String oldPassword;

    @NotBlank(message = "NewPassword is Required")
    private String newPassword;

    public String getOldPassword(){
        return oldPassword;
    }

    public void setOldPassword(String oldPassword){
        this.oldPassword = oldPassword;
    }

    public String getNewPassword(){
        return newPassword;
    }

    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }
}
