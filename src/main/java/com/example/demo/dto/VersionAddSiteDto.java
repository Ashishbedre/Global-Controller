package com.example.demo.dto;

import java.util.List;

public class VersionAddSiteDto {

	private String deploymentId;
	private List<ProductDto> version;
	
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public List<ProductDto> getVersion() {
		return version;
	}
	public void setVersion(List<ProductDto> version) {
		this.version = version;
	}
	
	
	public VersionAddSiteDto(String deploymentId, List<ProductDto> version) {
		super();
		this.deploymentId = deploymentId;
		this.version = version;
	}
	
	public VersionAddSiteDto() {
		super();
	}
	
	
}
