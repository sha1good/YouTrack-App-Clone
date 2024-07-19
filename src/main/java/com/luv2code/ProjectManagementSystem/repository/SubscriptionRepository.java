package com.luv2code.ProjectManagementSystem.repository;

import com.luv2code.ProjectManagementSystem.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
     Subscription  findByUserId(Long userId);
}
