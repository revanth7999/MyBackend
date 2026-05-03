package com.backend.MyBackend.account.service;

import com.backend.MyBackend.account.dto.UserDto;
import java.util.Map;

public interface AdminServiceInterface{

    /**
     * Update specific fields of a user identified by id.
     */
    UserDto updateFields(Long id,Map<String, Object> fields);

}
