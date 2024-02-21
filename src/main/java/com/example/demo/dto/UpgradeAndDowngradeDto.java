package com.example.demo.dto;

import java.util.List;

public class UpgradeAndDowngradeDto {

    private String deploymentId;

    List<ProductAvailableVersionDto>  product_list;

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public List<ProductAvailableVersionDto> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ProductAvailableVersionDto> product_list) {
        this.product_list = product_list;
    }
}
