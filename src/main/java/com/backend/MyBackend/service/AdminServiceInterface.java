package com.backend.MyBackend.service;

import com.backend.MyBackend.dto.UserDto;
import java.util.Map;

public interface AdminServiceInterface{

    /**
     * Update specific fields of a user identified by id.
     */
    UserDto updateFields(Long id,Map<String, Object> fields);

}
