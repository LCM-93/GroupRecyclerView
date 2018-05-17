package com.lcm.grouprecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lcm.grouprecyclerview.base.BaseItemViewBinder;
import com.lcm.grouprecyclerview.base.BaseItemViewFactory;

import java.util.List;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2018/5/16 14:22
 * Desc:
 * *****************************************************************
 */
public class GroupRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<GroupItem> groupItemsList;

    private BaseItemViewFactory baseViewFactory;

    private View EMPTY_PARAMETER;


    public GroupRecyclerAdapter(Context context, List<GroupItem> groupItemsList, BaseItemViewFactory baseViewFactory) {
        this.context = context;
        this.groupItemsList = groupItemsList;
        this.baseViewFactory = baseViewFactory;

        this.EMPTY_PARAMETER = new View(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(baseViewFactory.getBaseViewBinder(EMPTY_PARAMETER, viewType).getLayoutId(), parent, false);
        BaseItemViewBinder baseViewBinder = baseViewFactory.getBaseViewBinder(view, viewType);
        return baseViewBinder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GroupItem groupItem = groupItemsList.get(position);
        BaseItemViewBinder baseViewBinder = (BaseItemViewBinder) holder;
        baseViewBinder.bindView(groupItem);
    }

    @Override
    public int getItemCount() {
        return groupItemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return groupItemsList.get(position).getType();
    }
}
