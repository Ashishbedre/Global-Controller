package com.example.demo.dto;

import java.util.List;

public class UpdateAgentDataSaveDto {

    private String deploymentId;

    private String tenantId;

    List<ProductDto> productDetails;

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

    public List<ProductDto> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDto> productDetails) {
        this.productDetails = productDetails;
    }
}
