package com.example.demo.dto.BackendPackage;

public class DockerImageVersionDto {

	private String product;
	private String version;
	
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
	public DockerImageVersionDto(String product, String version) {
		super();
		this.product = product;
		this.version = version;
	}
	
	
	public DockerImageVersionDto() {
		super();
	}
	
	
	
	
	
	
	
}
