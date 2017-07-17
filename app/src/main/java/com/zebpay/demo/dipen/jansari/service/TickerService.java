package com.zebpay.demo.dipen.jansari.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.zebpay.demo.dipen.jansari.R;
import com.zebpay.demo.dipen.jansari.ZebpayApplication;
import com.zebpay.demo.dipen.jansari.db.DBHelper;
import com.zebpay.demo.dipen.jansari.model.TickerModel;
import com.zebpay.demo.dipen.jansari.network.RetrofitClient;
import com.zebpay.demo.dipen.jansari.util.Constant;
import com.zebpay.demo.dipen.jansari.util.Logg;
import com.zebpay.demo.dipen.jansari.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dipen on 7/17/2017.
 * Ticker service will run in background and update the task.
 */

public class TickerService extends IntentService {

    private double oldMarkerValue = 0;

    public TickerService() {
        super("TickerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (Util.isNetworkConnected(this)) {
            callApiForTickerData();
        } else {
            Util.showToast(this, getString(R.string.alert_connect_to_the_internet));
        }
    }


    /**
     * Api call to handle the ticker data.
     */
    public void callApiForTickerData() {

        Call<TickerModel> tickerApiCall = RetrofitClient.getInstance().getApiInterface().getTickerData();
        tickerApiCall.enqueue(new Callback<TickerModel>() {
            @Override
            public void onResponse(@NonNull Call<TickerModel> call, @NonNull Response<TickerModel> response) {
                TickerModel tickerModel = response.body();
                if (tickerModel != null) {

                    showNotification(tickerModel);
                    DBHelper.getInstance(TickerService.this).addTickerData(tickerModel);

                    oldMarkerValue = Double.parseDouble(ZebpayApplication.getInstance().getMarketValue());

                    if (!TextUtils.isEmpty(ZebpayApplication.getInstance().getPercentageValue()) &&
                            Util.getPercentageVariation(oldMarkerValue, tickerModel.getMarket())
                                    >= Double.parseDouble(ZebpayApplication.getInstance().getPercentageValue())) {

                        showPercentageVarianceNotification();
                    }


                    if (!TextUtils.isEmpty(ZebpayApplication.getInstance().getRupeeValue()) &&
                            Util.getRupeeVariation(oldMarkerValue, tickerModel.getMarket())
                                    >= Double.parseDouble(ZebpayApplication.getInstance().getRupeeValue())) {
                        showRupeeVarianceNotification();
                    }


                    ZebpayApplication.getInstance().setMarketValue(String.valueOf(tickerModel.getMarket()));

                    Intent intent = new Intent(Constant.BROADCAST_TICKER_DATA);
                    intent.putExtra(Constant.PARAMS_TICKER_DATA, tickerModel);
                    LocalBroadcastManager.getInstance(TickerService.this).sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TickerModel> call, @NonNull Throwable t) {
                Logg.d("Success Ticker", "0");
                Util.showToast(TickerService.this, t.getMessage());
            }
        });
    }

    /**
     * Showing the notification if variation in rupee we found.
     */
    private void showRupeeVarianceNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_logo)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_logo))
                .setColor(Color.RED)
                .setContentText(getString(R.string.variation_found_in_market_value_rupees))
                .setContentTitle(getString(R.string.zebpay_rate_alert));

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setSound(defaultSoundUri);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(Constant.NOTIFICATION_RUPEE, builder.build());
    }

    /**
     * Showing the notification if variation in percentage we found.
     */
    private void showPercentageVarianceNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_logo)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_logo))
                .setColor(Color.RED)
                .setContentText(getString(R.string.variation_found_in_market_value_rupees))
                .setContentTitle(getString(R.string.zebpay_rate_alert));

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setSound(defaultSoundUri);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(Constant.NOTIFICATION_PERCENTAGE, builder.build());
    }

    /**
     * Showing the notification in every background api call.
     */
    private void showNotification(TickerModel tickerModel) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_logo)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_logo))
                .setColor(Color.RED)
                .setContentText(getString(R.string.buy) +
                        Util.getFormattedString(tickerModel.getBuy()) +
                        getApplicationContext().getString(R.string.rupee) +
                        getString(R.string.seperator) +
                        getString(R.string.sell) +
                        Util.getFormattedString(tickerModel.getSell()) +
                        getApplicationContext().getString(R.string.rupee))
                .setContentTitle(getString(R.string.zebpay_rate_alert));

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setSound(defaultSoundUri);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(Constant.NOTIFICATION_UPADTE, builder.build());
    }
}
