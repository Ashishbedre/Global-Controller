package com.example.demo.service;

import com.example.demo.dto.ProvisionSiteSSDto;
import com.example.demo.dto.TenantDto;
import com.example.demo.dto.VersionAddSiteDto;
import com.example.demo.dto.VersionUpdateControlDto;

import java.util.List;

public interface AddSiteService {
    public List<TenantDto> getListOfTenant();

    public List<VersionAddSiteDto> getAllDataProduct(String tenantId);

    public void saveAddNewSiteData(ProvisionSiteSSDto provisionDto, String deploymentId, String tenantId);
    public VersionUpdateControlDto getListOfUpdatedVersion(String deploymentId);

//    public boolean checkCompatible();
}
