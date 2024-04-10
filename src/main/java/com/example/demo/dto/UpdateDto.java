package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateDto {
    private Boolean updateAvailable;
    private List<ProductDto> products;
    private Boolean scheduledUpdate;
    private LocalDateTime updateDateTime;

    public Boolean getUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(Boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public Boolean getScheduledUpdate() {
        return scheduledUpdate;
    }

    public void setScheduledUpdate(Boolean scheduledUpdate) {
        this.scheduledUpdate = scheduledUpdate;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
