package com.example.demo.dto;

public class SiteDetailsDto {
    private String siteName;
    private AddressDto address;
    private PersonDto personOfContact;

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
}
