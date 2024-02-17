package com.example.demo.dto;

import java.util.List;


public class ProvisionSiteSSDto {

	private String siteName;
	private AddressDto address;
	private PersonDto personOfContact;
	private List<VersionSetProductDto> versionControl;

	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public AddressDto getAddress() {
		return address;
	}
	public void setAddress(AddressDto address) {
		this.address = address;
	}
	public PersonDto getPersonOfContact() {
		return personOfContact;
	}
	public void setPersonOfContact(PersonDto personOfContact) {
		this.personOfContact = personOfContact;
	}
	public List<VersionSetProductDto> getVersionControl() {
		return versionControl;
	}
	public void setVersionControl(List<VersionSetProductDto> versionControl) {
		this.versionControl = versionControl;
	}


	public ProvisionSiteSSDto(String siteName, AddressDto address, PersonDto personOfContact,
			List<VersionSetProductDto> versionControl) {
		super();
		this.siteName = siteName;
		this.address = address;
		this.personOfContact = personOfContact;
		this.versionControl = versionControl;
	}


	public ProvisionSiteSSDto() {
		super();
	}





}
