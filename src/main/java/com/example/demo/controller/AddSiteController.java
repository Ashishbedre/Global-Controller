package com.example.demo.controller;


import com.example.demo.dto.ProvisionSiteSSDto;
import com.example.demo.dto.TenantDto;
import com.example.demo.dto.VersionAddSiteDto;
import com.example.demo.dto.VersionUpdateControlDto;
import com.example.demo.service.AddSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin
public class AddSiteController {

    @Autowired
    AddSiteService addSiteService;

    // To get the tenant of the list
    @GetMapping("/get_tenant")
    public List<TenantDto> getAllTenantId() {
        return addSiteService.getListOfTenant();
    }

    // To get the version information of all the sites in the add site
    @GetMapping("/provision/add_site/get_all_unprovisioned_site/tenant_id={tenant_id}")
    public List<VersionAddSiteDto> getAllversionInfo(@PathVariable("tenant_id") String tenantId) {
        return addSiteService.getAllDataProduct(tenantId);
    }

    // To get the list of all available version for in the sites
    @GetMapping("/provision/add_site/get_version_control_unprovisioned_site/deployment_id={deployment_id}")
    public VersionUpdateControlDto getListOfSites(@PathVariable("deployment_id") String deploymentId) {
        return addSiteService.getListOfUpdatedVersion(deploymentId);
    }

    // To add a new site in the provision list
    @PostMapping("/provision/add_site/post_add_site/deployment_id={deployment_id}/tenant_id={tenant_id}")
    public void saveAddNewSiteData(@RequestBody ProvisionSiteSSDto provisionDto, @PathVariable("deployment_id") String deploymentId, @PathVariable("tenant_id") String tenantId) {
        addSiteService.saveAddNewSiteData(provisionDto, deploymentId, tenantId);
    }

}
