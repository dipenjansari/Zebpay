package com.zebpay.demo.dipen.jansari.network;


import com.zebpay.demo.dipen.jansari.model.ActivityFeedModel;
import com.zebpay.demo.dipen.jansari.model.TickerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by dipen on 17/7/17.
 */
public interface ApiService {

    String BASE_URL = "https://api.zebpay.com/api/v1/";
    String TICKER_DATA = "ticker?currencyCode=INR";
    String FEED_DATA = "https://www.zebapi.com/api/v1/feed";

    @GET(TICKER_DATA)
    Call<TickerModel> getTickerData();

    @GET
    Call<ActivityFeedModel> getActivityFeedData(@Url String url);
}


