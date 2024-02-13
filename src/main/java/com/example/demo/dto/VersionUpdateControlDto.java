package com.example.demo.dto;

import java.util.List;

public class VersionUpdateControlDto {

	private String deploymentId;
	private List<VersionUpgradeDto> version_control;
	
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public List<VersionUpgradeDto> getVersion_control() {
		return version_control;
	}
	public void setVersion_control(List<VersionUpgradeDto> version_control) {
		this.version_control = version_control;
	}
	
	public VersionUpdateControlDto(String deploymentId, List<VersionUpgradeDto> version_control) {
		super();
		this.deploymentId = deploymentId;
		this.version_control = version_control;
	}
	
	public VersionUpdateControlDto() {
		super();
	}
	
	
}
