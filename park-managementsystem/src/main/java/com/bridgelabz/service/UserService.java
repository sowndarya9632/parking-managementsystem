package com.bridgelabz.service;

import com.bridgelabz.dto.UserDTO;
import com.bridgelabz.entity.User;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO user);
    public UserDTO getUserDetails(Long userId);
    public UserDTO updateUserDetails(Long userId, UserDTO updatedUserDetails);
}

