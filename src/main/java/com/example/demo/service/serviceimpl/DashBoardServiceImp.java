package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.SiteDetails;
import com.example.demo.Entity.UpdateProductVersion;
import com.example.demo.dto.DashBoardCountDto;
import com.example.demo.dto.UpdateAndDowngradeMonitorDto;
import com.example.demo.enums.Task;
import com.example.demo.repository.SiteDetailsRepository;
import com.example.demo.repository.UpdateProductVersionRepository;
import com.example.demo.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashBoardServiceImp implements DashBoardService {

    @Autowired
    SiteDetailsRepository siteDetailsRepository;

    @Autowired
    UpdateProductVersionRepository updateProductVersionRepository;

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
    public List<UpdateAndDowngradeMonitorDto> ListOfUpdateProductVersion() {
        List<String> deploymentIds = updateProductVersionRepository.findDistinctDeploymentId();
        List<UpdateAndDowngradeMonitorDto> updateAndDowngradeMonitorDtos = new ArrayList<>();
        for(String deploymentId : deploymentIds){
            UpdateAndDowngradeMonitorDto updateAndDowngradeMonitorDto = new UpdateAndDowngradeMonitorDto();
            List<UpdateProductVersion> updateProductVersion = updateProductVersionRepository
                    .findSingleByDeploymentIdOrderByTaskPriority(deploymentId);
            SiteDetails siteDetails = siteDetailsRepository.findByDeploymentId(deploymentId);
            updateAndDowngradeMonitorDto.setSiteId(siteDetails.getSiteId());
            updateAndDowngradeMonitorDto.setTenantId(siteDetails.getTenantId());
            updateAndDowngradeMonitorDto.setTask(convertTaskInFrontendView(updateProductVersion.get(0).getTask()));
            updateAndDowngradeMonitorDtos.add(updateAndDowngradeMonitorDto);
        }
        return updateAndDowngradeMonitorDtos;
    }

    public String convertTaskInFrontendView(Task task) {
        switch (task) {
            case Scheduled:
                return "Scheduled for update";
            case InProgress:
                return "Update in progress";
            case InQueue:
                return "In queue";
            case Completed:
                return "Completed";
            default:
                return "";
        }
    }


}
