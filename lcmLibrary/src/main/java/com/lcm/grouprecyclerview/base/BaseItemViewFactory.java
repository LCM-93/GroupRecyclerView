package com.lcm.grouprecyclerview.base;

import android.view.View;


/**
 * ****************************************************************
 * Author: LCM
 * Date: 2018/5/16 14:23
 * Desc:
 * *****************************************************************
 */
public  abstract class BaseItemViewFactory {

    public abstract BaseItemViewBinder getBaseViewBinder(View view, int type);
}
