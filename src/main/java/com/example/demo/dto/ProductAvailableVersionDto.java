package com.example.demo.dto;

import com.example.demo.dto.BackendPackage.VersionInformation;

import java.util.List;

public class ProductAvailableVersionDto {

    private String product_name;
    private String product_current_version;
    private List<VersionInformation> product_upgrade_available_version;
    private List<VersionInformation> product_downgrade_available_version;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_current_version() {
        return product_current_version;
    }

    public void setProduct_current_version(String product_current_version) {
        this.product_current_version = product_current_version;
    }

    public List<VersionInformation> getProduct_upgrade_available_version() {
        return product_upgrade_available_version;
    }

    public void setProduct_upgrade_available_version(List<VersionInformation> product_upgrade_available_version) {
        this.product_upgrade_available_version = product_upgrade_available_version;
    }

    public List<VersionInformation> getProduct_downgrade_available_version() {
        return product_downgrade_available_version;
    }

    public void setProduct_downgrade_available_version(List<VersionInformation> product_downgrade_available_version) {
        this.product_downgrade_available_version = product_downgrade_available_version;
    }
}
