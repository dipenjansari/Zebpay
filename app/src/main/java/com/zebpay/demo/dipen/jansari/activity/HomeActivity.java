package com.zebpay.demo.dipen.jansari.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.zebpay.demo.dipen.jansari.R;
import com.zebpay.demo.dipen.jansari.baseclass.BaseActivity;
import com.zebpay.demo.dipen.jansari.databinding.ActivityHomeBinding;
import com.zebpay.demo.dipen.jansari.interfaces.ClickListener;
import com.zebpay.demo.dipen.jansari.model.TickerModel;
import com.zebpay.demo.dipen.jansari.service.CustomService;
import com.zebpay.demo.dipen.jansari.util.Constant;
import com.zebpay.demo.dipen.jansari.util.Util;
import com.zebpay.demo.dipen.jansari.viewModel.HomeViewModel;

/**
 * Created by dipen on 17/7/17.
 */
public class HomeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ClickListener {

    private ActivityHomeBinding mHomeBinding;
    private HomeViewModel mHomeViewModel;

    private BroadcastReceiver tickerData = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                TickerModel tickerModel = bundle.getParcelable(Constant.PARAMS_TICKER_DATA);
                mHomeViewModel.updateTickerUI(tickerModel);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(tickerData, new IntentFilter(Constant.BROADCAST_TICKER_DATA));
    }

    @Override
    public void initVariable() {
        mHomeBinding = getBinding();
        mHomeBinding.contentTicker.setOnClick(this);
        setToolbar(mHomeBinding.toolbar.toolbar, getString(R.string.title_home_activity), false);

        mHomeBinding.swipeToRefresh.setOnRefreshListener(this);
        mHomeViewModel = new HomeViewModel(this, mHomeBinding);

        startGCMManager();
    }

    @Override
    public void loadData() {

        if (Util.isNetworkConnected(this)) {
            mHomeViewModel.callApiForTickerData();
            mHomeViewModel.callApiForFeedsData(false);
        } else {
            Util.showToast(this, getString(R.string.alert_connect_to_the_internet));
        }
    }

    @Override
    public void onRefresh() {
        mHomeViewModel.callApiForFeedsData(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_refresh:
                mHomeViewModel.callApiForTickerData();
                break;
        }
    }

    /**
     * Starting the GCM manager to do the task periodically.
     */
    private void startGCMManager() {
        GcmNetworkManager mGcmNetworkManager = GcmNetworkManager.getInstance(this);
        PeriodicTask task = new PeriodicTask.Builder()
                .setService(CustomService.class)
                .setTag(Constant.TASK_UPDATE_TICKER_DATA)
                .setPeriod(300L)
                .setUpdateCurrent(true)
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                .build();

        mGcmNetworkManager.schedule(task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.variance_percentage) {
            mHomeViewModel.openVarianceByPercDialog();
        } else if (id == R.id.variance_rupees) {
            mHomeViewModel.openVarianceByRupeeDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}
