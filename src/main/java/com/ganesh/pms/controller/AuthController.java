package com.ganesh.pms.controller;

import com.ganesh.pms.dtos.LoginDTO;
import com.ganesh.pms.dtos.SignupDTO;
import com.ganesh.pms.dtos.responses.LoginResponseDTO;
import com.ganesh.pms.dtos.responses.UserResponseDTO;
import com.ganesh.pms.models.Session;
import com.ganesh.pms.models.User;
import com.ganesh.pms.service.IAuthService;
import com.ganesh.pms.service.ISessionService;
import com.ganesh.pms.utils.JWTUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final IAuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final ISessionService sessionService;

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

        List<Session> sessions = sessionService.findSessionsByUser(user);
        log.info("{} are found!", sessions.size());
        String jwtToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .id(user.getId())
                .jwtToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/admincreation")
    public ResponseEntity<UserResponseDTO> adminCreation(@RequestBody @Valid SignupDTO signupDTO) {
        UserResponseDTO userResponseDTO = authService.adminCreation(signupDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestParam String refreshToken) {
        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }
}
