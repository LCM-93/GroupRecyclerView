package com.lcm.grouprecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lcm.grouprecyclerview.base.BaseItemViewFactory;
import com.lcm.grouprecyclerview.utils.DimenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2018/5/16 13:38
 * Desc:
 * *****************************************************************
 */
public class GroupRecyclerView extends RelativeLayout {

    private BaseItemViewFactory baseViewFactory;
    private List<GroupItem> groupItems;
    private List<GroupItem> specialGroupItems;
    private List<GroupItem> showGroupItems;
    private RecyclerView recyclerView;
    private View emptyView;
    private Map<String, Integer> groupMap;
    private SideBar sideBar;
    private TextView tvPrompt;
    private GroupRecyclerAdapter mAdapter;
    private String TAG = "GroupRecyclerView";
    private LinearLayoutManager manager;
    private int index = 0;
    private boolean move = false;


    public GroupRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public GroupRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buildRecyclerView();
        buildEmptyView();
        buildSideBar();
        buildPromptView();
    }

    /**
     * 构建 显示选中字母的View 居中展示
     */
    private void buildPromptView() {
        tvPrompt = new TextView(getContext());
        tvPrompt.setTextColor(Color.WHITE);
        tvPrompt.setBackgroundColor(Color.parseColor("#66000000"));
        LayoutParams params = new LayoutParams(DimenUtils.dip2px(getContext(), 80), DimenUtils.dip2px(getContext(), 80));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        params.setMargins(0, 0, 0, DimenUtils.dip2px(getContext(), 30));
        tvPrompt.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimenUtils.dip2px(getContext(), 20));
        tvPrompt.setGravity(Gravity.CENTER);
        tvPrompt.setLayoutParams(params);
        tvPrompt.setVisibility(GONE);
        addView(tvPrompt);
    }

    /**
     * 构建 右侧筛选字母列
     */
    private void buildSideBar() {
        sideBar = new SideBar(getContext());
        LayoutParams params = new LayoutParams(DimenUtils.dip2px(getContext(), 15.0f), LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        sideBar.setLayoutParams(params);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchDown() {
                if (tvPrompt != null) tvPrompt.setVisibility(VISIBLE);
            }

            @Override
            public void onTouchingLetterChanged(String s) {
                tvPrompt.setText(s);
                if (groupMap.containsKey(s)) {
                    if (recyclerView == null) return;
                    scollToPosition(groupMap.get(s));
                }
            }

            @Override
            public void onTouchUp() {
                if (tvPrompt != null) tvPrompt.setVisibility(GONE);
            }
        });
        addView(sideBar);
    }

    /**
     * 构建 RecyclerView
     */
    private void buildRecyclerView() {
        recyclerView = new RecyclerView(getContext());
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (move) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = index - manager.findFirstVisibleItemPosition();
                    if (0 <= n && n < recyclerView.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = recyclerView.getChildAt(n).getTop();
                        //最后的移动
                        recyclerView.smoothScrollBy(0, top);
                    }
                }
            }
        });
    }

    private void buildEmptyView() {
        emptyView = new TextView(getContext());
        ((TextView) emptyView).setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ((TextView) emptyView).setGravity(Gravity.CENTER);
        ((TextView) emptyView).setTextSize(TypedValue.COMPLEX_UNIT_PX, DimenUtils.dip2px(getContext(), 20));
        ((TextView) emptyView).setTextColor(Color.BLACK);
        ((TextView) emptyView).setText("未搜索到数据...");
        ((TextView) emptyView).setPadding(0, 0, 0, DimenUtils.dip2px(getContext(), 20));
        ((TextView) emptyView).setBackgroundColor(Color.WHITE);
        emptyView.setVisibility(GONE);
        addView(emptyView);
    }

    /**
     * 设置数据
     *
     * @param groupItems      Item数据集合
     * @param baseViewFactory ItemView提供类
     */
    public void setData(List<GroupItem> groupItems, BaseItemViewFactory baseViewFactory) {
        //对初始数据进行排序
        Collections.sort(groupItems, new Comparator<GroupItem>() {
            @Override
            public int compare(GroupItem t1, GroupItem t2) {
                if("#".equals(t1.getGroupChar()) && !"#".equals(t2.getGroupChar())){
                    return 1;
                }
                if(!"#".equals(t1.getGroupChar()) && "#".equals(t2.getGroupChar())){
                    return -1;
                }
                if("#".equals(t1.getGroupChar()) && "#".equals(t2.getGroupChar())){
                    return 0;
                }
                return t1.getGroupChar().compareTo(t2.getGroupChar());
            }
        });
        emptyView.setVisibility(VISIBLE);
        this.baseViewFactory = baseViewFactory;
        this.groupItems = groupItems;
    }

    /**
     * 特殊展示的分组   如常用地址 热门选择
     *
     * @param groupItems Item数据集合
     * @param groupName  特殊分组的名称
     */
    public void setSpecialGroupItems(@NonNull List<GroupItem> groupItems, @NonNull String groupName) {
        sideBar.setSpecialBar(groupName.substring(0, 1)); //取分组名称的首字设置为右侧导航列首项
        for (GroupItem groupItem : groupItems) {
            groupItem.setGroupChar(groupName.substring(0, 1));
            groupItem.setValueName(groupName);
        }
        this.specialGroupItems = groupItems;
    }

    /**
     * 判读是否是最后一个分组
     *
     * @param index
     * @return
     */
    @Deprecated
    private boolean isLastGroup(int index) {
        if (groupMap.size() <= 0) return false;
        boolean isLastGroup = true;
        for (int i : groupMap.values()) {
            if (i > index) {
                isLastGroup = false;
                break;
            }
        }
        return isLastGroup;
    }

    /**
     * recyclerView 指定位置滑动到顶部
     * 参考： https://www.jianshu.com/p/212dae40d96c
     *
     * @param n
     */
    private void scollToPosition(int n) {
        //滑动到指定的item
        this.index = n;//记录一下 在第三种情况下会用到
        //拿到当前屏幕可见的第一个position跟最后一个postion
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        //区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(n - firstItem).getTop();
            recyclerView.smoothScrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.smoothScrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }
    }


    /**
     * 刷新界面
     */
    public void refreshData() {
        emptyView.setVisibility(GONE);
        createList();
        doForGroupItems();
        if (mAdapter == null) {
            mAdapter = new GroupRecyclerAdapter(getContext(), showGroupItems, baseViewFactory);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 搜索
     *
     * @param str
     */
    public void search(String str) {
        if (groupItems == null || showGroupItems == null) {
            Log.e(TAG, "can not search in empty datas");
            return;
        }
        emptyView.setVisibility(GONE);
        showGroupItems.clear();

        if (TextUtils.isEmpty(str)) {
            showGroupItems.addAll(groupItems);
            doForGroupItems();
            mAdapter.notifyDataSetChanged();
            return;
        }

        for (GroupItem groupItem : groupItems) {
            if (groupItem.getValueName().indexOf(str) != -1) {
                showGroupItems.add(groupItem);
            }
        }
        if (showGroupItems.size() == 0) emptyView.setVisibility(VISIBLE);
        doForGroupItems();
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 添加搜索空布局
     *
     * @param emptyView
     */
    public void setSearchEmptyView(View emptyView) {
        if (emptyView == null) return;
        int index = indexOfChild(this.emptyView);
        removeView(this.emptyView); //移除默认空布局
        addView(emptyView, index);
        this.emptyView = emptyView;
    }

    /**
     * 获取搜索空布局
     *
     * @return
     */
    public View getSearchEmptyView() {
        return emptyView;
    }

    /**
     * 向原始数据集合中插入分组数据
     */
    private void doForGroupItems() {
        //Map表记录分组名称下标
        if (groupMap == null) groupMap = new HashMap<>();
        if (groupMap.size() > 0) groupMap.clear();

        for (int i = 0; i < showGroupItems.size(); i++) {
            GroupItem groupItem = showGroupItems.get(i);
            if (groupMap.containsKey(groupItem.getGroupChar())) continue;
            groupMap.put(groupItem.getGroupChar(), i);
            showGroupItems.add(i, new GroupItem(GroupItem.GROUP_ITEM, groupItem.getGroupChar(), groupItem.getGroupChar(), groupItem.getGroupChar()));
            i--;
        }
    }

    /**
     * 整合原始数据集合
     */
    private void createList() {
        if (showGroupItems == null) showGroupItems = new ArrayList<>();
        if (showGroupItems.size() > 0) showGroupItems.clear();

        if (specialGroupItems != null && specialGroupItems.size() > 0) {
            groupItems.addAll(0, specialGroupItems);
        }
        showGroupItems.addAll(groupItems);
    }


}
