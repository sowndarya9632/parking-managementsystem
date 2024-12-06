package com.bridgelabz.controller;
import com.bridgelabz.dto.UserDTO;
import com.bridgelabz.entity.User;
import com.bridgelabz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to register a user
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
            return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.INTERNAL_SERVER_ERROR); // Handle errors
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Long userId) {
            return new ResponseEntity<>(userService.getUserDetails(userId), HttpStatus.NOT_FOUND); // Return 404 if user not found
        }
        @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserDetails(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
            return new ResponseEntity<>(userService.updateUserDetails(userId,userDTO), HttpStatus.NOT_FOUND); // Return 404 if user not found
        }
    }


