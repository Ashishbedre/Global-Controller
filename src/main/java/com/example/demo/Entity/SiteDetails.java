package com.example.demo.Entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class SiteDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deploymentId;

    private String tenantId;

    private String siteId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "site", fetch = FetchType.EAGER,orphanRemoval = true)
    private Address addresses;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "site", fetch = FetchType.EAGER,orphanRemoval = true)
    private Person personsOfContact;

    private Boolean provision;

    private Boolean updateAvailable;

    private Boolean active;

    private LocalDateTime lastSeen;

    private int kafka;

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

    public Address getAddresses() {
        return addresses;
    }

    public void setAddresses(Address addresses) {
        this.addresses = addresses;
    }

    public Person getPersonsOfContact() {
        return personsOfContact;
    }

    public void setPersonsOfContact(Person personsOfContact) {
        this.personsOfContact = personsOfContact;
    }

    public Boolean getProvision() {
        return provision;
    }

    public void setProvision(Boolean provision) {
        this.provision = provision;
    }

    public Boolean getUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(Boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getKafka() {
        return kafka;
    }

    public void setKafka(int kafka) {
        this.kafka = kafka;
    }
}
