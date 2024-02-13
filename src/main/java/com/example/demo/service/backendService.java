package com.example.demo.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.ProvisionDto;
import com.example.demo.dto.TenantDto;
import com.example.demo.dto.VersionAddSiteDto;
import com.example.demo.dto.BackendPackage.DockerImageVersionDto;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.model.AgentModel;
import com.example.demo.model.UpdateVersionModel;
import com.example.demo.model.VersionModel;

public interface backendService {

	public void saveAgentData(AgentModel agentModel);
	
	public List<TenantDto> getListOfTenant();
	
	public List<ProvisionDto> getAllData(String tenantId);
	
	public List<VersionAddSiteDto> getAllDataProduct(String tenantId);
	
	public void saveVersionData(List<VersionModel> model);
	
	public void updatedVersionInfo(@RequestBody List<UpdateVersionModel> versionModel);
	
//	public List<DockerVersionInformationDto> getUpdatedVersionInformation(DockerImageVersionDto versionDto );
	
//	public List<DockerVersionInformationDto> getUpdatedVersionInform(DockerImageVersionDto versionDto );
	
	public List<DockerVersionInformationDto> getTwoUpdatedData(DockerImageVersionDto versionDto);
}
