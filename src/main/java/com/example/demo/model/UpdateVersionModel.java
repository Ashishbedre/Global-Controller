//package com.example.demo.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name="updated_version_data")
//public class UpdateVersionModel {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id")
//	private Long id;
//	@Column(name="version")
//	private String version;
//	@Column(name="product_name")
//	private String productName;
//	@Column(name="deployment_id")
//	private String deploymentId;
//
//
//	public String getVersion() {
//		return version;
//	}
//	public void setVersion(String version) {
//		this.version = version;
//	}
//	public String getDeploymentId() {
//		return deploymentId;
//	}
//	public void setDeploymentId(String deploymentId) {
//		this.deploymentId = deploymentId;
//	}
//	public String getProductName() {
//		return productName;
//	}
//	public void setProductName(String productName) {
//		this.productName = productName;
//	}
//
//	public UpdateVersionModel(String version, String productName, String deploymentId) {
//		super();
//		this.version = version;
//		this.productName = productName;
//		this.deploymentId = deploymentId;
//	}
//
//	public UpdateVersionModel() {
//		super();
//	}
//
//
//}
