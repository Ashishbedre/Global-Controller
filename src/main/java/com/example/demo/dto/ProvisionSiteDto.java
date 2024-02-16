package com.example.demo.dto;

import java.util.List;

public class ProvisionSiteDto {

	private String siteName;
	private List<AddressModel> address;
	private List<PersonModel> personOfContact;
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public List<AddressModel> getAddress() {
		return address;
	}
	public void setAddress(List<AddressModel> address) {
		this.address = address;
	}
	public List<PersonModel> getPersonOfContact() {
		return personOfContact;
	}
	public void setPersonOfContact(List<PersonModel> personOfContact) {
		this.personOfContact = personOfContact;
	}
	
	
	public ProvisionSiteDto(String siteName, List<AddressModel> address, List<PersonModel> personOfContact) {
		super();
		this.siteName = siteName;
		this.address = address;
		this.personOfContact = personOfContact;
	}
	
	
	public ProvisionSiteDto() {
		super();
	}
	
	
}
