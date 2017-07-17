package com.zebpay.demo.dipen.jansari.baseclass;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zebpay.demo.dipen.jansari.animation.AnimatorClass;
import com.zebpay.demo.dipen.jansari.util.Logg;
import com.zebpay.demo.dipen.jansari.util.Util;

/**
 *  Created by dipen on 17/7/17.
 */
public abstract class BaseActivity extends AppCompatActivity implements ActImpMethods {

    public static final String TAG = BaseActivity.class.getSimpleName();
    protected Toolbar toolbar;
    protected ViewDataBinding binding;

    protected void setView(int layoutResId) {
        binding = DataBindingUtil.setContentView(this, layoutResId);
        try {
            initVariable();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            Util.showToast(BaseActivity.this, e.toString());
            Logg.e(TAG, e.toString());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

    }

    protected <T extends ViewDataBinding> T getBinding() {
        return (T) binding;
    }

    /**
     * @param toolbar
     * @param title
     * @param isBackEnabled
     */
    protected void setToolbar(Toolbar toolbar, String title, boolean isBackEnabled) {
        this.toolbar = toolbar;
        super.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle(title);

        if (isBackEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * @param toolbar
     */
    protected void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        super.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    /**
     * use startNewActivity instade of startActivity();
     *
     * @param intent
     */
    public void startNewActivity(Intent intent) {
        //hide keyboard when activity change
        //Util.hideKeyboard(BaseActivity.this);
        startActivity(intent);
        AnimatorClass.appearLeftAnimation(BaseActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*hide keyboard when activity change*/
        //apply animations
        AnimatorClass.disappearLeftAnimation(BaseActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
    }
}
