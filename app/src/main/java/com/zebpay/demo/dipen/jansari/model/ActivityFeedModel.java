package com.zebpay.demo.dipen.jansari.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dipen on 17/7/17.
 */

public class ActivityFeedModel {

    @SerializedName("activityFeed")
    private ArrayList<ActivityFeedData> activityFeedList;

    @SerializedName("returncode")
    private int returncode;

    public ArrayList<ActivityFeedData> getActivityFeedList() {
        return activityFeedList;
    }

    public int getReturncode() {
        return returncode;
    }
}
