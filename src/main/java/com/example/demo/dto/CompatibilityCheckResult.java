package com.example.demo.dto;

import java.util.List;

public class CompatibilityCheckResult {
    private boolean isCompatible;
    private List<ProductList> productsWithGreaterId;

    public CompatibilityCheckResult(boolean isCompatible, List<ProductList> productsWithGreaterId) {
        this.isCompatible = isCompatible;
        this.productsWithGreaterId = productsWithGreaterId;
    }

    public void setCompatible(boolean compatible) {
        isCompatible = compatible;
    }

    public void setProductsWithGreaterId(List<ProductList> productsWithGreaterId) {
        this.productsWithGreaterId = productsWithGreaterId;
    }

    public boolean isCompatible() {
        return isCompatible;
    }

    public List<ProductList> getProductsWithGreaterId() {
        return productsWithGreaterId;
    }
}