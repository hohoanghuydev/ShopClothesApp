package com.example.shopclothesapp.data.models;

import java.util.List;
import java.util.Objects;

public class Orders {
    private String orderId;
    private List<ProductCart> products;
    private String status;
    private long totalPrice;
    private String timestamp;
    private String phoneNumber;
    private String address;

    public Orders() {
    }

    public Orders(String orderId, List<ProductCart> products, String status, long totalPrice, String timestamp, String phoneNumber, String address) {
        this.orderId = "order" + orderId;
        this.products = products;
        this.status = status;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<ProductCart> getProducts() {
        return products;
    }

    public void setProducts(List<ProductCart> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return totalPrice == orders.totalPrice && Objects.equals(orderId, orders.orderId) && Objects.equals(products, orders.products) && Objects.equals(status, orders.status) && Objects.equals(timestamp, orders.timestamp) && Objects.equals(phoneNumber, orders.phoneNumber) && Objects.equals(address, orders.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, products, status, totalPrice, timestamp, phoneNumber, address);
    }
}
