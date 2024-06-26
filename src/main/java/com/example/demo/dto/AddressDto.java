package com.example.demo.dto;


public class AddressDto {

	private String streetName;
	private String city;
	private String pinCode;
	private String state;
	private String country;

	private double latitude;
	private double longitude;
	
	
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public AddressDto(String streetName, String city, String pinCode, String state, String country, double longitude, double latitude) {
		super();
		this.streetName = streetName;
		this.city = city;
		this.pinCode = pinCode;
		this.state = state;
		this.country = country;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	
	public AddressDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
