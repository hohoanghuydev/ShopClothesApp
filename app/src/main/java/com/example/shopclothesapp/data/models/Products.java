package com.example.shopclothesapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class Products implements Parcelable {
    private String productId;
    private String name;
    private int price;
    private int stock;
    private String description;
    private List<Images> images;
    private String category;
    private List<Sizes> sizes;

    public Products() {
    }

    protected Products(Parcel in) {
        productId = in.readString();
        name = in.readString();
        price = in.readInt();
        stock = in.readInt();
        description = in.readString();
        images = in.createTypedArrayList(Images.CREATOR);

        if(images != null) {
            for(Images image : images) {
                if(image == null || image.getUrl() == null || image.getUrl().isEmpty()) {
                    Log.e("Error Image", "Image is null");
                }
            }
        }

        category = in.readString();
        sizes = in.createTypedArrayList(Sizes.CREATOR);

        if(sizes != null) {
            for(Sizes size : sizes) {
                if(size == null || size.getSize() == null || size.getSize().isEmpty()) {
                    Log.e("Error Size", "Size is null");
                }
            }
        }
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<Sizes> getSizes() {
        return sizes;
    }

    public void setSizes(List<Sizes> sizes) {
        this.sizes = sizes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return price == products.price && stock == products.stock && Objects.equals(productId, products.productId) && Objects.equals(name, products.name) && Objects.equals(description, products.description) && Objects.equals(images, products.images) && Objects.equals(category, products.category) && Objects.equals(sizes, products.sizes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, price, stock, description, images, category, sizes);
    }

    @Override
    public String toString() {
        return "Products{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", category='" + category + '\'' +
                ", sizes=" + sizes +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeInt(stock);
        parcel.writeString(description);
        parcel.writeTypedList(images);
        parcel.writeString(category);
        parcel.writeTypedList(sizes);
    }
}
