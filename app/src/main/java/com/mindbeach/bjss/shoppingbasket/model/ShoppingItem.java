package com.mindbeach.bjss.shoppingbasket.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ken on 22/01/2016.
 */
public class ShoppingItem implements Parcelable{

    private int nameResId;
    private int containerNameResId;
    private int priceInPence;

    // TODO move this out into a separate purchase
    private int amount;

    public ShoppingItem(int nameResId, int containerNameResId, int priceInPence) {
        this.nameResId = nameResId;
        this.containerNameResId = containerNameResId;
        this.priceInPence = priceInPence;
    }

    public ShoppingItem() {
    }

    public void incrementAmount() {
        amount++;
    }

    public void decrementAmount() {
        if (amount > 0)
            amount--;
    }

    public int getNameResId() {
        return nameResId;
    }

    public void setNameResId(int nameResId) {
        this.nameResId = nameResId;
    }

    public int getContainerNameResId() {
        return containerNameResId;
    }

    public void setContainerNameResId(int containerNameResId) {
        this.containerNameResId = containerNameResId;
    }

    public int getPriceInPence() {
        return priceInPence;
    }

    public void setPriceInPence(int priceInPence) {
        this.priceInPence = priceInPence;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "nameResId=" + nameResId +
                ", containerNameResId=" + containerNameResId +
                ", priceInPence=" + priceInPence +
                ", amount=" + amount +
                '}';
    }

    public static final Parcelable.Creator<ShoppingItem> CREATOR = new Parcelable.Creator<ShoppingItem>() {
        public ShoppingItem createFromParcel(Parcel source) {
            return new ShoppingItem(source);
        }

        @Override
        public ShoppingItem[] newArray(int size) {
            return new ShoppingItem[size];
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public ShoppingItem(Parcel in) {
        this.nameResId = in.readInt();
        this.containerNameResId = in.readInt();
        this.priceInPence = in.readInt();
        this.amount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nameResId);
        dest.writeInt(containerNameResId);
        dest.writeInt(priceInPence);
        dest.writeInt(amount);
    }

}

