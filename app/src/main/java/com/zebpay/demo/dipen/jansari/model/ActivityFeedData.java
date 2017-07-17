package com.zebpay.demo.dipen.jansari.model;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;

import com.google.gson.annotations.SerializedName;
import com.zebpay.demo.dipen.jansari.util.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by dipen on 17/7/17.
 */

public class ActivityFeedData {

    @SerializedName("AcitvityType")
    private int activityType;

    @SerializedName("SourceImageUrl")
    private String imageURL;

    @SerializedName("Title")
    private String title;

    @SerializedName("Description")
    private String description;

    @SerializedName("Time")
    private String time;

    @SerializedName("RefNumber")
    private String refNumber;

    @SerializedName("Name")
    private String name;

    @SerializedName("RefGuid")
    private String refGuid;

    @SerializedName("PaymentTypeId")
    private int paymentTypeId;

    @SerializedName("paymentTypeGuid")
    private int PaymentTypeGuid;

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return getFormattedDate(time);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String getFormattedDate(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(Constant.FEED_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        long time1 = 0;

        try {
            time1 = sdf.parse(time).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago = DateUtils.getRelativeTimeSpanString(time1, now, DateUtils.MINUTE_IN_MILLIS);
            return String.valueOf(ago);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
