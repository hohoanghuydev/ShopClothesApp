package com.example.shopclothesapp.data.models;

public class ProductFavorites {
    private String productId;
    private boolean favorite;

    public ProductFavorites(String productId, boolean favorite) {
        this.productId = productId;
        this.favorite = favorite;
    }

    public ProductFavorites() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
