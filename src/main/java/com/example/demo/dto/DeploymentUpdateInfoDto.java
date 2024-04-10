package com.example.demo.dto;

public class DeploymentUpdateInfoDto {
    private Boolean provisionStatus;
    private SiteDetailsDto siteDetails;
    private UpdateDto update;

    public Boolean getProvisionStatus() {
        return provisionStatus;
    }

    public void setProvisionStatus(Boolean provisionStatus) {
        this.provisionStatus = provisionStatus;
    }

    public SiteDetailsDto getSiteDetails() {
        return siteDetails;
    }

    public void setSiteDetails(SiteDetailsDto siteDetails) {
        this.siteDetails = siteDetails;
    }

    public UpdateDto getUpdate() {
        return update;
    }

    public void setUpdate(UpdateDto update) {
        this.update = update;
    }
}
