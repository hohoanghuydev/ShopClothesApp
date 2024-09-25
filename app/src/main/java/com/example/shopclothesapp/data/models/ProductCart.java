package com.example.shopclothesapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ProductCart implements Parcelable {
    private String productId;
    private int quantity;
    private String size;

    public ProductCart(String productId, int quantity, String size) {
        this.productId = productId;
        this.quantity = quantity;
        this.size = size;
    }

    public ProductCart() {
    }

    protected ProductCart(Parcel in) {
        productId = in.readString();
        quantity = in.readInt();
        size = in.readString();
    }

    public static final Creator<ProductCart> CREATOR = new Creator<ProductCart>() {
        @Override
        public ProductCart createFromParcel(Parcel in) {
            return new ProductCart(in);
        }

        @Override
        public ProductCart[] newArray(int size) {
            return new ProductCart[size];
        }
    };

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCart that = (ProductCart) o;
        return quantity == that.quantity && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeInt(quantity);
        parcel.writeString(size);
    }
}
