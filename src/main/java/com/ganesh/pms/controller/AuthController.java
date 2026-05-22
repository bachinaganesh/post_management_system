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
import com.ganesh.pms.utils.SessionUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
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
        Integer sessionLimit = SessionUtils.getSessionCount(user.getSubscriptionPlans());
        List<Session> sessions = sessionService.findSessionsByUser(user);

//        if sessions are exceed the limit then remove the old session
        if(!SessionUtils.isSessionValid(sessionLimit, sessions.size())) {
            sessions.sort(Comparator.comparing(Session::getLastUsedAt));
            Session session = sessions.getFirst();
            sessionService.deleteSession(session);
        }

        String jwtToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

//        create a new session and save it
        Session session = Session.builder()
                .refreshToken(refreshToken)
                .user(user)
                .lastUsedAt(LocalDateTime.now())
                .build();
        sessionService.saveSession(session);

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
//        if refresh token is valid then it will generate the access token for further auth instead of login again`
        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> logout() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String response = authService.logout(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestParam String refreshToken) {
        LoginResponseDTO loginResponseDTO = authService.refresh(refreshToken);
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

}
