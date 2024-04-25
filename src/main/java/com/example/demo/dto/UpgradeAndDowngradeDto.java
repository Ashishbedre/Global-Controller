package com.example.demo.dto;

import com.example.demo.enums.Task;

import java.util.List;

public class UpgradeAndDowngradeDto {

    private String deploymentId;

    //Ashish add this for UpgradeAndDowngrade should not be repeated update until the task is finished
    private boolean task;

    List<ProductAvailableVersionDto>  product_list;

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public boolean isTask() {
        return task;
    }

    public void setTask(boolean task) {
        this.task = task;
    }

    public List<ProductAvailableVersionDto> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ProductAvailableVersionDto> product_list) {
        this.product_list = product_list;
    }
}
