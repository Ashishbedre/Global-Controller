package com.example.demo.service.serviceimpl;

import ch.qos.logback.core.util.FixedDelay;
import com.example.demo.Entity.CurrentProductVersion;
import com.example.demo.Entity.SiteDetails;
import com.example.demo.Entity.UpdateProductVersion;
import com.example.demo.dto.ProductDetailsDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.UpdateAgentDataSaveDto;
import com.example.demo.dto.UpdateAvailableDataDto;
import com.example.demo.repository.CurrentProductVersionRepository;
import com.example.demo.repository.SiteDetailsRepository;
import com.example.demo.repository.UpdateProductVersionRepository;
import com.example.demo.service.UpdateAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public UpdateAvailableDataDto getTheUpdateAvailable(String deploymentId,String tenantName) {
        UpdateAvailableDataDto updateAgentDataSaveDto = new UpdateAvailableDataDto();
        updateAgentDataSaveDto.setDeploymentId(deploymentId);
        updateAgentDataSaveDto.setTenantId(tenantName);
        if(siteDetailsRepository.existsByProvisionAndUpdateAvailableAndDeploymentId(deploymentId)){
            List<UpdateProductVersion> sendUpdateAgent = updateProductVersionRepository.findByDeploymentId(deploymentId);
            List<ProductDetailsDto> dto = new ArrayList<>();
            for(UpdateProductVersion send : sendUpdateAgent) {
                ProductDetailsDto productDetailsDto = new ProductDetailsDto();
                productDetailsDto.setProductName(send.getProductName());
                productDetailsDto.setProductVersion(send.getProductVersion());
                productDetailsDto.setProduct_scheduled_update(send.getProduct_scheduled_update());
                productDetailsDto.setProduct_scheduled_update_dateTime(send.getProduct_scheduled_update_dateTime());
                productDetailsDto.setTask(send.getTask());
                dto.add(productDetailsDto);
            }
            updateAgentDataSaveDto.setProductDetails(dto);
        }
        return updateAgentDataSaveDto;

    }

    @Override
    public boolean saveDataToUpdateVersion(UpdateAvailableDataDto updateAvailableDataDto) {
        List<UpdateProductVersion> updateProductVersions = new ArrayList<>();

        for (ProductDetailsDto productDetailsDto : updateAvailableDataDto.getProductDetails()) {
            UpdateProductVersion updateProductVersion = new UpdateProductVersion();
            updateProductVersion.setDeploymentId(updateAvailableDataDto.getDeploymentId());
            updateProductVersion.setProductName(productDetailsDto.getProductName());
            updateProductVersion.setProductVersion(productDetailsDto.getProductVersion());
            updateProductVersion.setProduct_scheduled_update(productDetailsDto.getProduct_scheduled_update());
            updateProductVersion.setProduct_scheduled_update_dateTime(productDetailsDto.getProduct_scheduled_update_dateTime());
            updateProductVersion.setTask(productDetailsDto.getTask());

            updateProductVersions.add(updateProductVersion);
        }
        updateProductVersionRepository.saveAll(updateProductVersions);

        return true;
    }


//    @Scheduled(fixedDelay = 10000)
//    public void updateProductVersionAndDeleteUpdateProductVersion(String productName, String deploymentId) {
//        List<UpdateProductVersion> completedUpdates = updateProductVersionRepository.findByTaskAndProductNameAndDeploymentId(Task.Complete, productName, deploymentId);
//        for (UpdateProductVersion update : completedUpdates) {
//            // Update product version in CurrentProductVersion entity
//            updateProductVersionRepository.updateProductVersion(productName, deploymentId, update.getProductVersion());
//
//            // Delete UpdateProductVersion entity
//            updateProductVersionRepository.deleteCompletedUpdates(Task.Complete, productName, deploymentId);
//        }
//    }
}
