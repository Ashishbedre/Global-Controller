package com.example.demo.dto;


public class VersionProductDto {

	private String productName;
	private String productVersion;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	
	public VersionProductDto(String productName, String productVersion) {
		super();
		this.productName = productName;
		this.productVersion = productVersion;
	}
	
	public VersionProductDto() {
		super();
	}
	
	
	
	
	
	
	
	
	
}
