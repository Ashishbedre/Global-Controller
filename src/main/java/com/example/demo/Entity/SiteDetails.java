package com.example.demo.Entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class SiteDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deploymentId;

    private String tenantId;

    private String siteId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "site", fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "site", fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Person> personsOfContact;

    private Boolean provision;

    private Boolean available;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Person> getPersonsOfContact() {
        return personsOfContact;
    }

    public void setPersonsOfContact(List<Person> personsOfContact) {
        this.personsOfContact = personsOfContact;
    }

    public Boolean getProvision() {
        return provision;
    }

    public void setProvision(Boolean provision) {
        this.provision = provision;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
