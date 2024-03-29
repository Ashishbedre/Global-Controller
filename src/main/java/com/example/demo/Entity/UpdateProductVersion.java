package com.example.demo.Entity;

import com.example.demo.enums.Task;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UpdateProductVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deploymentId;

    private String productName;

    private String productVersion;

    private Boolean product_scheduled_update;

    private LocalDateTime product_scheduled_update_dateTime;

    @Enumerated(EnumType.STRING)
    private Task task;

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
