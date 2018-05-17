package com.lcm.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.lcm.grouprecyclerview.GroupItem;
import com.lcm.grouprecyclerview.GroupRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GroupRecyclerView groupRecyclerView;
    EditText edtSearch;


    private List<GroupItem> groupItemList;
    private List<GroupItem> specialGroupItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groupRecyclerView = findViewById(R.id.groupRecyclerView);
        edtSearch = findViewById(R.id.edt_search);


        buildGroupItems();
        buildSpecialGroupItems();

        groupRecyclerView.setSpecialGroupItems(specialGroupItems, "热门");
        groupRecyclerView.setData(groupItemList, new MyGroupViewFactory());
        groupRecyclerView.refreshData();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = new StringBuilder(charSequence).toString();
                groupRecyclerView.search(str);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void buildSpecialGroupItems(){
        specialGroupItems = new ArrayList<>();
        specialGroupItems.add(new GroupItem(new String("热门01"), "热门01"));
        specialGroupItems.add(new GroupItem(new String("大赛"), "大赛"));
    }


    private void buildGroupItems() {
        groupItemList = new ArrayList<>();
        groupItemList.add(new GroupItem(new String("哈哈哈哈"), "哈哈哈哈"));
        groupItemList.add(new GroupItem(new String("这时就"), "这时就"));
        groupItemList.add(new GroupItem(new String("他们之间"), "他们之间"));
        groupItemList.add(new GroupItem(new String("如果字符串"), "如果字符串"));
        groupItemList.add(new GroupItem(new String("个字符和参"), "个字符和参"));
        groupItemList.add(new GroupItem(new String("$罗罗罗欧"), "$罗罗罗欧"));
        groupItemList.add(new GroupItem(new String("较的字符"), "较的字符"));
        groupItemList.add(new GroupItem(new String("它是先比较"), "它是先比较"));
        groupItemList.add(new GroupItem(new String("让代码看"), "让代码看"));
        groupItemList.add(new GroupItem(new String("&啦啦啦"), "&啦啦啦"));
        groupItemList.add(new GroupItem(new String("加多宝"), "加多宝"));
        groupItemList.add(new GroupItem(new String("王老吉"), "王老吉"));
        groupItemList.add(new GroupItem(new String("阿里云"), "阿里云"));
        groupItemList.add(new GroupItem(new String("天天向上"), "天天向上"));
        groupItemList.add(new GroupItem(new String("要滑动"), "要滑动"));
        groupItemList.add(new GroupItem(new String("Android Studio"), "Android Studio"));
        groupItemList.add(new GroupItem(new String("Flutter可以"), "Flutter可以"));
        groupItemList.add(new GroupItem(new String("很多平台"), "很多平台"));
        groupItemList.add(new GroupItem(new String("谷歌官推的"), "谷歌官推的"));
        groupItemList.add(new GroupItem(new String("都可以封装"), "都可以封装"));
    }
}
