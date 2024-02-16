//package com.example.demo.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.dto.ProvisionDtoUpdate;
//import com.example.demo.dto.ProvisionSiteDto;
//import com.example.demo.dto.ProvisionSiteSSDto;
//import com.example.demo.dto.SiteListDto;
//import com.example.demo.dto.TenantDto;
//import com.example.demo.dto.VersionAddSiteDto;
//import com.example.demo.dto.VersionUpdateControlDto;
//import com.example.demo.model.AgentModel;
//import com.example.demo.model.UpdateVersionModel;
//import com.example.demo.dto.VersionControlDataModel;
//import com.example.demo.dto.VersionModel;
//import com.example.demo.service.backendService;
//import com.example.demo.service.frontendService;
//
//
//@RestController
//@RequestMapping("/v1")
//@CrossOrigin
//public class FrontendController {
//
//
//	@Autowired
//	backendService backendService;
//
//
//	@Autowired
//	frontendService service;
//
//
////	// To get the tenant of the list
////	@GetMapping("/get_tenant")
////	public List<TenantDto> getAllTenantId() {
////		return backendService.getListOfTenant();
////	}
//
//
//	// To save the version information of the sites
//	@PostMapping("/updater-agent/save_version_info")
//	public void saveVersionData1(@RequestBody List<VersionModel> model) {
//		backendService.saveVersionData(model);
//	}
//
//
//	// To save the details of the agent
//	@PostMapping("/updater-agent/provision_request")
//	public void saveAgentData(@RequestBody AgentModel agentModel) {
//		backendService.saveAgentData(agentModel);
//	}
//
//	// To save the information of the version info
//	@PostMapping("/save_update_product_info")
//	public void saveUpdatedVersionInfo(@RequestBody List<UpdateVersionModel> versionModel) {
//		backendService.updatedVersionInfo(versionModel);
//	}
//
//	// ***********Add-Site*************************
////	Ashish comment
////	// To get the version information of all the sites in the add site
////	@GetMapping("/provision/add_site/get_all_unprovisioned_site/tenant_id={tenant_id}")
////	public List<VersionAddSiteDto> getAllversionInfo(@PathVariable("tenant_id") String tenantId) {
////		return backendService.getAllDataProduct(tenantId);
////	}
//
////	Ashish comment
////	// To get the list of all available version for in the sites
////	@GetMapping("/provision/add_site/get_version_control_unprovisioned_site/deployment_id={deployment_id}")
////	public VersionUpdateControlDto getListOfSites(@PathVariable("deployment_id") String deploymentId) {
////		return service.getListOfUpdatedVersion(deploymentId);
////	}
//
////	Ashish comment
////	// To add a new site in the provision list
////	@PostMapping("/provision/add_site/post_add_site/deployment_id={deployment_id}/tenant_id={tenant_id}")
////	public void saveAddNewSiteData(@RequestBody ProvisionSiteSSDto provisionDto,@PathVariable("deployment_id") String deploymentId, @PathVariable("tenant_id") String tenantId) {
////		service.saveAddNewSiteData(provisionDto, deploymentId, tenantId);
////	}
//
//	// To Get the deployment id
////	@GetMapping("/get_deployment/tenant_id={tenant_id}")
////	public List<DeploymentIdDto> getDeploymentId(@PathVariable("tenant_id") String tenantId) {
////		return service.getDeploymentId(tenantId);
////	}
//
//	// ***************Site-List*************************** //
//
//	// To get the data of all the site available in the site list
////	@GetMapping("/provision/site_list/get_all_provisioned_site/tenant_id={tenant_id}")
////	public List<ProvisionDto> getAllData(@PathVariable("tenant_id") String tenantId) {
////		return backendService.getAllData(tenantId);
////	}
//
////	Ashish comment
//// 	To get the details of all the sites in the site list of the provision site list.
////	@GetMapping("/provision/site_list/get_all_provisioned_site/tenant_id={tenant_id}")
////	public List<SiteListDto> getAllSite(@PathVariable("tenant_id") String tenantId) {
////		return service.getAllSiteList(tenantId);
////	}
//
////Ashish comment
////	// To get the list of all sites in the provision list of site list
////	@GetMapping("/provision/site_list/get_site_details/deployment_id={deployment_id}")
////	public ProvisionSiteDto getSiteList(@PathVariable("deployment_id") String deploymentId) {
////		return service.getSiteList(deploymentId);
////	}
//
////	Ashish comment
////	// To update the details in the sites according to the deployment id of the site.
////	@PutMapping("/provision/site_list/update_site_details/deployment_id={deployment_id}")
////	public void updateExistingSite(@RequestBody ProvisionDtoUpdate provisionDto,@PathVariable("deployment_id") String deploymentId) {
////		service.UpdateExisitingSite(provisionDto, deploymentId);
////	}
//
////	Ashish comment
////	// To get the list of all site version in the provision list of site list
////	@GetMapping("/provision/site_list/get_version_control_provisioned_site/deployment_id={deployment_id}")
////	public VersionUpdateControlDto getVersionData(@PathVariable("deployment_id") String deploymentId) {
////		return service.getListOfVersion(deploymentId);
////	}
//
////	Ashish comment
////	// To save the details of the version in the site list.
////	@PostMapping("/provision/site_list/post_set_version_provisioned")
////	public void saveVersionData(@RequestBody List<VersionControlDataModel> versionControlDataModel) {
////		service.saveVersionData(versionControlDataModel);
////	}
//
////	Ashish comment
////	// To delete the sites of the deployment id.
////	@DeleteMapping("/provision/site_list/delete_site/deployment_id={deployment_id}")
////	public void deleteExistingSite(@PathVariable("deployment_id") String deploymentId) {
////		service.deleteExistingSite(deploymentId);
////	}
//
//	// To save the details of the version in the site list.
//	@GetMapping("/provision/site_list/get_set_version_provisioned/deployment_id={deployment_id}")
//	public List<VersionControlDataModel> getVersionDataInfo(@PathVariable ("deployment_id") String deploymentId) {
//
//		return service.getVersionInfo(deploymentId);
//	}
//
//}
