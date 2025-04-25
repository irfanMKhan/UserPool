package com.secured.userpool.controller;

import com.secured.userpool.model.dto.UserDTO;
import com.secured.userpool.model.payload.UserRequest;
import com.secured.userpool.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping(value = "")
    public RequestEntity<?> get() {
        return null;
    }

    @PostMapping("/sorted")
    public ResponseEntity<List<UserDTO>> getSorted(
            @RequestParam(defaultValue = "sort") String sort,
            @RequestParam(defaultValue = "order") String order) {

        List<UserDTO> result = userService.get(sort, order);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.save(request), HttpStatus.CREATED);
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
