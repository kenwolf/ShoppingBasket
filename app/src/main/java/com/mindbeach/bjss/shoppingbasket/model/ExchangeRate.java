package com.mindbeach.bjss.shoppingbasket.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * Created by Ken on 23/01/2016.
 * Represents an exchange rate, with 3 letter code and rate.
 * This will normally be populated from parsing fixer.io
 */
public class ExchangeRate implements Parcelable {

    private String code;
    private BigDecimal rate;

    public ExchangeRate(String code, String rate) {
        this.code = code;
        this.rate = new BigDecimal(rate);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return code;
    }


    public static final Parcelable.Creator<ExchangeRate> CREATOR = new Parcelable.Creator<ExchangeRate>() {
        public ExchangeRate createFromParcel(Parcel source) {
            return new ExchangeRate(source);
        }

        @Override
        public ExchangeRate[] newArray(int size) {
            return new ExchangeRate[size];
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public ExchangeRate(Parcel in) {
        this.code = in.readString();
        this.rate = new BigDecimal(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(String.valueOf(rate));
    }
}
