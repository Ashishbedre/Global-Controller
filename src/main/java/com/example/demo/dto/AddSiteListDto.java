package com.example.demo.dto;

import java.util.List;

public class AddSiteListDto {

	private String siteName;
	private String deploymentId;
	private AddressDto address;
	private PersonDto personOfContact;
	private List<VersionProductDto> versionControl;
	
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
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
	public List<VersionProductDto> getVersionControl() {
		return versionControl;
	}
	public void setVersionControl(List<VersionProductDto> versionControl) {
		this.versionControl = versionControl;
	}
	
	
	public AddSiteListDto(String siteName, String deploymentId, AddressDto address, PersonDto personOfContact,
			List<VersionProductDto> versionControl) {
		super();
		this.siteName = siteName;
		this.deploymentId = deploymentId;
		this.address = address;
		this.personOfContact = personOfContact;
		this.versionControl = versionControl;
	}
	
	
	public AddSiteListDto() {
		super();
	}
	
	
	
	
}
