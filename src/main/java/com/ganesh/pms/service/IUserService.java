package com.ganesh.pms.service;

import com.ganesh.pms.dtos.responses.UserResponseDTO;
import com.ganesh.pms.models.User;

public interface IUserService {
    User getUserById(Long id);

    UserResponseDTO saveUser(User user);
}
