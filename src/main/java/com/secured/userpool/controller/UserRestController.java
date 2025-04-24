package com.secured.userpool.controller;

import com.secured.userpool.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping(value = "")
    public RequestEntity<?> get() {
        return null;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public RequestEntity<?> save() {
        return null;
    }

    @PutMapping(value = "")
    public RequestEntity<?> update() {
        return null;
    }

    @DeleteMapping(value = "")
    public RequestEntity<?> delete() {
        return null;
    }

}
