//package com.example.demo.service;
//
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.example.demo.dto.AddSiteListDto;
//import com.example.demo.dto.DeploymentIdDto;
//import com.example.demo.dto.ProvisionDtoUpdate;
//import com.example.demo.dto.ProvisionSiteDto;
//import com.example.demo.dto.ProvisionSiteSSDto;
//import com.example.demo.dto.SiteListDto;
//import com.example.demo.dto.VersionControlDto;
//import com.example.demo.dto.VersionUpdateControlDto;
//import com.example.demo.dto.BackendPackage.DockerImageVersionDto;
//import com.example.demo.model.VersionControlDataModel;
//
//public interface frontendService{
//
//	public VersionUpdateControlDto getListOfVersion(String deploymentId);
//
//	public void saveAddNewSiteData(ProvisionSiteSSDto provisionDto,String deploymentId,String tenantId);
//
//	public void UpdateExisitingSite(ProvisionDtoUpdate provisionDto,String deploymentId);
//
//	public void deleteExistingSite(String deploymentId);
//
//	public List<DeploymentIdDto> getDeploymentId(String tenantId);
//
//	public ProvisionSiteDto getSiteList(String deploymentId);
//
//	public void saveVersionData(List<VersionControlDataModel> list);
//
//	public List<SiteListDto> getAllSiteList(String tenantId);
//
//	public AddSiteListDto getAddSiteDetails(String deploymentId);
//
//	public List<VersionControlDataModel> getVersionInfo(String deploymentId);
//
//	public VersionUpdateControlDto getListOfUpdatedVersion(String deploymentId);
//
//}
