package com.ganesh.pms.service.impl;

import com.ganesh.pms.dtos.SignupDTO;
import com.ganesh.pms.dtos.responses.UserResponseDTO;
import com.ganesh.pms.exceptions.ResourceNotFoundException;
import com.ganesh.pms.models.User;
import com.ganesh.pms.repository.UserRepository;
import com.ganesh.pms.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO signUp(SignupDTO signupDTO) {
        User user = this.modelMapper.map(signupDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = this.userRepository.save(user);
        return this.modelMapper.map(createdUser, UserResponseDTO.class);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }


}
