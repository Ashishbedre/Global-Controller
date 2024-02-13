package com.example.demo.dto;

import java.util.List;

public class VersionControlDto {

	private String deploymentId;
	private List<VersionAvailableDto> version_control;
	
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public List<VersionAvailableDto> getVersion_control() {
		return version_control;
	}
	public void setVersion_control(List<VersionAvailableDto> version_control) {
		this.version_control = version_control;
	}
	
	
	public VersionControlDto(String deploymentId, List<VersionAvailableDto> version_control) {
		super();
		this.deploymentId = deploymentId;
		this.version_control = version_control;
	}
	
	
	public VersionControlDto() {
		super();
	}

	
    
	
	
	
}
