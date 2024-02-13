package com.example.demo.dto;

import java.util.List;

public class ProvisionDtoUpdate {

	private String siteName;
	private List<AddressDto> address;
	private List<PersonDto> personOfContact;
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public List<AddressDto> getAddress() {
		return address;
	}
	public void setAddress(List<AddressDto> address) {
		this.address = address;
	}
	public List<PersonDto> getPersonOfContact() {
		return personOfContact;
	}
	public void setPersonOfContact(List<PersonDto> personOfContact) {
		this.personOfContact = personOfContact;
	}
	
	public ProvisionDtoUpdate(String siteName, List<AddressDto> address, List<PersonDto> personOfContact) {
		super();
		this.siteName = siteName;
		this.address = address;
		this.personOfContact = personOfContact;
	}
	
	public ProvisionDtoUpdate() {
		super();
	}
	
	
	
}
