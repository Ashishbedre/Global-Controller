package com.example.demo.dto;

import java.util.List;

public class ProvisionSiteDto {

	private String siteName;
	private AddressModel address;
	private PersonModel personOfContact;
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public AddressModel getAddress() {
		return address;
	}
	public void setAddress(AddressModel address) {
		this.address = address;
	}
	public PersonModel getPersonOfContact() {
		return personOfContact;
	}
	public void setPersonOfContact(PersonModel personOfContact) {
		this.personOfContact = personOfContact;
	}
	
	
	public ProvisionSiteDto(String siteName, AddressModel address, PersonModel personOfContact) {
		super();
		this.siteName = siteName;
		this.address = address;
		this.personOfContact = personOfContact;
	}
	
	
	public ProvisionSiteDto() {
		super();
	}
	
	
}
