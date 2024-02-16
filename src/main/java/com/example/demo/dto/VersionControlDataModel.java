package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Entity
//@Table(name="version_control_data")
public class VersionControlDataModel {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id")
//	private Long id;
//	@Column(name="product_name")
	private String product_name;
//	@Column(name="product_set_version")
	private String product_set_version;
//	@Column(name="product_scheduled_update")
	private Boolean product_scheduled_update;
//	@Column(name="product_scheduled_update_dateTime")
	private LocalDateTime product_scheduled_update_dateTime;
//	@Column(name="deployment_id")
	private String deploymentId;


	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_set_version() {
		return product_set_version;
	}
	public void setProduct_set_version(String product_set_version) {
		this.product_set_version = product_set_version;
	}
	public Boolean getProduct_scheduled_update() {
		return product_scheduled_update;
	}
	public void setProduct_scheduled_update(Boolean product_scheduled_update) {
		this.product_scheduled_update = product_scheduled_update;
	}
	public LocalDateTime getProduct_scheduled_update_dateTime() {
		return product_scheduled_update_dateTime;
	}
	public void setProduct_scheduled_update_dateTime(LocalDateTime product_scheduled_update_dateTime) {
		this.product_scheduled_update_dateTime = product_scheduled_update_dateTime;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}


	public VersionControlDataModel(String product_name, String product_set_version, Boolean product_scheduled_update,
			LocalDateTime product_scheduled_update_dateTime, String deploymentId) {
		super();
		this.product_name = product_name;
		this.product_set_version = product_set_version;
		this.product_scheduled_update = product_scheduled_update;
		this.product_scheduled_update_dateTime = product_scheduled_update_dateTime;
		this.deploymentId = deploymentId;
	}


	public VersionControlDataModel() {
		super();
	}





}
