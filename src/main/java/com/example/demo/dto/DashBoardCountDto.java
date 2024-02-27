package com.example.demo.dto;

public class DashBoardCountDto {

    private  Long tenants;
    private  Long activeSite;
    private Long  siteProvisionalTrue;
    private Long  siteProvisionalFalse;

    public Long getTenants() {
        return tenants;
    }

    public void setTenants(Long tenants) {
        this.tenants = tenants;
    }

    public Long getActiveSite() {
        return activeSite;
    }

    public void setActiveSite(Long activeSite) {
        this.activeSite = activeSite;
    }

    public Long getSiteProvisionalTrue() {
        return siteProvisionalTrue;
    }

    public void setSiteProvisionalTrue(Long siteProvisionalTrue) {
        this.siteProvisionalTrue = siteProvisionalTrue;
    }

    public Long getSiteProvisionalFalse() {
        return siteProvisionalFalse;
    }

    public void setSiteProvisionalFalse(Long siteProvisionalFalse) {
        this.siteProvisionalFalse = siteProvisionalFalse;
    }
}
