package com.ganesh.pms.controller;

import com.ganesh.pms.dtos.responses.UserResponseDTO;
import com.ganesh.pms.exceptions.RoleAlreadyAssignedException;
import com.ganesh.pms.models.User;
import com.ganesh.pms.models.enums.Role;
import com.ganesh.pms.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final IUserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserById() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResponseDTO userResponseDTO = this.modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/addRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> addRole(@RequestParam Role role, @RequestParam Long id) {
        User user = this.userService.getUserById(id);
        if(user.getRoles().contains(role)) {
            throw new RoleAlreadyAssignedException("Role "+role+" is already assigned to the user");
        }
        user.getRoles().add(role);
        UserResponseDTO userResponseDTO = this.userService.saveUser(user);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }


}
