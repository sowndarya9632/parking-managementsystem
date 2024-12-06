package com.bridgelabz.serviceimpl;
import com.bridgelabz.dto.UserDTO;
import com.bridgelabz.entity.User;
import com.bridgelabz.exception.SlotUnavailableException;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO registerUser(UserDTO userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRegisteredVehicles(userDto.getRegisteredVehicles());
        return mapToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new SlotUnavailableException("user not found"));
        return mapToDTO(user);
    }

    @Override
    public UserDTO updateUserDetails(Long userId, UserDTO updatedUserDetails) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(updatedUserDetails.getName());
            existingUser.setEmail(updatedUserDetails.getEmail());
            existingUser.setPhone(updatedUserDetails.getPhone());
            existingUser.setRegisteredVehicles(updatedUserDetails.getRegisteredVehicles());
            return mapToDTO(userRepository.save(existingUser)); // Save the updated user
        } else {
            throw new RuntimeException("User not found with id: " + userId); // Handle user not found
        }
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userRequestDTO = new UserDTO();
        userRequestDTO.setName(user.getName());
        userRequestDTO.setEmail(user.getEmail());
        userRequestDTO.setPhone(user.getPhone());
        userRequestDTO.setRegisteredVehicles(user.getRegisteredVehicles());
        return userRequestDTO;
    }
}


