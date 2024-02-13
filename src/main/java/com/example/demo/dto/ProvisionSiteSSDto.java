package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.AddressModel;
import com.example.demo.model.PersonModel;
import com.example.demo.model.VersionModel;


public class ProvisionSiteSSDto {

	private String siteName;
	private List<AddressDto> address;
	private List<PersonDto> personOfContact;
	private List<VersionSetProductDto> versionControl;
	
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
	public List<VersionSetProductDto> getVersionControl() {
		return versionControl;
	}
	public void setVersionControl(List<VersionSetProductDto> versionControl) {
		this.versionControl = versionControl;
	}
	
	
	public ProvisionSiteSSDto(String siteName, List<AddressDto> address, List<PersonDto> personOfContact,
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
