package com.zebpay.demo.dipen.jansari.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dipen on 17/7/17.
 */

public class TickerModel implements Parcelable {

    public static final Parcelable.Creator<TickerModel> CREATOR = new Parcelable.Creator<TickerModel>() {
        @Override
        public TickerModel createFromParcel(Parcel source) {
            return new TickerModel(source);
        }

        @Override
        public TickerModel[] newArray(int size) {
            return new TickerModel[size];
        }
    };
    private double market;
    private double buy;
    private double sell;
    private String currency;
    private double volume;


    public TickerModel() {
    }

    protected TickerModel(Parcel in) {
        this.market = in.readDouble();
        this.buy = in.readDouble();
        this.sell = in.readDouble();
        this.currency = in.readString();
        this.volume = in.readDouble();
    }

    public double getMarket() {
        return market;
    }

    public double getBuy() {
        return buy;
    }

    public double getSell() {
        return sell;
    }

    public String getCurrency() {
        return currency;
    }

    public double getVolume() {
        return volume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.market);
        dest.writeDouble(this.buy);
        dest.writeDouble(this.sell);
        dest.writeString(this.currency);
        dest.writeDouble(this.volume);
    }
}
