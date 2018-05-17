package com.lcm.demo;

import android.view.View;
import android.widget.TextView;

import com.lcm.grouprecyclerview.GroupItem;
import com.lcm.grouprecyclerview.base.BaseItemViewBinder;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2018/5/16 18:01
 * Desc:
 * *****************************************************************
 */
public class GroupItemViewBinder extends BaseItemViewBinder {

    private TextView tvTitle;

    public GroupItemViewBinder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_group;
    }

    @Override
    public void bindView(GroupItem groupItem) {
        tvTitle.setText(groupItem.getValue().toString());
    }
}
