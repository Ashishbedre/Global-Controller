package com.example.demo.dto;


public class AddressDto {

	private String streetName;
	private String city;
	private String pinCode;
	private String state;
	private String country;

	private long latitude;
	private long longitude;
	
	
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

	public AddressDto(String streetName, String city, String pinCode, String state, String country,long longitude) {
		super();
		this.streetName = streetName;
		this.city = city;
		this.pinCode = pinCode;
		this.state = state;
		this.country = country;
		this.longitude = longitude;
	}
	
	
	public AddressDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
