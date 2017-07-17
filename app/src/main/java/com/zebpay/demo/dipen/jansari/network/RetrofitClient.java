package com.zebpay.demo.dipen.jansari.network;

import com.zebpay.demo.dipen.jansari.BuildConfig;
import com.zebpay.demo.dipen.jansari.util.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dipen on 17/7/17.
 */
public class RetrofitClient {

    private static RetrofitClient clientInstance;
    private ApiService apiInterface;

    private RetrofitClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

        apiInterface = retrofit.create(ApiService.class);
    }

    public synchronized static RetrofitClient getInstance() {
        if (clientInstance == null) {
            clientInstance = new RetrofitClient();
        }
        return clientInstance;
    }

    public ApiService getApiInterface() {
        return apiInterface;
    }

    private OkHttpClient getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(Constant.RETROFIT_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        if (BuildConfig.IsLogVisible) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        httpClient.addInterceptor(logging);
        return httpClient.build();
    }
}