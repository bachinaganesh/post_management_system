package com.ganesh.pms.service;

import com.ganesh.pms.dtos.responses.UserResponseDTO;
import com.ganesh.pms.models.User;
import com.ganesh.pms.models.enums.SubscriptionPlans;

public interface IUserService {
    User getUserById(Long id);

    UserResponseDTO saveUser(User user);

    UserResponseDTO addSubscription(SubscriptionPlans subscription);
}
