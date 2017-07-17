package com.zebpay.demo.dipen.jansari.util;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.DecimalFormat;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Dipen on 17/7/17.
 */
public class Util {

    /**
     * To check the Internet is there to connect or not.
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        return isConnected;
    }

    /**
     * Methos to display the toast message here.
     *
     * @param context pass the activity context here.
     * @param msg     which you want to display as a toast.
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to get first character from the provided String
     *
     * @param name
     * @return
     */
    public static char charAt0(String name) {
        return name.charAt(0);
    }

    /**
     * Method for converting string value to Decimal format
     *
     * @param value
     * @return
     */
    public static String getFormattedString(double value) {
        return new DecimalFormat("##,##,##,###").format(value);
    }

    /**
     * Get the percentage variation to notify the user.
     *
     * @param oldMarketValue
     * @param newMarketValue
     * @return
     */
    public static double getPercentageVariation(double oldMarketValue, double newMarketValue) {
        return (Math.abs(oldMarketValue - newMarketValue) / oldMarketValue) * 100;
    }

    /**
     * Get the rupee variation to notify the user.
     *
     * @param oldMarketValue
     * @param newMarketValue
     * @return
     */
    public static double getRupeeVariation(double oldMarketValue, double newMarketValue) {
        return Math.abs(oldMarketValue - newMarketValue);
    }

    /**
     * Hide the keyboard.
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}