package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.ProductListResponcedto;

import java.util.List;

public interface SiteListService {
    public List<SiteListDto> getAllSiteList(String tenantId);
    public ProvisionSiteDto getSiteList(String deploymentId);
    public UpgradeAndDowngradeDto getListOfVersion(String deploymentId);
    public void UpdateExisitingSite(ProvisionDtoUpdate provisionDto, String deploymentId);
    public void deleteExistingSite(String deploymentId);
    public List<ProductListResponcedto> saveVersionData(List<VersionControlDataModel> list);

    public List<SiteDetailsResponseDTO> getSiteDetailsByTenantId(String tenantId);
    public List<SiteDetailsResponseDTO> getSiteDetailsByTenantId();


}
