package com.lcm.grouprecyclerview.utils;

import android.content.Context;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2018/5/16 19:37
 * Desc:
 * *****************************************************************
 */
public class DimenUtils {


    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //px->dp
    public static int pxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
