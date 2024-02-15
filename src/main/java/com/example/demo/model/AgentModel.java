//package com.example.demo.model;
//
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name="agent_data")
//public class AgentModel {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id")
//	private Long id;
//	@Column(name="deployment_id")
//	private String deploymentId;
//	@Column(name="tenant_id")
//	private String tenantId;
//	@Column(name="site_id")
//	private String siteId;
//	@Column(name="provision")
//	private Boolean provision;
//
//	public String getDeploymentId() {
//		return deploymentId;
//	}
//	public void setDeploymentId(String deploymentId) {
//		this.deploymentId = deploymentId;
//	}
//	public String getTenantId() {
//		return tenantId;
//	}
//	public void setTenantId(String tenantId) {
//		this.tenantId = tenantId;
//	}
//	public String getSiteId() {
//		return siteId;
//	}
//	public void setSiteId(String siteId) {
//		this.siteId = siteId;
//	}
//	public Boolean getProvision() {
//		return provision;
//	}
//	public void setProvision(Boolean provision) {
//		this.provision = provision;
//	}
//	public AgentModel(String deploymentId, String tenantId, String siteId, Boolean provision) {
//		super();
//		this.deploymentId = deploymentId;
//		this.tenantId = tenantId;
//		this.siteId = siteId;
//		this.provision = provision;
//	}
//	public AgentModel() {
//		super();
//	}
//
//
//
//
//
//
//}
