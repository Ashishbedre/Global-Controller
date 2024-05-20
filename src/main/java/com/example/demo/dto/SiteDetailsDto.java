package com.example.demo.dto;

public class SiteDetailsDto {
    private String siteName;
    private  int kafkaPartition;
    private AddressDto address;
    private PersonDto personOfContact;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getKafkaPartition() {
        return kafkaPartition;
    }

    public void setKafkaPartition(int kafkaPartition) {
        this.kafkaPartition = kafkaPartition;
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
