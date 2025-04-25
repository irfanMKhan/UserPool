package com.secured.userpool.service;

import com.secured.userpool.model.dto.UserDTO;
import com.secured.userpool.model.payload.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDTO> get(String sort, String order);

    UserDTO save(UserRequest request);

}
