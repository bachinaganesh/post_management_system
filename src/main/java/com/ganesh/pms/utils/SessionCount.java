package com.ganesh.pms.utils;

import com.ganesh.pms.models.enums.SubscriptionPlans;
import org.springframework.stereotype.Component;

@Component
public class SessionCount {

    public Integer getSessionCount(SubscriptionPlans subscriptionPlans) {
        return switch (subscriptionPlans) {
            case FREE -> 1;
            case BASIC -> 2;
            case PREMIUM -> 4;
            default -> 0;
        };
    }
}
