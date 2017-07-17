package com.zebpay.demo.dipen.jansari.service;

import android.content.Intent;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.zebpay.demo.dipen.jansari.util.Constant;

/**
 * Created by Dipen on 7/17/2017.
 */

public class CustomService extends GcmTaskService {


    @Override
    public int onRunTask(TaskParams taskParams) {

        if (Constant.TASK_UPDATE_TICKER_DATA.equalsIgnoreCase(taskParams.getTag())) {
            Intent intent = new Intent(getApplicationContext(), TickerService.class);
            startService(intent);
            return GcmNetworkManager.RESULT_SUCCESS;
        } else
            return GcmNetworkManager.RESULT_FAILURE;

    }
}
