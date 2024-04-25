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
	private String country;

	private long latitude;
	private long longitude;




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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public AddressModel(String deploymentId, String streetName, String city, String pinCode, String state, String country,long longitude,long latitude) {
		super();
		this.deploymentId = deploymentId;
		this.streetName = streetName;
		this.city = city;
		this.pinCode = pinCode;
		this.state = state;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	public AddressModel() {
		super();
	}





}
