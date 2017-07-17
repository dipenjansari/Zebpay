package com.zebpay.demo.dipen.jansari.baseclass;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *  Created by dipen on 17/7/17.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public BaseViewHolder(View itemView) {
        super(itemView);
        setBinding(itemView);
    }

    public <T extends ViewDataBinding> T getBinding() {
        return (T) binding;
    }

    private void setBinding(View itemView) {
        binding = DataBindingUtil.bind(itemView);
    }
}