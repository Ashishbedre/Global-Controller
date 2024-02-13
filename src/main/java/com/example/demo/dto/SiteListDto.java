package com.example.demo.dto;

import java.util.List;

public class SiteListDto {

	private String deploymentId;
	private String siteName;
	private String city;
	private List<VersionProductDto> versionControl;
	
	
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<VersionProductDto> getVersionControl() {
		return versionControl;
	}
	public void setVersionControl(List<VersionProductDto> versionControl) {
		this.versionControl = versionControl;
	}
	
	
	public SiteListDto(String deploymentId, String siteName, String city, List<VersionProductDto> versionControl) {
		super();
		this.deploymentId = deploymentId;
		this.siteName = siteName;
		this.city = city;
		this.versionControl = versionControl;
	}
	
	
	public SiteListDto() {
		super();
	}
	
	
	
}
