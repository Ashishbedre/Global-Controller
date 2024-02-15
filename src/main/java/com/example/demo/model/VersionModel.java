package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Entity
//@Table(name="version_data")
public class VersionModel {


//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id")
//	private Long id;
//	@Column(name="product_name")
	private String productName;
//	@Column(name="product_set_version")
	private String productSetVersion;
//	@Column(name="deployment_id")
	private String deploymentId;
//	@Column(name="tenant_id")
	private String tenantId;
//	@Column(name="product_version")
	private String productVersion;


	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSetVersion() {
		return productSetVersion;
	}
	public void setProductSetVersion(String productSetVersion) {
		this.productSetVersion = productSetVersion;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}


	public VersionModel(String productName, String productSetVersion, String deploymentId, String tenantId,
			String productVersion) {
		super();
		this.productName = productName;
		this.productSetVersion = productSetVersion;
		this.deploymentId = deploymentId;
		this.tenantId = tenantId;
		this.productVersion = productVersion;
	}


	public VersionModel() {
		super();
	}






}
