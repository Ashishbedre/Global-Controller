package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.ProductListResponcedto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AddSiteService {
    public List<TenantDto> getListOfTenant();

    public List<VersionAddSiteDto> getAllDataProduct(String tenantId);

    public List<ProductListResponcedto> saveAddNewSiteData(ProvisionSiteSSDto provisionDto, String deploymentId, String tenantId);
    public VersionUpdateControlDto getListOfUpdatedVersion(String deploymentId);

//    public boolean checkCompatible();
}
