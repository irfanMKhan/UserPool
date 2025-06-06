package com.secured.userpool.controller;


import com.secured.userpool.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class LoginRestController {

    private final UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public RequestEntity<?> getToken() {
        return null;
    }

    @PutMapping(value = "")
    public RequestEntity<?> refreshToken() {
        return null;
    }

}
