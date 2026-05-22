package com.ganesh.pms.utils;

import com.ganesh.pms.models.enums.SubscriptionPlans;
import org.springframework.stereotype.Component;


public class SessionUtils {

    public static Integer getSessionCount(SubscriptionPlans subscriptionPlans) {
        return switch (subscriptionPlans) {
            case FREE -> 1;
            case BASIC -> 2;
            case PREMIUM -> 4;
            default -> 0;
        };
    }

    public static boolean isSessionValid(Integer sessionCount, Integer totalSessions) {
        return sessionCount > totalSessions;
    }
}
