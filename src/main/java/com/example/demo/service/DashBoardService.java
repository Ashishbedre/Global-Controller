package com.example.demo.service;

import com.example.demo.Entity.Notifications;
import com.example.demo.dto.DashBoardCountDto;

import java.util.List;

public interface DashBoardService {

    DashBoardCountDto countTheElementOfSiteLists();
    List<Notifications> getAllNotifications();

    boolean updateNotifications(List<Notifications> notificationsList);




}
