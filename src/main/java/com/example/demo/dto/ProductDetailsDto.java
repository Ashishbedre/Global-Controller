package com.example.demo.dto;

import com.example.demo.enums.Task;

import java.time.LocalDateTime;

public class ProductDetailsDto {

    private String productName;

    private String productVersion;

    private Boolean product_scheduled_update;

    private LocalDateTime product_scheduled_update_dateTime;

    private Task task;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public Boolean getProduct_scheduled_update() {
        return product_scheduled_update;
    }

    public void setProduct_scheduled_update(Boolean product_scheduled_update) {
        this.product_scheduled_update = product_scheduled_update;
    }

    public LocalDateTime getProduct_scheduled_update_dateTime() {
        return product_scheduled_update_dateTime;
    }

    public void setProduct_scheduled_update_dateTime(LocalDateTime product_scheduled_update_dateTime) {
        this.product_scheduled_update_dateTime = product_scheduled_update_dateTime;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
