package com.zebpay.demo.dipen.jansari.viewModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.zebpay.demo.dipen.jansari.R;
import com.zebpay.demo.dipen.jansari.ZebpayApplication;
import com.zebpay.demo.dipen.jansari.adapter.FeedAdapter;
import com.zebpay.demo.dipen.jansari.databinding.ActivityHomeBinding;
import com.zebpay.demo.dipen.jansari.db.DBHelper;
import com.zebpay.demo.dipen.jansari.dialog.VarianceByPercDialog;
import com.zebpay.demo.dipen.jansari.dialog.VarianceByRupeeDialog;
import com.zebpay.demo.dipen.jansari.interfaces.ClickListener;
import com.zebpay.demo.dipen.jansari.interfaces.ValueReturnListener;
import com.zebpay.demo.dipen.jansari.model.ActivityFeedData;
import com.zebpay.demo.dipen.jansari.model.ActivityFeedModel;
import com.zebpay.demo.dipen.jansari.model.TickerModel;
import com.zebpay.demo.dipen.jansari.network.ApiService;
import com.zebpay.demo.dipen.jansari.network.RetrofitClient;
import com.zebpay.demo.dipen.jansari.util.Constant;
import com.zebpay.demo.dipen.jansari.util.Logg;
import com.zebpay.demo.dipen.jansari.util.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zebpay.demo.dipen.jansari.R.string.bitcoin;

/**
 * Created by Dipen on 17/7/17.
 * <p>
 * I have used the MVVM structure to represent the code.
 * This view model consist of logic part of the application.
 */

public class HomeViewModel implements ClickListener {

    private FeedAdapter mFeedAdapter;
    private Context mContext;
    private ActivityHomeBinding mHomeBinding;
    private ArrayList<ActivityFeedData> mActivityFeedData;
    private double marketValue = 0;

    public HomeViewModel(Context context, ActivityHomeBinding homeBinding) {
        this.mContext = context;
        this.mHomeBinding = homeBinding;
    }

    /**
     * Api Call for Ticker data
     */
    public void callApiForTickerData() {

        mHomeBinding.contentTicker.progressBar.setVisibility(View.VISIBLE);
        mHomeBinding.contentTicker.imgRefresh.setVisibility(View.INVISIBLE);

        Call<TickerModel> tickerApiCall = RetrofitClient.getInstance().getApiInterface().getTickerData();
        tickerApiCall.enqueue(new Callback<TickerModel>() {
            @Override
            public void onResponse(@NonNull Call<TickerModel> call, @NonNull Response<TickerModel> response) {
                Logg.d("Success Ticker", "1");

                mHomeBinding.contentTicker.progressBar.setVisibility(View.INVISIBLE);
                mHomeBinding.contentTicker.imgRefresh.setVisibility(View.VISIBLE);

                TickerModel tickerModel = response.body();
                DBHelper.getInstance(mContext).addTickerData(tickerModel);

                if (tickerModel != null)
                    updateTickerUI(tickerModel);
            }

            @Override
            public void onFailure(@NonNull Call<TickerModel> call, @NonNull Throwable t) {
                Logg.d("Success Ticker", "0");
                mHomeBinding.contentTicker.progressBar.setVisibility(View.INVISIBLE);
                mHomeBinding.contentTicker.imgRefresh.setVisibility(View.VISIBLE);
                Util.showToast(mContext, t.getMessage());
            }
        });
    }

