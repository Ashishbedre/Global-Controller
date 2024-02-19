package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.CurrentProductVersion;
import com.example.demo.Entity.SiteDetails;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.UpdateAgentDataSaveDto;
import com.example.demo.repository.CurrentProductVersionRepository;
import com.example.demo.repository.SiteDetailsRepository;
import com.example.demo.service.UpdateAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateAgentServiceImpl implements UpdateAgentService {

    @Autowired
    SiteDetailsRepository siteDetailsRepository;
    @Autowired
    CurrentProductVersionRepository currentProductVersionRepository;
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
}
