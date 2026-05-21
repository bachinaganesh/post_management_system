package com.ganesh.pms.controller;

import com.ganesh.pms.dtos.LoginDTO;
import com.ganesh.pms.dtos.SignupDTO;
import com.ganesh.pms.dtos.responses.LoginResponseDTO;
import com.ganesh.pms.dtos.responses.UserResponseDTO;
import com.ganesh.pms.models.User;
import com.ganesh.pms.service.IAuthService;
import com.ganesh.pms.utils.JWTUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signUp(@RequestBody @Valid SignupDTO signupDTO) {
        UserResponseDTO userResponseDTO = authService.signUp(signupDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        User user = authService.getUserByEmail(loginDTO.getEmail());
        String jwtToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .id(user.getId())
                .jwtToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }
}
