package com.luv2code.ProjectManagementSystem.service;

import com.luv2code.ProjectManagementSystem.model.Subscription;
import com.luv2code.ProjectManagementSystem.model.SubscriptionType;
import com.luv2code.ProjectManagementSystem.model.User;

public interface SubscriptionService {
    Subscription createSubscription(User user);

    Subscription getUserSubscription(Long userId) throws  Exception;
    Subscription  upgradeUserSubscription(Long userId, SubscriptionType subType) throws Exception;
    Boolean isValid(Subscription subscription);
}
