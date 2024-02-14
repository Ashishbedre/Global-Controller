package com.example.demo.service;

import com.example.demo.dto.ProvisionDtoUpdate;
import com.example.demo.dto.ProvisionSiteDto;
import com.example.demo.dto.SiteListDto;
import com.example.demo.dto.VersionUpdateControlDto;
import com.example.demo.model.VersionControlDataModel;

import java.util.List;

public interface SiteListService {
    public List<SiteListDto> getAllSiteList(String tenantId);
    public ProvisionSiteDto getSiteList(String deploymentId);
    public VersionUpdateControlDto getListOfVersion(String deploymentId);
    public void UpdateExisitingSite(ProvisionDtoUpdate provisionDto, String deploymentId);
    public void deleteExistingSite(String deploymentId);
    public void saveVersionData(List<VersionControlDataModel> list);


}
