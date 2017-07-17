package com.zebpay.demo.dipen.jansari.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.zebpay.demo.dipen.jansari.R;
import com.zebpay.demo.dipen.jansari.baseclass.BaseActivity;
import com.zebpay.demo.dipen.jansari.util.Constant;

public class SplashActivity extends BaseActivity {

    private Handler myHandler;
    private Runnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_splash);
    }

    @Override
    public void initVariable() {

        myHandler = new Handler();
        myHandler.postDelayed(myRunnable = new Runnable() {
            @Override
            public void run() {
                //Start Home activity
                startNewActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        }, Constant.SPLASH_TIMEOUT);
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeCallbacks(myRunnable);
        }
    }
}
