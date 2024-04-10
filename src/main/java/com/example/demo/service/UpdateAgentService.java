package com.example.demo.service;

import com.example.demo.dto.DeploymentUpdateInfoDto;
import com.example.demo.dto.UpdateAgentDataSaveDto;
import com.example.demo.dto.UpdateAvailableDataDto;

public interface UpdateAgentService {

    void saveDataToSiteDetailsAndCurrentProductVersion(UpdateAgentDataSaveDto updateAgentDataSaveDto);

//    UpdateAvailableDataDto getTheUpdateAvailable(String deploymentId, String tenantName);
    DeploymentUpdateInfoDto getTheUpdateAvailable(String deploymentId, String tenantName);

    boolean saveDataToUpdateVersion(UpdateAvailableDataDto updateAvailableDataDto);
}
