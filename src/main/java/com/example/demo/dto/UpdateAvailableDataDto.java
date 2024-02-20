package com.example.demo.dto;

import java.util.List;

public class UpdateAvailableDataDto {

    private String deploymentId;

    private String tenantId;

    List<ProductDetailsDto> productDetails;

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

    public List<ProductDetailsDto> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetailsDto> productDetails) {
        this.productDetails = productDetails;
    }
}
