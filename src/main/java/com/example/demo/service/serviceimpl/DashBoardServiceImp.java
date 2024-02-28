package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.Notifications;
import com.example.demo.dto.DashBoardCountDto;
import com.example.demo.repository.NotificationsRepository;
import com.example.demo.repository.SiteDetailsRepository;
import com.example.demo.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DashBoardServiceImp implements DashBoardService {

    @Autowired
    SiteDetailsRepository siteDetailsRepository;

    @Autowired
    NotificationsRepository notificationsRepository;

    @Override
    public DashBoardCountDto countTheElementOfSiteLists() {
        DashBoardCountDto dashBoardCountDto = new DashBoardCountDto();
        try {
            dashBoardCountDto.setTenants(siteDetailsRepository.countDistinctTenantIds());
            dashBoardCountDto.setActiveSite(siteDetailsRepository.countActiveSites());
            dashBoardCountDto.setSiteProvisionalFalse(siteDetailsRepository.countUnprovisionedSites());
            dashBoardCountDto.setSiteProvisionalTrue(siteDetailsRepository.countProvisionedSites());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dashBoardCountDto;
    }

    @Override
    public List<Notifications> getAllNotifications() {
        return notificationsRepository.findByReadStatusFalseOrReadStatusNull();
    }

    @Override
    public boolean updateNotifications(List<Notifications> notificationsList) {
        for (Notifications notification : notificationsList) {
            // Retrieve the existing notification from the database (if it exists)
            Optional<Notifications> existingNotificationOptional = notificationsRepository.findByCategory(notification.getCategory());

            // Check if the notification exists in the database
            if (existingNotificationOptional.isPresent()) {
                // Update the existing notification with the new data
                Notifications existingNotification = existingNotificationOptional.get();
                existingNotification.setHeader(notification.getHeader());
                existingNotification.setBody(notification.getBody());
                existingNotification.setReadStatus(notification.isReadStatus());
                existingNotification.setCategory(notification.getCategory());

                // Save the updated notification
                Notifications updatedNotification = notificationsRepository.save(existingNotification);
            }
        }
        return true;
    }

}
