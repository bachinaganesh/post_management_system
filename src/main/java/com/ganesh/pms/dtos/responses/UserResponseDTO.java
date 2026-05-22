package com.ganesh.pms.dtos.responses;

import com.ganesh.pms.models.enums.Role;
import com.ganesh.pms.models.enums.SubscriptionPlans;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private SubscriptionPlans subscriptionPlans;
    private Set<Role> roles = new HashSet<>();
}
