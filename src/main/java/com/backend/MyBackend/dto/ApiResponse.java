package com.backend.MyBackend.dto;

import com.backend.MyBackend.enums.Constants;

public class ApiResponse {
    private String message;
    private Object data;

	public void setMessage(String message) {
		this.message = message;
	}

	public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
}

