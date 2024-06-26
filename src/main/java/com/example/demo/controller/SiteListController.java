package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.ProductListResponcedto;
import com.example.demo.service.SiteListService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin
@PreAuthorize("hasAnyRole('client_user', 'client_admin')")
public class SiteListController {


    @Autowired
    SiteListService siteListService;

    // To get the details of all the sites in the site list of the provision site list.
    @GetMapping("/provision/site_list/get_all_provisioned_site/tenant_id={tenant_id}")
    public List<SiteListDto> getAllSite(@PathVariable("tenant_id") String tenantId) {
        return siteListService.getAllSiteList(tenantId);
    }

    // To get the list of all sites in the provision list of site list
    @GetMapping("/provision/site_list/get_site_details/deployment_id={deployment_id}")
    public ProvisionSiteDto getSiteList(@PathVariable("deployment_id") String deploymentId) {
        return siteListService.getSiteList(deploymentId);
    }

    // To get the list of all site version in the provision list of site list
    @GetMapping("/provision/site_list/get_version_control_provisioned_site/deployment_id={deployment_id}")
    public UpgradeAndDowngradeDto getVersionData(@PathVariable("deployment_id") String deploymentId) {
        return siteListService.getListOfVersion(deploymentId);
    }

    // To update the details in the sites according to the deployment id of the site.
    @PutMapping("/provision/site_list/update_site_details/deployment_id={deployment_id}")
    public void updateExistingSite(@RequestBody ProvisionDtoUpdate provisionDto, @PathVariable("deployment_id") String deploymentId) {
        siteListService.UpdateExisitingSite(provisionDto, deploymentId);
    }

    // To delete the sites of the deployment id.
    @DeleteMapping("/provision/site_list/delete_site/deployment_id={deployment_id}")
    public void deleteExistingSite(@PathVariable("deployment_id") String deploymentId) {
        siteListService.deleteExistingSite(deploymentId);
    }

    // To save the details of the version in the site list.
    @PostMapping("/provision/site_list/post_set_version_provisioned")
    public List<ProductListResponcedto> saveVersionData(@RequestBody List<VersionControlDataModel> versionControlDataModel) {
        return siteListService.saveVersionData(versionControlDataModel);
    }

//     to get the list of SiteName by tenant name
    @GetMapping("/provision/site_list/TenantName={tenant_name}")
    public  List<SiteDetailsResponseDTO>  getSiteName(@PathVariable("tenant_name") String tenantName) {
        return siteListService.getSiteDetailsByTenantId(tenantName);
    }

    //     to get the list of SiteName by tenant name
    @GetMapping("/provision/site_list/get_all_site_name")
    public  List<SiteDetailsResponseDTO>  getSiteName() {
        return siteListService.getSiteDetailsByTenantId();
    }


}
