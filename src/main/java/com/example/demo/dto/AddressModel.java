package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Entity
//@Table(name="address_data")
public class AddressModel {


//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id")
//	private Long id;
//	@Column(name="deployment_id")
	private String deploymentId;
//	@Column(name="street_name")
	private String streetName;
//	@Column(name="city")
	private String city;
//	@Column(name="pin_code")
	private String pinCode;
//	@Column(name="state")
	private String state;


	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}


	public AddressModel(String deploymentId, String streetName, String city, String pinCode, String state) {
		super();
		this.deploymentId = deploymentId;
		this.streetName = streetName;
		this.city = city;
		this.pinCode = pinCode;
		this.state = state;
	}


	public AddressModel() {
		super();
	}





}
