package com.zebpay.demo.dipen.jansari;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.zebpay.demo.dipen.jansari.util.Constant;

import io.fabric.sdk.android.Fabric;

/**
 * Created by dipen on 17/7/17.
 * <p>
 * Activity is loaded at the time of application launch.
 */

public class ZebpayApplication extends Application {

    private static ZebpayApplication context;
    private SharedPreferences sharedPreferences;

    public synchronized static ZebpayApplication getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = this;
        sharedPreferences = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
    }

    ///////////////////////SAVE MARKET VALUE//////////////////////////////////
    public String getMarketValue() {
        return sharedPreferences.getString(Constant.MARKET_VALUE, "");
    }

    public void setMarketValue(String marketValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.MARKET_VALUE, marketValue);
        editor.apply();
    }

    ////////////////////SAVE PERCENTAGE VALUE////////////////////////////////
    public String getPercentageValue() {
        return sharedPreferences.getString(Constant.VARIANCE_PERCENTAGE, "");
    }

    public void setPercentageValue(String marketValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.VARIANCE_PERCENTAGE, marketValue);
        editor.apply();
    }

    ///////////////////////SAVE RUPEE VALUE///////////////////////////
    public String getRupeeValue() {
        return sharedPreferences.getString(Constant.VARIANCE_RUPEE, "");
    }

    public void setRupeeValue(String marketValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.VARIANCE_RUPEE, marketValue);
        editor.apply();
    }
}
