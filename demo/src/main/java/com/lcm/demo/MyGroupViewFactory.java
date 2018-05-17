package com.lcm.demo;

import android.view.View;

import com.lcm.grouprecyclerview.base.BaseItemViewBinder;
import com.lcm.grouprecyclerview.base.BaseItemViewFactory;

import static com.lcm.grouprecyclerview.GroupItem.GROUP_ITEM;
import static com.lcm.grouprecyclerview.GroupItem.NORMAL_ITEM;


/**
 * ****************************************************************
 * Author: LCM
 * Date: 2018/5/16 17:51
 * Desc:
 * *****************************************************************
 */
public class MyGroupViewFactory extends BaseItemViewFactory {
    @Override
    public BaseItemViewBinder getBaseViewBinder(View view, int type) {
        switch (type) {
            case NORMAL_ITEM:
                return new NormalItemViewBinder(view);

            case GROUP_ITEM:
                return new GroupItemViewBinder(view);
        }
        return null;
    }
}
