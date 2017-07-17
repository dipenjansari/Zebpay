package com.zebpay.demo.dipen.jansari.animation;

import android.app.Activity;

import com.zebpay.demo.dipen.jansari.R;

/**
 *  Created by dipen on 17/7/17.
 */
public class AnimatorClass {

    public static void appearLeftAnimation(Activity a) {
        a.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void disappearLeftAnimation(Activity a) {
        a.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}