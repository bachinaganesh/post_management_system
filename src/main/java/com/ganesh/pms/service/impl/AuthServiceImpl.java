package com.ganesh.pms.service.impl;

import com.ganesh.pms.dtos.SignupDTO;
import com.ganesh.pms.dtos.responses.LoginResponseDTO;
import com.ganesh.pms.dtos.responses.UserResponseDTO;
import com.ganesh.pms.exceptions.ResourceAlreadyExistedException;
import com.ganesh.pms.exceptions.ResourceNotFoundException;
import com.ganesh.pms.models.Session;
import com.ganesh.pms.models.User;
import com.ganesh.pms.models.enums.Role;
import com.ganesh.pms.models.enums.SubscriptionPlans;
import com.ganesh.pms.repository.UserRepository;
import com.ganesh.pms.service.IAuthService;
import com.ganesh.pms.service.ISessionService;
import com.ganesh.pms.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final ISessionService sessionService;

    @Override
    public UserResponseDTO signUp(SignupDTO signupDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(signupDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new ResourceAlreadyExistedException("User with email " + signupDTO.getEmail() + " already exists");
        }
        User user = this.modelMapper.map(signupDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

//        set the subscription as "FREE" for every user when they sign up
        user.setSubscriptionPlans(SubscriptionPlans.FREE);

//        set the free subscription plan for every user
        user.setSubscriptionPlans(SubscriptionPlans.FREE);

        User createdUser = this.userRepository.save(user);
        return this.modelMapper.map(createdUser, UserResponseDTO.class);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public UserResponseDTO adminCreation(SignupDTO signupDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(signupDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new ResourceAlreadyExistedException("Admin with email " + signupDTO.getEmail() + " already exists");
        }
        User user = this.modelMapper.map(signupDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ADMIN);
        user.setSubscriptionPlans(SubscriptionPlans.PREMIUM);
        User createdUser = this.userRepository.save(user);
        return this.modelMapper.map(createdUser, UserResponseDTO.class);
    }

    @Override
    public LoginResponseDTO refreshToken(String refreshToken) {
        Long id = jwtUtils.generateIdFromToken(refreshToken);
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        String accessToken = jwtUtils.generateAccessToken(user);
        return LoginResponseDTO.builder()
                .id(user.getId())
                .jwtToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public String logout(User user) {
        sessionService.deleteAllSessions(user.getId());
        return "All sessions have been deleted of user: " + user.getEmail();
    }


}
