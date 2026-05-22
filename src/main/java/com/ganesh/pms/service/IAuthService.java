package com.ganesh.pms.service;

import com.ganesh.pms.dtos.SignupDTO;
import com.ganesh.pms.dtos.responses.LoginResponseDTO;
import com.ganesh.pms.dtos.responses.UserResponseDTO;
import com.ganesh.pms.models.User;

public interface IAuthService {
    UserResponseDTO signUp(SignupDTO signupDTO);
    User getUserByEmail(String email);
    UserResponseDTO adminCreation(SignupDTO signupDTO);
    LoginResponseDTO refreshToken(String refreshToken);
    String logout(User user);
}
