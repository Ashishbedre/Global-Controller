package com.example.demo.service.serviceimpl;

import ch.qos.logback.core.util.FixedDelay;
import com.example.demo.Entity.*;
import com.example.demo.dto.*;
import com.example.demo.enums.Task;
import com.example.demo.repository.CurrentProductVersionRepository;
import com.example.demo.repository.SiteDetailsRepository;
import com.example.demo.repository.UpdateProductVersionRepository;
import com.example.demo.service.UpdateAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EnableScheduling
@Service
public class UpdateAgentServiceImpl implements UpdateAgentService {

    @Autowired
    SiteDetailsRepository siteDetailsRepository;
    @Autowired
    CurrentProductVersionRepository currentProductVersionRepository;

    @Autowired
    UpdateProductVersionRepository updateProductVersionRepository;

    @Override
    public void saveDataToSiteDetailsAndCurrentProductVersion(UpdateAgentDataSaveDto updateAgentDataSaveDto) {
        String deploymentId = updateAgentDataSaveDto.getDeploymentId();

        // Save SiteDetails if not exist
        SiteDetails siteDetails = siteDetailsRepository.findByDeploymentId(deploymentId);
        if (siteDetails == null) {
            siteDetails = new SiteDetails();
            siteDetails.setDeploymentId(deploymentId);
            siteDetails.setTenantId(updateAgentDataSaveDto.getTenantId());
            siteDetailsRepository.save(siteDetails);
            createPost();
        }

        // Check if current version exists, if not save
        List<CurrentProductVersion> checkCurrentVersion = currentProductVersionRepository.findByDeploymentId(deploymentId);
        if (checkCurrentVersion.isEmpty()) {
            List<ProductDto> productDetails = updateAgentDataSaveDto.getProductDetails();
            List<CurrentProductVersion> currentProductVersions = new ArrayList<>(productDetails.size());
            for (ProductDto productDto : productDetails) {
                CurrentProductVersion currentProductVersion = new CurrentProductVersion();
                currentProductVersion.setDeploymentId(deploymentId);
                currentProductVersion.setProductName(productDto.getProductName());
                currentProductVersion.setProductVersion(productDto.getProductVersion());
                currentProductVersions.add(currentProductVersion);
            }
            currentProductVersionRepository.saveAll(currentProductVersions);
        }
    }

    //Create post in
    public void createPost() {
        // Define the request URL
        String url = "http://localhost:8081/v1/DashBoard/createPost";

        // Create a WebClient instance
        WebClient webClient = WebClient.create();

        // Create a Notification object representing the request body
        List<Notification> notifications = new ArrayList<>();
        Notification notification = new Notification();
        notification.setHeader("Site Management");
        notification.setBody("A new Site is available for provision");
        notification.setReadStatus(false);
        notification.setCategory("Site Management");
        notification.setSubCategory("Add Site");
        notifications.add(notification);

        // Make the POST request
        String response = webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(notifications)
                .retrieve()
                .bodyToMono(String.class)
                .block();



    }

//    @Override
//    public UpdateAvailableDataDto getTheUpdateAvailable(String deploymentId,String tenantName) {
//        UpdateAvailableDataDto updateAgentDataSaveDto = new UpdateAvailableDataDto();
//        updateAgentDataSaveDto.setDeploymentId(deploymentId);
//        updateAgentDataSaveDto.setTenantId(tenantName);
//        if(siteDetailsRepository.existsByProvisionAndUpdateAvailableAndDeploymentId(deploymentId)){
//            //for update lastSeen
//            SiteDetails localDateTimeUpdate = siteDetailsRepository.findByDeploymentId(deploymentId);
//            localDateTimeUpdate.setLastSeen(LocalDateTime.now());
//            siteDetailsRepository.save(localDateTimeUpdate);
//
//            List<UpdateProductVersion> sendUpdateAgent = updateProductVersionRepository.findByDeploymentId(deploymentId);
//            List<ProductDetailsDto> dto = new ArrayList<>();
//            for(UpdateProductVersion send : sendUpdateAgent) {
//                ProductDetailsDto productDetailsDto = new ProductDetailsDto();
//                productDetailsDto.setProductName(send.getProductName());
//                productDetailsDto.setProductVersion(send.getProductVersion());
//                productDetailsDto.setProduct_scheduled_update(send.getProduct_scheduled_update());
//                productDetailsDto.setProduct_scheduled_update_dateTime(send.getProduct_scheduled_update_dateTime());
//                productDetailsDto.setTask(send.getTask());
//                dto.add(productDetailsDto);
//                send.setTask(Task.InQueue);
//                updateProductVersionRepository.save(send);
//            }
//            updateAgentDataSaveDto.setProductDetails(dto);
//        }
//        return updateAgentDataSaveDto;
//
//    }

