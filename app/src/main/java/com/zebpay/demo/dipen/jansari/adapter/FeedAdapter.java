package com.zebpay.demo.dipen.jansari.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.zebpay.demo.dipen.jansari.R;
import com.zebpay.demo.dipen.jansari.baseclass.BaseViewHolder;
import com.zebpay.demo.dipen.jansari.databinding.RowFeedsBinding;
import com.zebpay.demo.dipen.jansari.interfaces.ClickListener;
import com.zebpay.demo.dipen.jansari.model.ActivityFeedData;
import com.zebpay.demo.dipen.jansari.util.CircleTransform;
import com.zebpay.demo.dipen.jansari.util.Constant;
import com.zebpay.demo.dipen.jansari.util.Util;
import com.zebpay.demo.dipen.jansari.viewModel.HomeViewModel;

import java.util.List;

/**
 * Created by dipen on 17/7/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ActivityFeedData> mListFeeds;
    private HomeViewModel mHomeViewModel;
    private ClickListener onClickListener;

    public FeedAdapter(List<ActivityFeedData> mListFeeds, HomeViewModel mHomeViewModel, ClickListener clickListener) {
        this.mListFeeds = mListFeeds;
        this.mHomeViewModel = mHomeViewModel;
        this.onClickListener=clickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feeds, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final ActivityFeedData feedData = mListFeeds.get(position);
        RowFeedsBinding mRowFeedDataBinding = holder.getBinding();

        mRowFeedDataBinding.setActivityFeedData(feedData);
        mRowFeedDataBinding.setHomeViewModel(mHomeViewModel);
        mRowFeedDataBinding.setOnClick(onClickListener);

        if (TextUtils.isEmpty(feedData.getImageURL())) {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(String.valueOf(Util.charAt0(feedData.getName())), Color.BLACK);
            mRowFeedDataBinding.imgFeed.setImageDrawable(drawable);

        } else {
            Glide.with(mRowFeedDataBinding.imgFeed.getContext()).load(feedData.getImageURL()).transform(
                    new CircleTransform(mRowFeedDataBinding.imgFeed.getContext())).crossFade().
                    into(mRowFeedDataBinding.imgFeed);
        }

        if (feedData.getActivityType() == Constant.BITCOIN_PURCHASED) {
            mRowFeedDataBinding.imgFeed.setBackground
                    (ContextCompat.getDrawable(mRowFeedDataBinding.getRoot().getContext(),
                            R.drawable.bg_circle_feed_buy));

            mRowFeedDataBinding.llFeedContain.setBackground
                    (ContextCompat.getDrawable(mRowFeedDataBinding.getRoot().getContext(),
                            R.drawable.bg_rounded_square_feed_buy));

        } else if (feedData.getActivityType() == Constant.BITCOINT_SEND) {
            mRowFeedDataBinding.imgFeed.setBackground
                    (ContextCompat.getDrawable(mRowFeedDataBinding.getRoot().getContext(),
                            R.drawable.bg_circle_feed_sent));

            mRowFeedDataBinding.llFeedContain.setBackground
                    (ContextCompat.getDrawable(mRowFeedDataBinding.getRoot().getContext(),
                            R.drawable.bg_rounded_square_feed_sent));

        } else {
            mRowFeedDataBinding.imgFeed.setBackground
                    (ContextCompat.getDrawable(mRowFeedDataBinding.getRoot().getContext(),
                            R.drawable.bg_circle_feed));

            mRowFeedDataBinding.llFeedContain.setBackground
                    (ContextCompat.getDrawable(mRowFeedDataBinding.getRoot().getContext(),
                            R.drawable.bg_rounded_square_feed));
        }

        mRowFeedDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }

    private class ViewHolder extends BaseViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
