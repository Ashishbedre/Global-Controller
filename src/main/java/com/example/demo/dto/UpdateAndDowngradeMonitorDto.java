package com.example.demo.dto;

import com.example.demo.enums.Task;

public class UpdateAndDowngradeMonitorDto {

    private String tenantId;

    private String siteId;

    private String task;

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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
