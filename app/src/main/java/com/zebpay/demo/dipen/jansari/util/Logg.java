package com.zebpay.demo.dipen.jansari.util;

import android.util.Log;

import com.zebpay.demo.dipen.jansari.BuildConfig;

/**
 * Created by dipen on 17/7/17.
 * This is class is for custom log print which we will disable in release build.
 */

public class Logg {
    // TODO: 19/4/16 showLogs false_img when app was publish
    private static final boolean showLogs = BuildConfig.IsLogVisible;

    public static void e(String logTAG, String msg) {
        if (showLogs) {
            Log.e(logTAG, msg);
        }
    }

    public static void d(String logTAG, String msg) {
        if (showLogs) {
            Log.d(logTAG, msg);
        }
    }

    public static void i(String logTAG, String msg) {
        if (showLogs) {
            Log.i(logTAG, msg);
        }
    }

    public static void v(String logTAG, String msg) {
        if (showLogs) {
            Log.v(logTAG, msg);
        }
    }

    public static void w(String logTAG, String msg) {
        if (showLogs) {
            Log.w(logTAG, msg);
        }
    }

    public static void wtf(String logTAG, String msg) {
        if (showLogs) {
            Log.wtf(logTAG, msg);
        }
    }


}
