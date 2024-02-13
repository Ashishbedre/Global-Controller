package com.example.demo.dto;


public class PersonDto {

	private String fullName;
	private String contact;
	private String email;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public PersonDto(String fullName, String contact, String email) {
		super();
		this.fullName = fullName;
		this.contact = contact;
		this.email = email;
	}
	public PersonDto() {
		super();
	}
	
	
}
