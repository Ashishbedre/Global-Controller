package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Entity
//@Table(name="person_data")
public class PersonModel {


//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id")
//	private Long id;
//	@Column(name="full_name")
	private String fullName;
//	@Column(name="contact")
	private String contact;
//	@Column(name="email")
	private String email;
//	@Column(name="deployment_id")
	private String deploymentId;


	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}


	public PersonModel(String fullName, String contact, String email, String deploymentId) {
		super();
		this.fullName = fullName;
		this.contact = contact;
		this.email = email;
		this.deploymentId = deploymentId;
	}


	public PersonModel() {
		super();
	}


}