    @Override
    public DeploymentUpdateInfoDto getTheUpdateAvailable(String deploymentId, String tenantName) {
        DeploymentUpdateInfoDto deploymentUpdateInfoDto = new DeploymentUpdateInfoDto();

        // Check if the site details exist for the given deployment ID
        if (siteDetailsRepository.existsByProvisionAndUpdateAvailableAndDeploymentId(deploymentId)) {
            SiteDetails siteDetails = siteDetailsRepository.findByDeploymentId(deploymentId);


            //for time should be save or we can say that for website is active or not
            siteDetails.setLastSeen(LocalDateTime.now());
            siteDetailsRepository.save(siteDetails);

            // Populate site details
            SiteDetailsDto siteDetailsDto = new SiteDetailsDto();
            siteDetailsDto.setSiteName(siteDetails.getSiteId()); // Assuming siteId is the site name
            siteDetailsDto.setAddress(convertAddressDto(siteDetails.getAddresses()));
            siteDetailsDto.setPersonOfContact(convertPersonDto(siteDetails.getPersonsOfContact()));

            // Populate provision status
            deploymentUpdateInfoDto.setProvisionStatus(siteDetails.getProvision());
            deploymentUpdateInfoDto.setSiteDetails(siteDetailsDto);
            // Populate update details
            UpdateDto updateDto = new UpdateDto();
            List<ProductDto> productDtos = new ArrayList<>();
            List<UpdateProductVersion> updateProductVersions = updateProductVersionRepository.findByDeploymentId(deploymentId);
            Boolean scheduledUpdate =false;
            LocalDateTime updateDateTime = null;
            for (UpdateProductVersion updateProductVersion : updateProductVersions) {
                ProductDto productDto = new ProductDto();
                productDto.setProductName(updateProductVersion.getProductName());
                productDto.setProductVersion(updateProductVersion.getProductVersion());
                productDtos.add(productDto);
                if(updateProductVersion.getProduct_scheduled_update()!=null && updateProductVersion.getProduct_scheduled_update()==true){
                    scheduledUpdate = true;
                    updateDateTime = updateProductVersion.getProduct_scheduled_update_dateTime();
                }
            }
            updateDto.setProducts(productDtos);
            updateDto.setUpdateAvailable(true); // Assuming update is always available if products exist
            updateDto.setScheduledUpdate(scheduledUpdate); // Assuming no scheduled update for now
            updateDto.setUpdateDateTime(updateDateTime); // Assuming current date for update datetime

            deploymentUpdateInfoDto.setUpdate(updateDto);
        } else if (siteDetailsRepository.existsByDeploymentIdAndProvision(deploymentId)) {
            SiteDetails siteDetails = siteDetailsRepository.findByDeploymentId(deploymentId);

            // Populate site details
            SiteDetailsDto siteDetailsDto = new SiteDetailsDto();
            siteDetailsDto.setSiteName(siteDetails.getSiteId()); // Assuming siteId is the site name
            siteDetailsDto.setAddress(convertAddressDto(siteDetails.getAddresses()));
            siteDetailsDto.setPersonOfContact(convertPersonDto(siteDetails.getPersonsOfContact()));

            // Populate provision status
            deploymentUpdateInfoDto.setProvisionStatus(siteDetails.getProvision());
            deploymentUpdateInfoDto.setSiteDetails(siteDetailsDto);
            UpdateDto updateDto = new UpdateDto();
            updateDto.setUpdateAvailable(false);
            deploymentUpdateInfoDto.setUpdate(updateDto);
        } else{
            deploymentUpdateInfoDto.setProvisionStatus(false);
            return deploymentUpdateInfoDto;
        }

        return deploymentUpdateInfoDto;
    }

