package com.zebpay.demo.dipen.jansari.baseclass;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.zebpay.demo.dipen.jansari.R;

/**
 *  Created by dipen on 17/7/17.
 */

public abstract class BaseDialog extends Dialog implements ActImpMethods {

    private static final String TAG = BaseDialog.class.getSimpleName();
    protected ViewDataBinding binding;
    private Context context;

    public BaseDialog(@NonNull Context context, @NonNull int theamResourse) {
        super(context,theamResourse);
        this.context = context;
        context.setTheme(theamResourse);

    }

    protected void setView(int layoutResId) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutResId, null, false);
        //windowSetting();
        setContentView(binding.getRoot());

        getWindow().setLayout((int) context.getResources().getDimension(R.dimen.scale_300dp),
                WindowManager.LayoutParams.WRAP_CONTENT);
        try {
            initVariable();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    protected <T extends ViewDataBinding> T getBinding() {
        return (T) binding;
    }

}
