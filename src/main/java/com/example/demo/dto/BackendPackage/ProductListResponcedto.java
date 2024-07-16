package com.example.demo.dto.BackendPackage;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ProductList;

import java.util.List;

public class ProductListResponcedto {
    private boolean isCompatible;

    private String wantedToUpdate;
    private List<ProductDto> productsWithGreaterId;

    public boolean isCompatible() {
        return isCompatible;
    }

    public void setCompatible(boolean compatible) {
        isCompatible = compatible;
    }

    public String getWantedToUpdate() {
        return wantedToUpdate;
    }

    public void setWantedToUpdate(String wantedToUpdate) {
        this.wantedToUpdate = wantedToUpdate;
    }

    public List<ProductDto> getProductsWithGreaterId() {
        return productsWithGreaterId;
    }

    public void setProductsWithGreaterId(List<ProductDto> productsWithGreaterId) {
        this.productsWithGreaterId = productsWithGreaterId;
    }
}
