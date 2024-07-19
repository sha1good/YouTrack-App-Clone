package com.luv2code.ProjectManagementSystem.service;

import com.luv2code.ProjectManagementSystem.model.Subscription;
import com.luv2code.ProjectManagementSystem.model.SubscriptionType;
import com.luv2code.ProjectManagementSystem.model.User;
import com.luv2code.ProjectManagementSystem.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subRepository;
    @Autowired
    private UserService userService;

    @Override
    public Subscription createSubscription(User user) {
        Subscription sub = new Subscription();
        sub.setUser(user);
        sub.setSubscriptionStartDate(LocalDate.now());
        sub.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        sub.setIsValid(true);
        sub.setSubType(SubscriptionType.FREE);

        return subRepository.save(sub);
    }

    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {

        Subscription subscription = subRepository.findByUserId(userId);
        if (!isValid(subscription)) {
            subscription.setSubType(SubscriptionType.FREE);
            subscription.setSubscriptionStartDate(LocalDate.now());
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }
        return subRepository.save(subscription);
    }

    @Override
    public Subscription upgradeUserSubscription(Long userId, SubscriptionType subType) throws Exception {
        Subscription sub = subRepository.findByUserId(userId);
        sub.setSubType(subType);
        sub.setSubscriptionStartDate(LocalDate.now());
        if (subType == SubscriptionType.ANNUALLY) {
            sub.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        } else {
            sub.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }
        return subRepository.save(sub);
    }

    @Override
    public Boolean isValid(Subscription subscription) {
        if (subscription.getSubType().equals(SubscriptionType.FREE)) {
            return true;
        }

        LocalDate endDate = subscription.getSubscriptionEndDate();
        LocalDate currentDate = LocalDate.now();
        return endDate.isAfter(currentDate) || endDate.equals(currentDate);
    }
}
