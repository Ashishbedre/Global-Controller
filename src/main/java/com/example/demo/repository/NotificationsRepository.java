package com.example.demo.repository;

import com.example.demo.Entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsRepository extends JpaRepository<Notifications,Long> {

}
