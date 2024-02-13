package com.example.demo.dto;

import java.util.List;


public class ProvisionDto {

	private String siteName;
	private String deploymentId;
	private List<AddressDto> address;
	private List<PersonDto> personOfContact;
	private List<VersionProductDto> versionControl;
	
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
	public List<VersionProductDto> getVersionControl() {
		return versionControl;
	}
	public void setVersionControl(List<VersionProductDto> versionControl) {
		this.versionControl = versionControl;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
	public ProvisionDto(String siteName, String deploymentId, List<AddressDto> address, List<PersonDto> personOfContact,
			List<VersionProductDto> versionControl) {
		super();
		this.siteName = siteName;
		this.deploymentId = deploymentId;
		this.address = address;
		this.personOfContact = personOfContact;
		this.versionControl = versionControl;
	}
	public ProvisionDto() {
		super();
	}
	
	
	
	
	
	
	
	
}