    /**
     * Api call for feeds data
     *
     * @param isSwipeToRefresh is true if we want to display the progressbar to the user.
     */
    public void callApiForFeedsData(final boolean isSwipeToRefresh) {

        mActivityFeedData = new ArrayList<>();

        if (!isSwipeToRefresh)
            mHomeBinding.progressBar.setVisibility(View.VISIBLE);

        Call<ActivityFeedModel> activityFeedApiCall = RetrofitClient.getInstance().getApiInterface().getActivityFeedData(ApiService.FEED_DATA);
        activityFeedApiCall.enqueue(new Callback<ActivityFeedModel>() {
            @Override
            public void onResponse(Call<ActivityFeedModel> call, Response<ActivityFeedModel> response) {

                ActivityFeedModel activityFeedModel = response.body();

                if (activityFeedModel != null) {
                    mActivityFeedData = activityFeedModel.getActivityFeedList();
                    if (activityFeedModel.getReturncode() == Constant.SUCCESS &&
                            mActivityFeedData.size() > 0) {

                        mHomeBinding.rcvFeeds.setVisibility(View.VISIBLE);
                        mHomeBinding.progressBar.setVisibility(View.GONE);

                        mFeedAdapter = new FeedAdapter(mActivityFeedData, HomeViewModel.this, HomeViewModel.this);
                        mHomeBinding.setFeedAdapter(mFeedAdapter);

                    } else {
                        mHomeBinding.progressBar.setVisibility(View.GONE);
                        mHomeBinding.rcvFeeds.setVisibility(View.GONE);
                        Util.showToast(mContext, mContext.getString(R.string.alert_unable_to_load_data));
                    }
                } else {
                    mHomeBinding.progressBar.setVisibility(View.GONE);
                    mHomeBinding.rcvFeeds.setVisibility(View.GONE);
                    Util.showToast(mContext, mContext.getString(R.string.alert_unable_to_load_data));
                }

                if (isSwipeToRefresh)
                    mHomeBinding.swipeToRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ActivityFeedModel> call, Throwable t) {
                mHomeBinding.progressBar.setVisibility(View.GONE);
                mHomeBinding.rcvFeeds.setVisibility(View.GONE);

                Util.showToast(mContext, t.getMessage());

                if (isSwipeToRefresh)
                    mHomeBinding.swipeToRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * Ticker UI is going to change as per ticker data arrives.
     *
     * @param tickerModel is consist of data from API
     */
    public void updateTickerUI(TickerModel tickerModel) {

        marketValue = tickerModel.getMarket();
        ZebpayApplication.getInstance().setMarketValue(String.valueOf(marketValue));

        mHomeBinding.contentTicker.txtBuyPrice.setText(mContext.getString(R.string.buy_one_bitcoin) +
                mContext.getString(R.string.space) +
                Util.getFormattedString(tickerModel.getBuy()) +
                mContext.getString(R.string.space) +
                mContext.getString(R.string.rupee));

        mHomeBinding.contentTicker.txtSellPrice.setText(mContext.getString(R.string.sell_one_bitcoin) +
                mContext.getString(R.string.space) +
                Util.getFormattedString(tickerModel.getSell()) +
                mContext.getString(R.string.space) +
                mContext.getString(R.string.rupee));

        mHomeBinding.contentTicker.txtVolume.setText(String.valueOf(tickerModel.getVolume() +
                mContext.getString(R.string.space) +
                mContext.getString(bitcoin)));

        mHomeBinding.contentTicker.txtMarketPrice.setText(mContext.getString(R.string.approx) +
                mContext.getString(R.string.space) +
                Util.getFormattedString(tickerModel.getMarket() * tickerModel.getVolume()) +
                mContext.getString(R.string.space) +
                mContext.getString(R.string.rupee));
    }


    /**
     * Open Variance for rupee dialog to get input from User.
     */
    public void openVarianceByRupeeDialog() {

        if (marketValue != 0) {
            VarianceByRupeeDialog varianceByRupeeDialog = new VarianceByRupeeDialog(mContext, new ValueReturnListener() {
                @Override
                public void onValueReturn(double value) {
                    ZebpayApplication.getInstance().setRupeeValue(String.valueOf(value));
                    Util.showToast(mContext, mContext.getString(R.string.msg_variation_in_rupee));
                }
            }, marketValue);
            varianceByRupeeDialog.show();
        }
    }

    /**
     * Open Variance for percentage dialog to get input from User.
     */
    public void openVarianceByPercDialog() {
        VarianceByPercDialog varianceByPercDialog = new VarianceByPercDialog(mContext, new ValueReturnListener() {
            @Override
            public void onValueReturn(double value) {
                ZebpayApplication.getInstance().setPercentageValue(String.valueOf(value));
                Util.showToast(mContext, mContext.getString(R.string.msg_variation_in_percentage));
            }
        });
        varianceByPercDialog.show();
    }

    @Override
    public void onClick(View view) {
        int pos = mHomeBinding.rcvFeeds.getChildLayoutPosition(view);
        int type = mActivityFeedData.get(pos).getActivityType();

        switch (type) {
            case Constant.JOIN:
                Util.showToast(mContext, mContext.getString(R.string.join_message));
                break;

            case Constant.ZEBIAN_SEND:
                Util.showToast(mContext, mContext.getString(R.string.zebian_send_message));
                break;
            case Constant.OTHER_SEND:
                Util.showToast(mContext, mContext.getString(R.string.other_send_message));
                break;
            case Constant.BITCOINT_SEND:
                Util.showToast(mContext, mContext.getString(R.string.bitcoin_send_message));
                break;

            case Constant.MAGIC_MOMENT_SEND:
                Util.showToast(mContext, mContext.getString(R.string.magic_moment_send_message));
                break;

            case Constant.BITCOIN_RECEIVED:
                Util.showToast(mContext, mContext.getString(R.string.bitcoin_received_message));
                break;

            case Constant.BITCOIN_PURCHASED:
                Util.showToast(mContext, mContext.getString(R.string.bitcoin_buy_message));
                break;

            case Constant.VOUCHER_PURCHASED:
                Util.showToast(mContext, mContext.getString(R.string.voucher_purchased_message));
                break;

            case Constant.AIRTIME_PURCHASED:
                Util.showToast(mContext, mContext.getString(R.string.airtime_purchased_message));
                break;

        }

    }
}
