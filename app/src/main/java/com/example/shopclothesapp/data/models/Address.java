package com.example.shopclothesapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Address implements Parcelable {
    private String addressId;
    private String nameStreet;
    private String city;
    private String state;
    private String zipCode;

    public Address(String nameStreet, String city, String state, String zipCode) {
        this.nameStreet = nameStreet;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.addressId = hashCode() + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addressId, address.addressId) && Objects.equals(nameStreet, address.nameStreet) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameStreet, city, state, zipCode);
    }

    public Address() {
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getNameStreet() {
        return nameStreet;
    }

    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    protected Address(Parcel in) {
        addressId = in.readString();
        nameStreet = in.readString();
        city = in.readString();
        state = in.readString();
        zipCode = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(addressId);
        parcel.writeString(nameStreet);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(zipCode);
    }
}
