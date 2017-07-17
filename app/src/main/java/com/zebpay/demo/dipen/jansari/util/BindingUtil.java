package com.zebpay.demo.dipen.jansari.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zebpay.demo.dipen.jansari.baseclass.BaseViewHolder;

/**
 * Created by dipen on 17/7/17.
 * This class is useful in data binding.
 */

public class BindingUtil {

    @BindingAdapter({"bind:adapter"})
    public static void bind(RecyclerView view, RecyclerView.Adapter<BaseViewHolder> adapter) {
        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        view.setAdapter(adapter);
    }

}

