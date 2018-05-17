package com.lcm.grouprecyclerview;

import android.text.TextUtils;

import com.lcm.grouprecyclerview.utils.PinyinUtils;


/**
 * ****************************************************************
 * Author: LCM
 * Date: 2018/5/16 13:42
 * Desc:
 * *****************************************************************
 */
public class GroupItem {

    //普通类型
    public static final int NORMAL_ITEM = 1;
    //分组类型
    public static final int GROUP_ITEM = 2;

    private int type;

    private Object value;

    private String groupChar;

    private String valueName;

    public GroupItem(Object value, String valueName) {
        this.type = NORMAL_ITEM;
        this.value = value;
        this.valueName = valueName;
        this.groupChar = getGroupChar(valueName);
    }

    public GroupItem(int type, Object value, String valueName, String groupChar) {
        this.type = type;
        this.value = value;
        this.valueName = valueName;
        this.groupChar = groupChar;
    }

    public int getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getValueName() {
        return valueName;
    }

    public String getGroupChar() {
        return groupChar;
    }

    public void setGroupChar(String groupChar) {
        this.groupChar = groupChar;
    }

    @Override
    public String toString() {
        return "GroupItem{" +
                "type=" + type +
                ", value=" + value +
                ", groupChar='" + groupChar + '\'' +
                ", valueName='" + valueName + '\'' +
                '}';
    }

    /**
     * 获取首字母
     *
     * @param valueName
     * @return
     */
    private String getGroupChar(String valueName) {
        String first = valueName.substring(0, 1);
        String s = PinyinUtils.getInstance().getAlpha(first);
        if (TextUtils.isEmpty(s) || !s.matches("[a-zA-Z]*")) return "#";
        return s.toUpperCase();
    }

}