    public PersonDto convertPersonDto(Person person){
        PersonDto personDto = new PersonDto();
        personDto.setContact(person.getContact());
        personDto.setEmail(person.getEmail());
        personDto.setFullName(person.getFullName());
        return personDto;
    }

    public AddressDto convertAddressDto(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setStreetName(address.getStreetName());
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        addressDto.setPinCode(address.getPinCode());
        addressDto.setState(address.getState());
        return addressDto;
    }

    @Override
    public boolean saveDataToUpdateVersion(UpdateAvailableDataDto updateAvailableDataDto) {
        List<UpdateProductVersion> updateProductVersions = new ArrayList<>();

        for (ProductDetailsDto productDetailsDto : updateAvailableDataDto.getProductDetails()) {
            Optional<UpdateProductVersion> optionalUpdateProductVersion = updateProductVersionRepository.findByDeploymentIdAndProductNameAndProductVersion(
                    updateAvailableDataDto.getDeploymentId(),productDetailsDto.getProductName(),productDetailsDto.getProductVersion());
            if (optionalUpdateProductVersion.isPresent()) {
                UpdateProductVersion updateProductVersion = optionalUpdateProductVersion.get();
                updateProductVersion.setDeploymentId(updateAvailableDataDto.getDeploymentId());
                updateProductVersion.setProductName(productDetailsDto.getProductName());
                updateProductVersion.setProductVersion(productDetailsDto.getProductVersion());
                updateProductVersion.setProduct_scheduled_update(productDetailsDto.getProduct_scheduled_update());
                updateProductVersion.setProduct_scheduled_update_dateTime(productDetailsDto.getProduct_scheduled_update_dateTime());
                updateProductVersion.setTask(productDetailsDto.getTask());

                updateProductVersions.add(updateProductVersion);
            }else{
                System.err.println("UpdateProductVersion not found for productName: " + productDetailsDto.getProductName() + " and productVersion: " + productDetailsDto.getProductVersion());

            }
        }
        updateProductVersionRepository.saveAll(updateProductVersions);

        return true;
    }


    @Scheduled(fixedDelay = 10000)
    void updateProductVersionAndDeleteUpdateProductVersion() {
        List<UpdateProductVersion> completedUpdates = updateProductVersionRepository.findByTask(Task.Completed);
        for (UpdateProductVersion update : completedUpdates) {
            // Update product version in CurrentProductVersion entity
            updateProductVersionRepository.updateProductVersion(update.getProductName(), update.getDeploymentId(), update.getProductVersion());

            // Delete UpdateProductVersion entity
            updateProductVersionRepository.deleteCompletedUpdates(Task.Completed, update.getProductName(), update.getDeploymentId());
        }
    }

    @Scheduled(fixedDelay = 10000)
    void updateProductStatus() {
        List<SiteDetails> siteDetails = siteDetailsRepository.findAll();
        for(SiteDetails updateSiteDetails : siteDetails){
            boolean isActive = IsSiteActive(updateSiteDetails.getLastSeen());
            updateSiteDetails.setActive(isActive);
            siteDetailsRepository.save(updateSiteDetails);
        }
    }

    // Method to determine if the site is active based on lastSeen time
    public boolean IsSiteActive(LocalDateTime lastSeen) {
        if (lastSeen == null) {
            return false; // If lastSeen is null, site is considered inactive
        }

        // Calculate the difference between current time and lastSeen time
//        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusSeconds(21);

        // Compare lastSeen with ten minutes ago
        return lastSeen.isAfter(tenMinutesAgo);
    }
}
