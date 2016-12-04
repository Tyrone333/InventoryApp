package com.example.android.inventoryapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tyrone3 on 03.12.16.
 */

public class Product implements Parcelable {

    private String mName;
    private int mQuantity;
    private int mPrice;
    private byte[] mImage;

    public Product(String name, int quantity, int price, byte[] image) {
        mName = name;
        mQuantity = quantity;
        mPrice = price;
        mImage = image;
    }

    protected Product(Parcel in) {
        mName = in.readString();
        mQuantity = in.readInt();
        mPrice = in.readInt();
        mImage = in.createByteArray();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return mName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public int getPrice() {
        return mPrice;
    }

    public byte[] getImage() {
        return mImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeInt(mQuantity);
        parcel.writeInt(mPrice);
        parcel.writeByteArray(mImage);
    }
}
