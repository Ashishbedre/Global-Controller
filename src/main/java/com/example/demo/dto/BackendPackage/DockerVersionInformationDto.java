package com.example.demo.dto.BackendPackage;

import java.util.List;

public class DockerVersionInformationDto {

	private String product;
	private List<VersionInformation> versions;
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public List<VersionInformation> getVersions() {
		return versions;
	}
	public void setVersions(List<VersionInformation> versions) {
		this.versions = versions;
	}
	
	public DockerVersionInformationDto(String product, List<VersionInformation> versions) {
		super();
		this.product = product;
		this.versions = versions;
	}
	
	public DockerVersionInformationDto() {
		super();
	}
	
	
	
	
	
}
