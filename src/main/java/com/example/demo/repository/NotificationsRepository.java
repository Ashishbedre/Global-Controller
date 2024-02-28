package com.example.demo.repository;

import com.example.demo.Entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationsRepository extends JpaRepository<Notifications,Long> {
    List<Notifications> findByReadStatusFalseOrReadStatusNull();

    Optional<Notifications> findByCategory(String category);
}
