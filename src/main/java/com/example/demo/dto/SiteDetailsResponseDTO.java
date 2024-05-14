package com.example.demo.dto;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.Person;

public class SiteDetailsResponseDTO {
    private String siteName;
    private AddressDto address;
    private PersonDto personOfContact;

    public SiteDetailsResponseDTO(String siteName, AddressDto address, PersonDto personOfContact) {
        this.siteName = siteName;
        this.address = address;
        this.personOfContact = personOfContact;
    }

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
