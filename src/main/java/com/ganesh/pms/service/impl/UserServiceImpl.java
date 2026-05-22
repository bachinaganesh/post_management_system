package com.ganesh.pms.service.impl;

import com.ganesh.pms.dtos.responses.UserResponseDTO;
import com.ganesh.pms.exceptions.ResourceNotFoundException;
import com.ganesh.pms.models.User;
import com.ganesh.pms.models.enums.SubscriptionPlans;
import com.ganesh.pms.repository.UserRepository;
import com.ganesh.pms.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserResponseDTO saveUser(User user) {
        User savedUser = this.userRepository.save(user);
        return this.modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO addSubscription(SubscriptionPlans subscription) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assert user != null;
        user.setSubscriptionPlans(subscription);
        User savedUser = this.userRepository.save(user);
        return this.modelMapper.map(savedUser, UserResponseDTO.class);
    }
}
