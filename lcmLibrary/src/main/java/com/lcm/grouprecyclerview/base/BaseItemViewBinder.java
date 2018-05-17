package com.lcm.grouprecyclerview.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcm.grouprecyclerview.GroupItem;


/**
 * ****************************************************************
 * Author: LCM
 * Date: 2018/5/16 13:40
 * Desc:
 * *****************************************************************
 */
public abstract class BaseItemViewBinder extends RecyclerView.ViewHolder {

    public BaseItemViewBinder(View itemView) {
        super(itemView);
    }

    public abstract int getLayoutId();


    public abstract void bindView(GroupItem groupItem);


}
